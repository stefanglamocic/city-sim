package project.java.datamodel;

import project.java.Controller;
import project.java.datamodel.enums.LocomotiveType;
import java.util.LinkedList;


public class RailwayComposition {
    private LinkedList<RailwayVehicle> composition = new LinkedList<>();
    private int speed;
    private Controller controller;
    private LocomotiveType initialLocomotive;

    public RailwayComposition(Controller controller, int speed){
        this.controller = controller;
        this.speed = speed;
    }

    public void addRailwayVehicle(RailwayVehicle vehicle){
        if(composition.isEmpty() && vehicle instanceof Locomotive){
            composition.add(vehicle);
            initialLocomotive = ((Locomotive) vehicle).getLocomotiveType();
        }
        else if(vehicle instanceof Wagon ||
                (vehicle instanceof Locomotive && isCompatible((Locomotive) vehicle))){
            composition.add(vehicle);
        }
    }

    private boolean isCompatible(Locomotive locomotive){
        LocomotiveType locomotiveType = locomotive.getLocomotiveType();
        switch(this.initialLocomotive){
            case Passenger:{
                if(locomotiveType == LocomotiveType.Passenger || locomotiveType == LocomotiveType.Universal)
                    return true;
            }break;
            case Cargo:{
                if(locomotiveType == LocomotiveType.Cargo || locomotiveType == LocomotiveType.Universal)
                    return true;
            }break;
            case Universal:{
                return true;
            }
            case Maintenance:{
                if(locomotiveType == LocomotiveType.Maintenance || locomotiveType == LocomotiveType.Universal)
                    return true;
            }break;
        }
        return false;
    }

    public void addComposition(Position position, LinkedList<Position> railway) {
        Position newPosition = position;

        for(RailwayVehicle v : composition){
            Position temp = new Position(v.getI(), v.getJ());
            v.setI(newPosition.getI());
            v.setJ(newPosition.getJ());

            if(railway.contains(newPosition)) {
                controller.addVehicle(newPosition, v);
                v.setVisible(true);
            }
            else
                v.setVisible(false);

            newPosition = temp;
        }
    }

}


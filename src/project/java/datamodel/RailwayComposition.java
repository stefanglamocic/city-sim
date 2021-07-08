package project.java.datamodel;

import project.java.Controller;
import project.java.datamodel.enums.LocomotiveType;
import project.java.datamodel.enums.RailwayVehicleDirection;

import java.util.LinkedList;
import java.util.Set;


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

    public void addComposition(Position position, Set<Position> railroads) {
        Position newPosition = position;
        RailwayVehicleDirection newDirection = composition.get(0).getDirection();

        for(RailwayVehicle v : composition){
            Position temp = new Position(v.getI(), v.getJ());
            RailwayVehicleDirection tempDirection = v.getDirection();

            v.setI(newPosition.getI());
            v.setJ(newPosition.getJ());
            v.setDirection(newDirection);

            if(v.getI() != -1)
                controller.addVehicle(newPosition, v);

            if(railroads.contains(newPosition)) {
                v.setVisible(true);
            }
            else
                v.setVisible(false);

            newPosition = temp;
            newDirection = tempDirection;
        }
    }

}


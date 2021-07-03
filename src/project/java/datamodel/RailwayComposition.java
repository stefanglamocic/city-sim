package project.java.datamodel;

import project.java.Controller;

import java.util.LinkedList;

public class RailwayComposition {
    private LinkedList<RailwayVehicle> composition = new LinkedList<>();
    private int speed;
    private Controller controller;
    private CompositionDirection direction;
    private LocomotiveType initialLocomotive;

    public RailwayComposition(Controller controller, int speed, CompositionDirection direction){
        this.controller = controller;
        this.speed = speed;
        this.direction = direction;
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

}

enum CompositionDirection{
    Up, Down, Left, Right
}

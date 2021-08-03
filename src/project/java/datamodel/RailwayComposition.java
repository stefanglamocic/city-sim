package project.java.datamodel;

import javafx.application.Platform;
import project.java.Controller;
import project.java.datamodel.enums.LocomotiveType;
import project.java.datamodel.enums.VehicleDirection;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class RailwayComposition implements Runnable{
    private LinkedList<RailwayVehicle> composition = new LinkedList<>();
    private int speed;
    private Controller controller;
    private LocomotiveType initialLocomotive;
    private Thread thread;

    public RailwayComposition(Controller controller, int speed){
        this.controller = controller;
        this.speed = speed;
        thread = new Thread(this);
        thread.setDaemon(true);
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
        VehicleDirection newDirection = composition.get(0).getDirection();

        for(RailwayVehicle v : composition){
            Position temp = new Position(v.getI(), v.getJ());
            VehicleDirection tempDirection = v.getDirection();

            v.setI(newPosition.getI());
            v.setJ(newPosition.getJ());
            v.setDirection(newDirection);

            if(v.getI() != -1)
                controller.addVehicle(newPosition, v);

            v.setVisible(railroads.contains(newPosition));

            newPosition = temp;
            newDirection = tempDirection;
        }
    }

    private void compositionStop(Set<Position> railroads){
       RailwayVehicle locomotive = composition.getFirst();
       Position finalPosition = new Position(locomotive.getI(), locomotive.getJ());
        VehicleDirection newDirection = composition.get(0).getDirection();
       Position newPosition = finalPosition;
       for(RailwayVehicle v : composition){
           Position temp = new Position(v.getI(), v.getJ());
           VehicleDirection tempDirection = v.getDirection();
           if(!v.getPosition().equals(finalPosition)){
               v.setI(newPosition.getI());
               v.setJ(newPosition.getJ());
               v.setDirection(newDirection);
               controller.addVehicle(newPosition, v);
               v.setVisible(railroads.contains(newPosition));

               newPosition = temp;
               newDirection = tempDirection;
           }
       }
    }

    private void compositionRotation(){
        for(RailwayVehicle v : composition) {
            switch (v.getDirection()) {
                case Up:
                    v.rotate(90);
                    break;
                case Down:
                    v.rotate(270);
                    break;
                case Left:
                    v.rotate(0);
                    break;
                case Right:
                    v.rotate(180);
                    break;
            }
        }
    }

    @Override
    public void run() {
        Set<Position> system = new HashSet<>(Railroads.railroadSystem);
        LinkedList<Position> temp = Railroads.BFS(Railroads.stationC, Railroads.stationB, system);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int sleepTime = 700; //privremena brzina

        for(int i = 0; i < temp.size(); i++){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int j = i + 1;
            if(j < temp.size()){
                Position pos1 = temp.get(i);
                Position pos2 = temp.get(j);
                composition.get(0).setDirection(RoadVehicle.comparePositions(pos1, pos2));
            }
            compositionRotation();

            int finalI = i;
            Platform.runLater(() -> addComposition(temp.get(finalI), Railroads.railroads));
            RailwayStations.closeRamp(temp.get(i));
        }

        RailwayVehicle locomotive = composition.getFirst();
        RailwayVehicle lastVehicle = composition.getLast();
        Position finalPosition = new Position(locomotive.getI(), locomotive.getJ());
        while(!lastVehicle.getPosition().equals(finalPosition)){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                //logger
            }
            Platform.runLater(() -> compositionStop(Railroads.railroads));
            compositionRotation();
        }
    }

    public void go(){ thread.start(); }
}


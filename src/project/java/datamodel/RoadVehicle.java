package project.java.datamodel;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import project.java.Controller;
import project.java.datamodel.enums.VehicleDirection;
import static project.java.datamodel.ConfigProperties.*;
import static project.java.datamodel.Roads.*;

import java.util.LinkedList;
import java.util.Random;

public abstract class RoadVehicle extends Vehicle implements Runnable{
    protected String brand;
    protected String model;
    protected int modelYear;
    protected int speed;
    private final Thread thread;
    private final Controller controller;

    private final static LinkedList<Position> positionsUpToDown, positionsLeftToDown, positionsRightToDown, positionsDownToLeft,
        positionsDownToUp, positionsDownToRight;
    private static int carsOnLeftRoad, carsOnMiddleRoad, carsOnRightRoad;

    static {
        positionsUpToDown = BFS(upToDownStart, upToDownEnd, upToDown);
        positionsLeftToDown = BFS(leftToDownStart, leftToDownEnd, leftToDown);
        positionsRightToDown = BFS(rightToDownStart, rightToDownEnd, rightToDown);
        positionsDownToLeft = BFS(downToLeftStart, downToLeftEnd, downToLeft);
        positionsDownToUp = BFS(downToUpStart, downToUpEnd, downToUp);
        positionsDownToRight = BFS(downToRightStart, downToRightEnd, downToRight);
    }

    public RoadVehicle(String brand, String model, int modelYear, Image image, Controller controller) {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.controller = controller;
        setImage(image);
        setFitHeight(27);
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
        thread = new Thread(this);
        thread.setDaemon(true);
    }

    @Override
    public void run(){
        Random random = new Random();
        while(true){

            try{
                Thread.sleep(500); //vrijeme cekanja u flow pane-u
            }catch (InterruptedException e){
                //TO-DO: LOGGER
            }
            int minSleepTime = 0;
            FlowPane destination = new FlowPane();
            LinkedList<Position> positionList = new LinkedList<>();
            int finalRotation = 0;

            if(controller.getFpTop().getChildren().contains(this)){
                positionList = positionsUpToDown;
                destination = controller.getFpBottom();
                minSleepTime = middleRoadSpeed;
                rotate(180);
            }
            else if(controller.getFpLeft().getChildren().contains(this)){
                positionList = positionsLeftToDown;
                destination = controller.getFpBottom();
                minSleepTime = leftRoadSpeed;
                rotate(90);
            }
            else if(controller.getFpRight().getChildren().contains(this)){
                positionList = positionsRightToDown;
                destination = controller.getFpBottom();
                minSleepTime = rightRoadSpeed;
                rotate(270);
            }
            else if(controller.getFpBottom().getChildren().contains(this)){
                rotate(0);
                int randomNumber = random.nextInt(3);
                switch(randomizeRoad(randomNumber)){
                    case 0:
                    {
                        positionList = positionsDownToLeft;
                        destination = controller.getFpLeft();
                        minSleepTime = leftRoadSpeed;
                        finalRotation = 90;
                    }break;

                    case 1:
                    {
                        positionList = positionsDownToUp;
                        destination = controller.getFpTop();
                        minSleepTime = middleRoadSpeed;
                        finalRotation = 180;
                    }break;

                    case 2:
                    {
                        positionList = positionsDownToRight;
                        destination = controller.getFpRight();
                        minSleepTime = rightRoadSpeed;
                        finalRotation = 270;
                    }break;
                }
            }

            synchronized (this){
                while(isLimitReached(positionList)) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                        //logger
                    }
                }
            }
            regulateCarsOnRoad(positionList, true);
            LinkedList<Position> positions = positionList;
            speed = random.nextInt(minSpeed - minSleepTime) + minSleepTime;
            int sleepTime = speed;

            for (int i = 0; i < positionList.size(); i++) {
                int counter = i;
                synchronized (this){
                    while(controller.hasVehicle(positions.get(i)) || Roads.crossroadStop(positions.get(i))){
                        try{
                            wait(10);
                        }catch (InterruptedException e){
                            //logger
                        }
                    }
                    Platform.runLater(() -> controller.addVehicle(positions.get(counter), this));
                    notifyAll();
                }
//                Platform.runLater(() -> controller.addVehicle(positions.get(counter), this));
                int j = i + 1;
                if (j < positionList.size()) {
                    Position pos1 = positionList.get(i);
                    Position pos2 = positionList.get(j);
                    movementRotation(comparePositions(pos1, pos2));
                }

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    //Logger
                }

            }

            rotate(finalRotation);
            FlowPane finalDestination = destination;
            Platform.runLater(() -> finalDestination.getChildren().add(this));
            regulateCarsOnRoad(positionList, false);
        }
    }

    public void go(){ thread.start(); }

    public static VehicleDirection comparePositions(Position pos1, Position pos2){
        int x1 = pos1.getI();
        int x2 = pos2.getI();
        int y1 = pos1.getJ();
        int y2 = pos2.getJ();

        if(y2 < y1)
            return VehicleDirection.Up;
        else if(y2 > y1)
            return VehicleDirection.Down;
        else if(x2 < x1)
            return VehicleDirection.Left;
        else
            return VehicleDirection.Right;
    }

    private void movementRotation(VehicleDirection comparison){
        switch (comparison){
            case Up:
                rotate(0);
                break;
            case Down:
                rotate(180);
                break;
            case Left:
                rotate(270);
                break;
            case Right:
                rotate(90);
                break;
        }
    }

    private synchronized void regulateCarsOnRoad(LinkedList<Position> positionList, boolean add){
        if(positionList.equals(positionsUpToDown) || positionList.equals(positionsDownToUp))
            carsOnMiddleRoad = (add) ? (carsOnMiddleRoad + 1) : (carsOnMiddleRoad - 1);
        else if(positionList.equals(positionsLeftToDown) || positionList.equals(positionsDownToLeft))
            carsOnLeftRoad = (add) ? (carsOnLeftRoad + 1) : (carsOnLeftRoad - 1);
        else if(positionList.equals(positionsRightToDown) || positionList.equals(positionsDownToRight))
            carsOnRightRoad = (add) ? (carsOnRightRoad + 1) : (carsOnRightRoad - 1);
    }

    private synchronized boolean isLimitReached(LinkedList<Position> positionList){
        if((positionList.equals(positionsUpToDown) || positionList.equals(positionsDownToUp))
                && carsOnMiddleRoad == middleRoadCars)
            return true;
        else if((positionList.equals(positionsLeftToDown) || positionList.equals(positionsDownToLeft))
                && carsOnLeftRoad == leftRoadCars)
            return true;
        else return (positionList.equals(positionsRightToDown) || positionList.equals(positionsDownToRight))
                    && carsOnRightRoad == rightRoadCars;
    }

    private synchronized int randomizeRoad(int randomNumber){
        if((randomNumber == 0 && carsOnLeftRoad == leftRoadCars)
                || (randomNumber == 1 && carsOnMiddleRoad == middleRoadCars) || (randomNumber == 2 && carsOnRightRoad == rightRoadCars))
            return randomizeRoad((randomNumber + 1) % 3);
        return randomNumber;
    }
}

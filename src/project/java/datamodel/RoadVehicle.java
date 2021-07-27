package project.java.datamodel;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import project.java.Controller;
import project.java.datamodel.enums.VehicleDirection;
import static project.java.datamodel.ConfigProperties.*;

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

    static {
        positionsUpToDown = Roads.BFS(Roads.upToDownStart, Roads.upToDownEnd, Roads.upToDown);
        positionsLeftToDown = Roads.BFS(Roads.leftToDownStart, Roads.leftToDownEnd, Roads.leftToDown);
        positionsRightToDown = Roads.BFS(Roads.rightToDownStart, Roads.rightToDownEnd, Roads.rightToDown);
        positionsDownToLeft = Roads.BFS(Roads.downToLeftStart, Roads.downToLeftEnd, Roads.downToLeft);
        positionsDownToUp = Roads.BFS(Roads.downToUpStart, Roads.downToUpEnd, Roads.downToUp);
        positionsDownToRight = Roads.BFS(Roads.downToRightStart, Roads.downToRightEnd, Roads.downToRight);
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
                Thread.sleep(300); //vrijeme cekanja u flow pane-u
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
                switch(random.nextInt(3) + 1){
                    case 1:
                    {
                        positionList = positionsDownToLeft;
                        destination = controller.getFpLeft();
                        minSleepTime = leftRoadSpeed;
                        finalRotation = 90;
                    }break;

                    case 2:
                    {
                        positionList = positionsDownToUp;
                        destination = controller.getFpTop();
                        minSleepTime = middleRoadSpeed;
                        finalRotation = 180;
                    }break;

                    case 3:
                    {
                        positionList = positionsDownToRight;
                        destination = controller.getFpRight();
                        minSleepTime = rightRoadSpeed;
                        finalRotation = 270;
                    }break;
                }
            }

            LinkedList<Position> positions = positionList;
            speed = random.nextInt(minSpeed - minSleepTime) + minSleepTime;
            int sleepTime = speed;

            for (int i = 0; i < positionList.size(); i++) {
                int counter = i;
                synchronized (this){
                    while(controller.hasVehicle(positions.get(i))){
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
//                    RoadVehicle nextVehicle = (RoadVehicle) controller.getVehicle(positionList.get(j));
//                    if (nextVehicle != null && sleepTime < nextVehicle.getSpeed())
//                        sleepTime = nextVehicle.getSpeed();

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

        }
    }

    public void go(){ thread.start(); }

    public int getSpeed(){ return speed; }

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

}

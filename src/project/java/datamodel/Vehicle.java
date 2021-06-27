package project.java.datamodel;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import project.java.Controller;

import java.util.LinkedList;
import java.util.Random;

public abstract class Vehicle extends ImageView implements Runnable{
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

    public Vehicle(String brand, String model, int modelYear, int speed, Image image, Controller controller) {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.speed = speed;
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
        //LinkedList<Position> positionList = null;
        while(true){
            //LinkedList<Position> positionList = null;
            FlowPane destination = new FlowPane();
            LinkedList<Position> positionList = new LinkedList<>();
            int finalRotation = 0;

            if(controller.getFpTop().getChildren().contains(this)){
                positionList = positionsUpToDown;
                destination = controller.getFpBottom();
                rotate(180);
            }
            else if(controller.getFpLeft().getChildren().contains(this)){
                positionList = positionsLeftToDown;
                destination = controller.getFpBottom();
                rotate(90);
            }
            else if(controller.getFpRight().getChildren().contains(this)){
                positionList = positionsRightToDown;
                destination = controller.getFpBottom();
                rotate(270);
            }
            else if(controller.getFpBottom().getChildren().contains(this)){
                rotate(0);
                switch(random.nextInt(3) + 1){
                    case 1:
                    {
                        positionList = positionsDownToLeft;
                        destination = controller.getFpLeft();
                        finalRotation = 90;
                    }break;

                    case 2:
                    {
                        positionList = positionsDownToUp;
                        destination = controller.getFpTop();
                        finalRotation = 180;
                    }break;

                    case 3:
                    {
                        positionList = positionsDownToRight;
                        destination = controller.getFpRight();
                        finalRotation = 270;
                    }break;
                }
            }

            try{
                Thread.sleep(300); //vrijeme cekanja u flow pane-u
            }catch (InterruptedException e){
                //TO-DO: LOGGER
            }

            LinkedList<Position> positions = positionList;
            int sleepTime = speed;

            for (int i = 0; i < positionList.size(); i++) {
                int counter = i;
                Platform.runLater(() -> controller.addVehicle(positions.get(counter), this));
                int j = i + 1;
                if (j < positionList.size()) {
                    Vehicle nextVehicle = controller.getVehicle(positionList.get(j));
                    if (nextVehicle != null && sleepTime < nextVehicle.getSpeed())
                        sleepTime = nextVehicle.getSpeed();

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

    private void rotate(int degrees){
        setRotate(0);
        setRotate(degrees);
    }

    private int comparePositions(Position pos1, Position pos2){
        int x1 = pos1.getI();
        int x2 = pos2.getI();
        int y1 = pos1.getJ();
        int y2 = pos2.getJ();

        if(y2 < y1)
            return 1; //up
        else if(y2 > y1)
            return 2; //down
        else if(x2 < x1)
            return 3; //left
        else
            return 4; //right
    }

    private void movementRotation(int comparison){
        switch (comparison){
            case 1:
                rotate(0);
                break;
            case 2:
                rotate(180);
                break;
            case 3:
                rotate(270);
                break;
            case 4:
                rotate(90);
                break;
        }
    }

}

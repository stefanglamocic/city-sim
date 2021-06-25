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
    private Thread thread;
    private Controller controller;

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
        thread = new Thread(this);
    }

    @Override
    public void run(){
        Random random = new Random();
        LinkedList<Position> positionList = new LinkedList<>();
        FlowPane destination = null;
        while(true){

            if(controller.getFpTop().getChildren().contains(this)){
                positionList = Roads.BFS(Roads.upToDownStart, Roads.upToDownEnd, Roads.upToDown);
                destination = controller.getFpBottom();
            }
            else if(controller.getFpLeft().getChildren().contains(this)){
                positionList = Roads.BFS(Roads.leftToDownStart, Roads.leftToDownEnd, Roads.leftToDown);
                destination = controller.getFpBottom();
            }
            else if(controller.getFpRight().getChildren().contains(this)){
                positionList = Roads.BFS(Roads.rightToDownStart, Roads.rightToDownEnd, Roads.rightToDown);
                destination = controller.getFpBottom();
            }
            else if(controller.getFpBottom().getChildren().contains(this)){
                switch (random.nextInt(3) + 1){
                    case 1:
                    {
                        positionList = Roads.BFS(Roads.downToLeftStart, Roads.downToLeftEnd, Roads.downToLeft);
                        destination = controller.getFpLeft();
                    }
                        break;
                    case 2:
                    {
                        positionList = Roads.BFS(Roads.downToUpStart, Roads.downToUpEnd, Roads.downToUp);
                        destination = controller.getFpTop();
                    }
                        break;
                    case 3:
                    {
                        positionList = Roads.BFS(Roads.downToRightStart, Roads.downToRightEnd, Roads.downToRight);
                        destination = controller.getFpRight();
                    }
                        break;
                }
            }

            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                //TO-DO: LOGGER
            }

            for(Position position : positionList){
                Platform.runLater(() -> Controller.stackPanes[position.getI()][position.getJ()].getChildren().add(this));
                try{
                    Thread.sleep(speed);
                }catch (InterruptedException e){
                    //Logger
                }
            }

            FlowPane finalDestination = destination;
            Platform.runLater(() -> finalDestination.getChildren().add(this));
        }
    }

    public void go(){ thread.start(); }
}

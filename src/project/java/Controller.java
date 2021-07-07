package project.java;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import project.java.datamodel.*;
import project.java.datamodel.enums.DriveType;
import project.java.datamodel.enums.LocomotiveType;

import java.util.LinkedList;
import java.util.Set;

public class Controller {
    @FXML
    private FlowPane fpTop;
    @FXML
    private FlowPane fpBottom;
    @FXML
    private FlowPane fpLeft;
    @FXML
    private FlowPane fpRight;
    @FXML
    private GridPane gridPane;
    private StackPane[][] stackPanes = new StackPane[30][30];
    private LinkedList<Vehicle> vehicles = new LinkedList<>();
    private boolean simulationStarted = false;


    @FXML
    public void initialize(){
        populateGridPane();
        generateRoads();
        generateRailroads();
        generateTrainStations();
        generateCrossroads();

        LinkedList<Position> temp = new LinkedList<>();
        for(int i = 0; i < 9; i++){
            temp.add(new Position(2, 26 - i));
        }
        RailwayComposition comp = new RailwayComposition(this, 0);
        comp.addRailwayVehicle(new Locomotive(Images.imgTrain, "a", 5, LocomotiveType.Passenger, DriveType.Electrical));
        comp.addRailwayVehicle(new PassengerWagonForSleeping(Images.imgWagon1, "b", 3));
        comp.addRailwayVehicle(new PassengerWagonForSleeping(Images.imgWagon2, "b", 3));

        Thread thread = new Thread(() -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < 13; i++){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int finalI = i;
                Platform.runLater(() -> comp.addComposition(new Position(2, 26 - finalI), temp));
            }
        });

        thread.start();

//        Car testCar = new Car("Yugo", "Koral", 1995, 300, Images.imgCar1, 4, this);
//        Car testCar2 = new Car("Yugo", "Koral", 1995, 200, Images.imgCar2, 4, this);
//        Car testCar3 = new Car("Yugo", "Koral", 1995, 350, Images.imgCar3, 4, this);
//        vehicles.add(testCar);
//        vehicles.add(testCar2);
//        vehicles.add(testCar3);
//        fpTop.getChildren().add(testCar);
//        fpTop.getChildren().add(testCar2);
//        fpTop.getChildren().add(testCar3);

    }

    private void populateGridPane(){
        for(int i = 0; i < 30; i++){
            for(int j = 0; j < 30; j++){
                stackPanes[i][j] = new StackPane();
                StackPane temp = stackPanes[i][j];
                temp.setPrefSize(80, 80);
                setStackPaneColor(i, j, StylesCSS.WHITE);
                gridPane.add(temp, i, j);
            }
        }
    }

    private void generateRoads(){
        String color = StylesCSS.BLUE;
        for(int i = 0; i < 30; i++){
            setStackPaneColor(13, i, color);
            Roads.add(Roads.upToDown, new Position(13, i));
            setStackPaneColor(14, i, color);
            Roads.add(Roads.downToUp, new Position(14, i));
        }

        for(int i = 0; i < 9; i++){
            setStackPaneColor(i,20, color);
            Roads.add(Roads.downToLeft, new Position(i, 20));
            setStackPaneColor(29 - i,20, color);
            Roads.add(Roads.rightToDown, new Position(29 - i, 20));
        }

        for(int i = 0; i < 8; i++){
            setStackPaneColor(i,21, color);
            Roads.add(Roads.leftToDown, new Position(i, 21));
            setStackPaneColor(29 - i,21, color);
            Roads.add(Roads.downToRight, new Position(29 - i, 21));
        }

        for(int i = 22; i < 30; i++){
            setStackPaneColor(7, i, color);
            Roads.add(Roads.leftToDown, new Position(7, i));
            setStackPaneColor(29 - 7, i, color);
            Roads.add(Roads.downToRight, new Position(22, i));
        }

        for(int i = 21; i < 30; i++){
            setStackPaneColor(8, i, color);
            Roads.add(Roads.downToLeft, new Position(8, i));
            setStackPaneColor(29 - 8, i, color);
            Roads.add(Roads.rightToDown, new Position(21, i));
        }
    }

    private void generateCrossroads(){
        String color = StylesCSS.BLACK;
        setStackPaneColor(13, 6, color);
        getPositionFromSet(Roads.upToDown, 13, 6).setCrossroad();
        setStackPaneColor(14, 6, color);
        getPositionFromSet(Roads.downToUp, 14, 6).setCrossroad();
        setStackPaneColor(26, 20, color);
        getPositionFromSet(Roads.rightToDown, 26, 20).setCrossroad();
        setStackPaneColor(26, 21, color);
        getPositionFromSet(Roads.downToRight, 26, 21).setCrossroad();
        setStackPaneColor(2, 20, color);
        getPositionFromSet(Roads.downToLeft, 2, 20).setCrossroad();
        setStackPaneColor(2, 21, color);
        getPositionFromSet(Roads.leftToDown, 2, 21).setCrossroad();
    }

    private Position getPositionFromSet(Set<Position> set, int i, int j){
        Position temp = null;
        for(Position position : set){
            if(position.equals(new Position(i, j)))
                temp = position;
        }
        return temp;
    }

    private void generateRailroads(){
        String color = StylesCSS.GRAY;
        for(int i = 0; i < 14; i++){
            setRailroad(2, 29 - i, color);
            setRailroad(6 + i, 6, color);
        }
        for(int i = 0; i < 11; i++)
            setRailroad(5, 6 + i, color);
        for(int i = 0; i < 8; i++)
            setRailroad(19, 6 + i, color);
        for(int i = 0; i < 7; i++)
            setRailroad(20, 12 + i, color);
        for(int i = 0; i < 9; i++)
            setRailroad(26, 18 + i, color);
        for(int i = 0; i < 5; i++)
            setRailroad(25 - i, 18, color);
        for(int i = 0; i < 3; i++) {
            setRailroad(29 - i, 25, color);
            setRailroad(26, 9 + i, color);
        }
        for(int i = 0; i < 4; i++)
            setRailroad(28, 6 + i, color);
        for(int i = 0; i < 2; i++) {
            setRailroad(3 + i, 16, color);
            setRailroad(22, 2 + i, color);
            setRailroad(23, 3 + i, color);
        }
        for(int i = 0; i < 6; i++){
            setRailroad(27 - i, 1, color);
            setRailroad(28 - i, 5, color);
            setRailroad(26 - i, 12, color);
        }
        setRailroad(27, 9, color);
    }

    private void setStackPaneColor(int i, int j, String color){
        stackPanes[i][j].setStyle(color);
    }

    private void setRailroad(int i, int j, String color){
        setStackPaneColor(i, j, color);
        Railroads.railroads.add(new Position(i, j));
    }

    private void setStationColor(int i, int j, String color){
        setStackPaneColor(i, j, color);
        Railroads.railroads.remove(new Position(i, j));
    }

    private void setStationImage(int quadrant, int i, int j){
        ImageView view = new ImageView();
        view.setFitWidth(27);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        switch (quadrant) {
            case 1: {
                view.setImage(Images.imgStation1);
                StackPane.setAlignment(view, Pos.BOTTOM_RIGHT);
            }
            break;
            case 2:
            {
                view.setImage(Images.imgStation2);
                StackPane.setAlignment(view, Pos.BOTTOM_LEFT);
            }
                break;
            case 3:
            {
                view.setImage(Images.imgStation3);
                StackPane.setAlignment(view, Pos.TOP_RIGHT);
            }
                break;
            case 4:
            {
                view.setImage(Images.imgStation4);
                StackPane.setAlignment(view, Pos.TOP_LEFT);
            }
                break;
            default:
                break;
        }
        stackPanes[i][j].getChildren().add(view);
    }

    private void generateTrainStations(){
        Insets insets = new Insets(0,0,5,5);

        //Train station A:
        Label a = new Label("A");
        setStationColor(1, 28, StylesCSS.GRAY_THIRD);
        stackPanes[1][28].getChildren().add(a);
        setStationImage(3, 1, 28);
        StackPane.setAlignment(a, Pos.BOTTOM_LEFT);
        StackPane.setMargin(a, insets);
        setStationColor(2, 27, StylesCSS.GRAY_SECOND);
        setStationImage(2, 2, 27);
        setStationColor(1, 27, StylesCSS.GRAY_FIRST);
        setStationImage(1, 1, 27);
        setStationColor(2, 28, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 2, 28);

        //Train station B:
        Label b = new Label("B");
        setStationColor(6, 6, StylesCSS.GRAY_THIRD);
        stackPanes[6][6].getChildren().add(b);
        StackPane.setAlignment(b, Pos.BOTTOM_LEFT);
        StackPane.setMargin(b, insets);
        setStationImage(3, 6, 6);
        setStationColor(7, 5, StylesCSS.GRAY_SECOND);
        setStationImage(2, 7, 5);
        setStationColor(6, 5, StylesCSS.GRAY_FIRST);
        setStationImage(1, 6, 5);
        setStationColor(7, 6, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 7, 6);

        //Train station C:
        Label c = new Label("C");
        setStationColor(19, 13, StylesCSS.GRAY_THIRD);
        stackPanes[19][13].getChildren().add(c);
        StackPane.setAlignment(c, Pos.BOTTOM_LEFT);
        StackPane.setMargin(c, insets);
        setStationImage(3, 19, 13);
        setStationColor(20, 12, StylesCSS.GRAY_SECOND);
        setStationImage(2, 20, 12);
        setStationColor(19, 12, StylesCSS.GRAY_FIRST);
        setStationImage(1, 19, 12);
        setStationColor(20, 13, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 20, 13);

        //Train station D:
        Label d = new Label("D");
        setStationColor(26, 2, StylesCSS.GRAY_THIRD);
        stackPanes[26][2].getChildren().add(d);
        StackPane.setAlignment(d, Pos.BOTTOM_LEFT);
        StackPane.setMargin(d, insets);
        setStationImage(3, 26, 2);
        setStationColor(27, 1, StylesCSS.GRAY_SECOND);
        setStationImage(2, 27, 1);
        setStationColor(26, 1, StylesCSS.GRAY_FIRST);
        setStationImage(1, 26, 1);
        setStationColor(27, 2, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 27, 2);

        //Train station E:
        Label e = new Label("E");
        setStationColor(25, 26, StylesCSS.GRAY_THIRD);
        stackPanes[25][26].getChildren().add(e);
        StackPane.setAlignment(e, Pos.BOTTOM_LEFT);
        StackPane.setMargin(e, insets);
        setStationImage(3, 25, 26);
        setStationColor(26, 25, StylesCSS.GRAY_SECOND);
        setStationImage(2, 26, 25);
        setStationColor(25, 25, StylesCSS.GRAY_FIRST);
        setStationImage(1, 25, 25);
        setStationColor(26, 26, StylesCSS.GRAY_FOURTH);
        setStationImage(4, 26, 26);
    }

    public FlowPane getFpTop(){ return fpTop; }

    public FlowPane getFpBottom(){ return fpBottom; }

    public FlowPane getFpLeft(){ return fpLeft; }

    public FlowPane getFpRight(){ return fpRight; }

    public synchronized boolean hasVehicle(Position position){
        if(position != null) {
            int i = position.getI();
            int j = position.getJ();
            return !stackPanes[i][j].getChildren().isEmpty();
        }
        return false;
    }

    public synchronized void addVehicle(Position position, Vehicle vehicle){
        if(position != null) {
            int i = position.getI();
            int j = position.getJ();
            stackPanes[i][j].getChildren().add(vehicle);
        }
    }

    public synchronized Vehicle getVehicle(Position position){
        if(hasVehicle(position)){
            int i = position.getI();
            int j = position.getJ();
            return (Vehicle)stackPanes[i][j].getChildren().get(0);
        }
        return null;
    }

    @FXML
    public void startSimulation(){
        if(!simulationStarted){
            simulationStarted = true;
            Thread thread = new Thread(() -> {
                for(Object o : vehicles){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((RoadVehicle)o).go();
                }
            });
            thread.start();
        }
    }
}

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
import static project.java.datamodel.ConfigProperties.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;
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
    private volatile StackPane[][] stackPanes = new StackPane[30][30];
    private LinkedList<Vehicle> vehicles = new LinkedList<>();
    private boolean simulationStarted = false;
    public Properties properties;
    private final Path rootPath = Paths.get("config");
    public Path configPath = Paths.get("config/config.properties");
    public int carsGeneratedMiddle, carsGeneratedLeft, carsGeneratedRight;

    @FXML
    public void initialize(){
        properties = new Properties();
        carsGeneratedMiddle = carsGeneratedLeft = carsGeneratedRight = 0;
        try(InputStream inputStream = new FileInputStream(configPath.toString())){
            properties.load(inputStream);
        }catch (IOException e){
            //logger
        }
        loadProperties();
        new FileWatcher(this, rootPath);
        generateWorld();
        placeVehicles();

        RailwayComposition comp = new RailwayComposition(this, 0);
        comp.addRailwayVehicle(new Locomotive(Images.imgTrain, "a", 5, LocomotiveType.Passenger, DriveType.Electrical));
        comp.addRailwayVehicle(new PassengerWagonForSleeping(Images.imgWagon1, "b", 3));
        comp.addRailwayVehicle(new PassengerWagonForSleeping(Images.imgWagon2, "b", 3));

        //comp.go();
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
        Railroads.stations.add(new Position(i, j));
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

    private void generateWorld(){
        populateGridPane();
        generateRoads();
        generateRailroads();
        generateTrainStations();
        generateCrossroads();
        Railroads.railroadSystem.addAll(Railroads.railroads);
        Railroads.railroadSystem.addAll(Railroads.stations);
    }

    public synchronized void loadProperties(){
        int lrc = Integer.parseInt(properties.getProperty("leftRoadCars"));
        int mrc = Integer.parseInt(properties.getProperty("middleRoadCars"));
        int rrc = Integer.parseInt(properties.getProperty("rightRoadCars"));
        leftRoadSpeed = Integer.parseInt(properties.getProperty("leftRoadSpeed"));
        if(lrc > leftRoadCars)
            leftRoadCars = lrc;
        middleRoadSpeed = Integer.parseInt(properties.getProperty("middleRoadSpeed"));
        if(mrc > middleRoadCars)
            middleRoadCars = mrc;
        rightRoadSpeed = Integer.parseInt(properties.getProperty("rightRoadSpeed"));
        if(rrc > rightRoadCars)
            rightRoadCars = rrc;
    }

    public synchronized RoadVehicle generateVehicle(FlowPane flowPane){
        Random rng = new Random();
        RoadVehicle vehicle = null;
        switch (rng.nextInt(6) + 1){
            case 1:{
                vehicle = new Car("Java", "modelA1", 2021, Images.imgCar1, 4, this);
            }break;
            case 2:{
                vehicle = new Car("Java", "modelA2", 2021, Images.imgCar2, 4, this);
            }break;
            case 3:{
                vehicle = new Car("Java", "modelA3", 2021, Images.imgCar3, 4, this);
            }break;
            case 4:{
                vehicle = new Car("Java", "modelA4", 2021, Images.imgCar4, 4, this);
            }break;
            case 5:{
                vehicle = new Truck("Java", "modelT5", 2021, Images.imgTruck1, 100, this);
            }break;
            case 6:{
                vehicle = new Truck("Java", "modelT6", 2021, Images.imgTruck2, 80, this);
            }break;
        }
        flowPane.getChildren().add(vehicle);
        return vehicle;
    }

    public synchronized void placeVehicles(){
        Random rng = new Random();
        for(; carsGeneratedMiddle < middleRoadCars; carsGeneratedMiddle++){
            if(rng.nextInt(2) == 0)
                vehicles.add(generateVehicle(fpTop));
            else
                vehicles.add(generateVehicle(fpBottom));
        }
        for(; carsGeneratedLeft < leftRoadCars; carsGeneratedLeft++){
            if(rng.nextInt(2) == 0)
                vehicles.add(generateVehicle(fpLeft));
            else
                vehicles.add(generateVehicle(fpBottom));
        }
        for(; carsGeneratedRight < rightRoadCars; carsGeneratedRight++){
            if(rng.nextInt(2) == 0)
                vehicles.add(generateVehicle(fpRight));
            else
                vehicles.add(generateVehicle(fpBottom));
        }
    }

    @FXML
    public void startSimulation(){
        if(!simulationStarted){
            simulationStarted = true;
            Thread thread = new Thread(() -> {
                for(Object o : vehicles){
                    ((RoadVehicle)o).go();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}

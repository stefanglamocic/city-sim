package project.java;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import project.java.datamodel.MovementHistory;
import project.java.datamodel.RailwayVehicle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;

public class Details {
    @FXML
    private VBox vBox;

    @FXML
    public void initialize(){
        File history = new File(Controller.historyFolder);
        File[] files = history.listFiles();
        if(files != null){
            for(File f : files){
                try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))){
                    MovementHistory movementHistory = (MovementHistory) is.readObject();
                    TextArea textArea = new TextArea();
                    textArea.setEditable(false);
                    textArea.setWrapText(true);
                    textArea.setPrefHeight(60);
                    textArea.setMaxWidth(400);
                    textArea.setText(getCompositionDetails(movementHistory) + "\n");
                    textArea.appendText("\t je prosla kroz stanice: " + movementHistory.getStationList() + " za "
                            + movementHistory.getTripDuration() + "ms");

                    TitledPane titledPane = new TitledPane();
                    TextArea positionsList = new TextArea(movementHistory.getPositionsList());
                    positionsList.setEditable(false);
                    positionsList.setWrapText(true);
                    titledPane.setText("Niz predjenih tacaka");
                    titledPane.setMaxWidth(400);
                    titledPane.setMaxHeight(200);
                    titledPane.setExpanded(false);
                    titledPane.setContent(positionsList);

                    VBox innerVbox = new VBox();
                    innerVbox.getChildren().addAll(textArea, titledPane);
                    vBox.getChildren().add(innerVbox);
                }catch (Exception e){
                    Main.logger.log(Level.SEVERE, "Can't display details.", e);
                }
            }
        }
    }

    private String getCompositionDetails(MovementHistory movementHistory){
        StringBuilder sb = new StringBuilder("Kompozicija koju cine vozila oznaka: ");
        for(RailwayVehicle vehicle : movementHistory.getComposition()){
            sb.append(vehicle.getMark()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}

package project.java.datamodel;

import javafx.scene.image.Image;

public class Wagon extends RailwayVehicle{
    private int length;

    public Wagon(Image image, String mark, int length){
        super(image, mark);
        this.length = length;
    }
}

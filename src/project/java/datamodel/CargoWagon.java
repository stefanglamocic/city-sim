package project.java.datamodel;

import javafx.scene.image.Image;

public class CargoWagon extends Wagon{
    private int loadCapacity;

    public CargoWagon(Image image, String mark, int length, int loadCapacity){
        super(image, mark, length);
        this.loadCapacity = loadCapacity;
    }
}

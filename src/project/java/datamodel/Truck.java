package project.java.datamodel;

import javafx.scene.image.Image;

public class Truck extends Vehicle{
    private int capacity;

    public Truck(String brand, String model, int modelYear, int speed, Image image, int capacity) {
        super(brand, model, modelYear, speed, image);
        this.capacity = capacity;
    }
}

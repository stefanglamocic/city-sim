package project.java.datamodel;

import javafx.scene.image.Image;

public class Car extends Vehicle{
    private int doors;

    public Car(String brand, String model, int modelYear, int speed, Image image, int doors) {
        super(brand, model, modelYear, speed, image);
        this.doors = doors;
    }
}

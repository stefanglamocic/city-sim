package project.java.datamodel;

import javafx.scene.image.Image;
import project.java.Controller;

public class Car extends Vehicle{
    private int doors;

    public Car(String brand, String model, int modelYear, int speed, Image image, int doors, Controller controller) {
        super(brand, model, modelYear, speed, image, controller);
        this.doors = doors;
    }
}

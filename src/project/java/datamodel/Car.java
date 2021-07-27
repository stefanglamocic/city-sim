package project.java.datamodel;

import javafx.scene.image.Image;
import project.java.Controller;

public class Car extends RoadVehicle {
    private int doors;

    public Car(String brand, String model, int modelYear, Image image, int doors, Controller controller) {
        super(brand, model, modelYear, image, controller);
        this.doors = doors;
    }
}

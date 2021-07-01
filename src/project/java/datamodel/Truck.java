package project.java.datamodel;

import javafx.scene.image.Image;
import project.java.Controller;

public class Truck extends RoadVehicle {
    private int capacity;

    public Truck(String brand, String model, int modelYear, int speed, Image image, int capacity, Controller controller) {
        super(brand, model, modelYear, speed, image, controller);
        this.capacity = capacity;
    }
}

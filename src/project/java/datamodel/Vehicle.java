package project.java.datamodel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Vehicle extends ImageView {
    protected String brand;
    protected String model;
    protected int modelYear;
    protected int speed;

    public Vehicle(String brand, String model, int modelYear, int speed, Image image) {
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.speed = speed;
        setImage(image);
        setFitHeight(30);
        setPreserveRatio(true);
        setSmooth(true);
    }
}

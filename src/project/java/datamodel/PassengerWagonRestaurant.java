package project.java.datamodel;

import javafx.scene.image.Image;

public class PassengerWagonRestaurant extends PassengerWagon{
    private String description;

    public PassengerWagonRestaurant(Image image, String mark, int length, String description){
        super(image, mark, length);
        this.description = description;
    }
}

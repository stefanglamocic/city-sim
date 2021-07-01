package project.java.datamodel;

import javafx.scene.image.Image;

public class PassengerWagonWithBeds extends PassengerWagon{
    private int numberOfBeds;

    public PassengerWagonWithBeds(Image image, String mark, int length, int numberOfBeds){
        super(image, mark, length);
        this.numberOfBeds = numberOfBeds;
    }
}

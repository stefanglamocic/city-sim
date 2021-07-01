package project.java.datamodel;

import javafx.scene.image.Image;

public class PassengerWagonWithSeats extends PassengerWagon{
    private int numberOfSeats;

    public PassengerWagonWithSeats(Image image, String mark, int length, int numberOfSeats){
        super(image, mark, length);
        this.numberOfSeats = numberOfSeats;
    }
}

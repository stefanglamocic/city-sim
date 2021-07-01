package project.java.datamodel;

import javafx.scene.image.Image;

public class Locomotive extends RailwayVehicle{
    private int power;
    private LocomotiveType locomotiveType;
    private DriveType driveType;

    public Locomotive(Image image, String mark, int power, LocomotiveType locomotiveType, DriveType driveType){
        super(image, mark);
        this.power = power;
        this.locomotiveType = locomotiveType;
        this.driveType = driveType;
    }
}

enum LocomotiveType{
    Passenger, Cargo, Universal, Maintenance
}

enum DriveType{
    Steam, Diesel, Electrical
}

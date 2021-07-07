package project.java.datamodel;

import javafx.scene.image.Image;
import project.java.datamodel.enums.DriveType;
import project.java.datamodel.enums.LocomotiveType;

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

    public int getPower() {
        return power;
    }

    public LocomotiveType getLocomotiveType() {
        return locomotiveType;
    }

    public DriveType getDriveType() {
        return driveType;
    }
}


package project.java.datamodel;

import javafx.scene.image.Image;
import project.java.datamodel.enums.VehicleDirection;

import java.io.Serializable;

public abstract class RailwayVehicle extends Vehicle implements Serializable {
    private String mark;
    private int i;
    private int j;
    private VehicleDirection direction;

    public RailwayVehicle(Image image, String mark){
        this.mark = mark;
        i = j = -1;
        setImage(image);
        setFitWidth(30);
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
        direction = VehicleDirection.Up;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Position getPosition(){
        return new Position(i, j);
    }

    public VehicleDirection getDirection() {
        return direction;
    }

    public void setDirection(VehicleDirection direction) {
        this.direction = direction;
    }

    public String getMark(){ return mark; }
}

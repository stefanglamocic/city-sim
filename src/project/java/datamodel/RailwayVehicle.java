package project.java.datamodel;

import javafx.scene.image.Image;
import project.java.datamodel.enums.RailwayVehicleDirection;

public abstract class RailwayVehicle extends Vehicle{
    private String mark;
    private int i;
    private int j;
    private RailwayVehicleDirection direction;

    public RailwayVehicle(Image image, String mark){
        this.mark = mark;
        i = j = -1;
        setImage(image);
        setFitWidth(29);
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
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

    public RailwayVehicleDirection getDirection() {
        return direction;
    }

    public void setDirection(RailwayVehicleDirection direction) {
        this.direction = direction;
    }
}

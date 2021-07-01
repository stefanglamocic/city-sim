package project.java.datamodel;

import javafx.scene.image.Image;

public abstract class RailwayVehicle extends Vehicle{
    private String mark;

    public RailwayVehicle(Image image, String mark){
        this.mark = mark;
        setImage(image);
        setFitWidth(29);
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
    }

    @Override
    public void run(){}
}

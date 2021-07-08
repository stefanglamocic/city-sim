package project.java.datamodel;

import javafx.scene.image.ImageView;

public abstract class Vehicle extends ImageView{
    public void rotate(int degrees){
        setRotate(0);
        setRotate(degrees);
    }
}

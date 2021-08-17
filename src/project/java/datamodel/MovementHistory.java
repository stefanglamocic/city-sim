package project.java.datamodel;

import java.io.Serializable;
import java.util.LinkedList;

public class MovementHistory implements Serializable {
    private int speed, tripDuration;
    private LinkedList<Position> positions;

    public MovementHistory(int speed, int tripDuration, LinkedList<Position> positions){
        this.speed = speed;
        this.tripDuration = tripDuration;
        this.positions = new LinkedList<>(positions);
    }

    public void printList(){
        for(Position p : positions)
            System.out.print(p + " ");
    }
}

package project.java.datamodel;

import java.io.Serializable;
import java.util.LinkedList;

public class MovementHistory implements Serializable {
    private int tripDuration;
    private LinkedList<Position> positions;
    private LinkedList<Position> stationsList;
    private LinkedList<RailwayVehicle> composition;

    public MovementHistory(int tripDuration, LinkedList<Position> positions, LinkedList<Position> stationsList,
                           LinkedList<RailwayVehicle> composition){
        this.tripDuration = tripDuration;
        this.positions = new LinkedList<>(positions);
        this.stationsList = new LinkedList<>(stationsList);
        this.composition = new LinkedList<>(composition);
    }

    public int getTripDuration(){ return tripDuration; }

    public String getStationList(){
        StringBuilder sb = new StringBuilder();
        for(Position p : stationsList)
            sb.append(Railroads.getStationName(p)).append("-");
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String getPositionsList(){
        StringBuilder sb = new StringBuilder();
        for(Position p : positions)
            sb.append(p).append(", ");
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public LinkedList<RailwayVehicle> getComposition(){ return composition; }
}

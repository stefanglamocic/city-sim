package project.java.datamodel;

import java.util.HashSet;
import java.util.Set;
import static project.java.datamodel.Railroads.*;

public class RailwayStations {
    public static Set<Position> positionsAtoB = new HashSet<>(setPositions(stationA, stationB));
    public static Set<Position> positionsAtoE = new HashSet<>(setPositions(stationA, stationE));
    public static Set<Position> positionsBtoC = new HashSet<>(setPositions(stationB, stationC));
    public static Set<Position> positionsCtoD = new HashSet<>(setPositions(stationC, stationD));
    public static Set<Position> positionsCtoE = new HashSet<>(setPositions(stationC, stationE));

    public static boolean closeLeftRoad = false;
    public static boolean closeMiddleRoad = false;
    public static boolean closeRightRoad = false;

    public static boolean departureAtoB = false;
    public static boolean departureBtoA = false;
    public static boolean departureAtoE = false;
    public static boolean departureEtoA = false;
    public static boolean departureBtoC = false;
    public static boolean departureCtoB = false;
    public static boolean departureCtoD = false;
    public static boolean departureDtoC = false;
    public static boolean departureCtoE = false;
    public static boolean departureEtoC = false;

    private static Set<Position> setPositions(Position start, Position end){
        Set<Position> positions = new HashSet<>(BFS(start, end, railroadSystem));
        for(Position p : stations)
            positions.remove(p);
        return positions;
    }

    //zatvara odredjene sine (trebace kad se neka kompozicija vec krece u suprotnom smijeru, pa su zauzete sine za trenutnu komp.)
    public synchronized static Set<Position> closeRailroad(Set<Position> railroad){
        Set<Position>temp = new HashSet<>(railroadSystem);
        for(Position p : railroad)
            temp.remove(p);
        return temp;
    }

    public synchronized static void closeRamp(Position position){
        if(positionsAtoB.contains(position))
            closeLeftRoad = true;
        else if(positionsBtoC.contains(position))
            closeMiddleRoad = true;
        else if(positionsCtoE.contains(position))
            closeRightRoad = true;
        else
            closeLeftRoad = closeMiddleRoad = closeRightRoad = false;
    }
}

package project.java.datamodel;

import java.util.HashSet;
import java.util.Set;

public class Roads {
    public static Set<Position> upToDown = new HashSet<>();
    public static Set<Position> downToUp = new HashSet<>();
    public static Set<Position> leftToDown = new HashSet<>();
    public static Set<Position> downToLeft = new HashSet<>();
    public static Set<Position> rightToDown = new HashSet<>();
    public static Set<Position> downToRight = new HashSet<>();

    public static Position upToDownStart = new Position(13, 0);
    public static Position downToUpStart = new Position(14, 29);
    public static Position leftToDownStart = new Position(0, 21);
    public static Position downToLeftStart = new Position(8, 29);
    public static Position rightToDownStart = new Position(29, 20);
    public static Position downToRightStart = new Position(22, 29);

    public static void add(Set<Position> road, Position position){
        road.add(position);
    }

    public static boolean contains(Set<Position> road, Position position){
        return road.contains(position);
    }

    //TO DO:
    //public static LinkedList<Position> BFS(){}
}

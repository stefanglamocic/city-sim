package project.java.datamodel;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Roads {
    public static Set<Position> upToDown = new HashSet<>();
    public static Set<Position> downToUp = new HashSet<>();
    public static Set<Position> leftToDown = new HashSet<>();
    public static Set<Position> downToLeft = new HashSet<>();
    public static Set<Position> rightToDown = new HashSet<>();
    public static Set<Position> downToRight = new HashSet<>();

    //startne pozicije puteva
    public static Position upToDownStart = new Position(13, 0);
    public static Position downToUpStart = new Position(14, 29);
    public static Position leftToDownStart = new Position(0, 21);
    public static Position downToLeftStart = new Position(8, 29);
    public static Position rightToDownStart = new Position(29, 20);
    public static Position downToRightStart = new Position(22, 29);

    //krajnje pozicije puteva
    public static Position upToDownEnd = new Position(13, 29);
    public static Position downToUpEnd = new Position(14, 0);
    public static Position leftToDownEnd = new Position(7, 29);
    public static Position downToLeftEnd = new Position(0, 20);
    public static Position rightToDownEnd = new Position(21, 29);
    public static Position downToRightEnd = new Position(29, 21);

    public static void add(Set<Position> road, Position position){
        road.add(position);
    }

    public static LinkedList<Position> BFS(Position start, Position end, Set<Position> road){
        LinkedList<Position> queue = new LinkedList<>();
        boolean[][] visited = new boolean[30][30];

        queue.add(start);
        visited[start.getI()][start.getJ()] = true;
        Position temp = new Position(start.getI(), start.getJ());

        while(!visited[end.getI()][end.getJ()]){
            Position up = new Position(temp.getI(), temp.getJ() - 1);
            Position down = new Position(temp.getI(), temp.getJ() + 1);
            Position left = new Position(temp.getI() - 1, temp.getJ());
            Position right = new Position(temp.getI() + 1, temp.getJ());

            if(road.contains(up) && !visited[up.getI()][up.getJ()]){
                temp.setI(up.getI());
                temp.setJ(up.getJ());
            }
            else if(road.contains(down) && !visited[down.getI()][down.getJ()]){
                temp.setI(down.getI());
                temp.setJ(down.getJ());
            }
            else if(road.contains(left) && !visited[left.getI()][left.getJ()]){
                temp.setI(left.getI());
                temp.setJ(left.getJ());
            }
            else if(road.contains(right) && !visited[right.getI()][right.getJ()]){
                temp.setI(right.getI());
                temp.setJ(right.getJ());
            }

            visited[temp.getI()][temp.getJ()] = true;
            if(!isCrossroad(road, temp))
                queue.add(new Position(temp.getI(), temp.getJ()));
            else
                queue.add(new Position(temp.getI(), temp.getJ(), true));
        }

        return queue;
    }

    private static boolean isCrossroad(Set<Position> road, Position position){
        for(Position p : road){
            if(p.equals(position) && p.isCrossroad())
                return true;
        }
        return false;
    }
}

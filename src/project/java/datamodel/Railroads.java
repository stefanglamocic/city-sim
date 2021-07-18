package project.java.datamodel;

import java.util.*;

public class Railroads {
    public static Set<Position> railroads = new HashSet<>();
    public static Set<Position> stations = new HashSet<>();
    public static Set<Position> railroadSystem = new HashSet<>();
    public static Position stationA = new Position(1, 28);
    public static Position stationB = new Position(6, 6);
    public static Position stationC = new Position(19, 13);
    public static Position stationD = new Position(26, 2);
    public static Position stationE = new Position(25, 26);
    private static Position aToE = new Position(2, 29);
    private static Position eToA = new Position(29, 25);

    public static LinkedList<Position> BFS(Position start, Position end, Set<Position> road){
        LinkedList<LinkedList<Position>> queue = new LinkedList<>();
        boolean[][] visited = new boolean[30][30];
        LinkedList<Position> currentQueue = new LinkedList<>();
        currentQueue.add(start);
        queue.add(currentQueue);
        visited[start.getI()][start.getJ()] = true;
        Position current = currentQueue.getLast();

        while(!current.equals(end)) {
            currentQueue = queue.pollFirst();
            current = currentQueue.getLast();

            Position up = new Position(current.getI(), current.getJ() - 1);
            Position down = (current.equals(aToE)) ? eToA : new Position(current.getI(), current.getJ() + 1);
            Position left = new Position(current.getI() - 1, current.getJ());
            Position right = (current.equals(eToA)) ? aToE : new Position(current.getI() + 1, current.getJ());

            if (road.contains(up) && !visited[up.getI()][up.getJ()]) {
                visited[up.getI()][up.getJ()] = true;
                LinkedList<Position> newQueue = new LinkedList<>(currentQueue);
                newQueue.add(up);
                queue.add(newQueue);
            }
            if (road.contains(down) && !visited[down.getI()][down.getJ()]) {
                visited[down.getI()][down.getJ()] = true;
                LinkedList<Position> newQueue = new LinkedList<>(currentQueue);
                newQueue.add(down);
                queue.add(newQueue);
            }
            if (road.contains(left) && !visited[left.getI()][left.getJ()]) {
                visited[left.getI()][left.getJ()] = true;
                LinkedList<Position> newQueue = new LinkedList<>(currentQueue);
                newQueue.add(left);
                queue.add(newQueue);
            }
            if (road.contains(right) && !visited[right.getI()][right.getJ()]) {
                visited[right.getI()][right.getJ()] = true;
                LinkedList<Position> newQueue = new LinkedList<>(currentQueue);
                newQueue.add(right);
                queue.add(newQueue);
            }

        }

        return currentQueue;
    }
}

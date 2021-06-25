package project.java.datamodel;

import java.util.Objects;

public class Position{
    private int i;
    private int j;
    //dodati boolean za crossroad???

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
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

    @Override
    public String toString() {
        return "[" + i + "]" + "[" + j + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return i == position.getI() &&
                j == position.getJ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}

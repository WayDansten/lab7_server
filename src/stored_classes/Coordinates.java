package stored_classes;

import java.util.Objects;

public class Coordinates {
    private Integer x; //Максимальное значение поля: 599, Поле не может быть null
    private Integer y; //Поле не может быть null

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + x + ", y = " + y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Coordinates coordinates = (Coordinates) obj;
        return (this.x.equals(coordinates.getX()) && this.y.equals(coordinates.getY()));
    }
}

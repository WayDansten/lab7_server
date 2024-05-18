package stored_classes;

import stored_classes.enums.Furnish;
import stored_classes.enums.Transport;
import stored_classes.enums.View;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class Flat implements Comparable<Flat>, Serializable {
    private static final HashSet<Integer> usedIds = new HashSet<>();
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double area; //Значение поля должно быть больше 0
    private Integer numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private View view; //Поле не может быть null
    private Transport transport; //Поле может быть null
    private final House house; //Поле не может быть null

    public Flat(String name, Coordinates coordinates, double area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this.name = name;
        this.coordinates = coordinates;
        creationDate = new Date();
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
        int generatedId;
        do {
            generatedId = new Random().nextInt(Integer.MAX_VALUE);
        } while (usedIds.contains(generatedId));
        id = generatedId;
    }

    public Flat(int id, String name, Coordinates coordinates, Date creationDate, double area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
        addUsedId(id);
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getArea() {
        return area;
    }
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }
    public Furnish getFurnish() {
        return furnish;
    }
    public View getView() {
        return view;
    }
    public Transport getTransport() {
        return transport;
    }
    public House getHouse() {
        return house;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setArea(double area) {
        this.area = area;
    }
    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }
    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
    public void setTransport(Transport transport) {
        this.transport = transport;
    }
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public int compareTo(Flat flat) {
        return (int) (id - flat.getId());
    }
    public static void addUsedId(int id) {
            usedIds.add(id);
        }
    public static void removeUsedId(int id) {
        usedIds.remove(id);
    }
    public static void clearUsedIds() {
        usedIds.clear();
    }
    public static HashSet<Integer> getUsedIds() {
        return usedIds;
    }
    @Override
    public String toString() {
        return "id = " + id + ", name = " + name + ", creation_date = " + creationDate + ", area = " + area + ", number_of_rooms = " + numberOfRooms +
                ", furnish = " + furnish + ", view = " + view + ", transport = " + transport + ";\ncoordinates: " + coordinates +
                ";\nhouse: " + house;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, area, numberOfRooms, furnish, transport, view, coordinates, house);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Flat flat = (Flat) obj;
        return (this.name.equals(flat.getName()) && this.area == flat.getArea() && this.numberOfRooms.equals(flat.getNumberOfRooms()) &&
                this.creationDate.equals(flat.getCreationDate()) && this.furnish.equals(flat.getFurnish()) && this.view.equals(flat.getView()) &&
                this.transport.equals(flat.getTransport()) && this.coordinates.equals(flat.getCoordinates())) && this.house.equals(flat.getHouse());
    }
}

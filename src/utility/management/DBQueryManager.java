package utility.management;

import stored_classes.Coordinates;
import stored_classes.Flat;
import stored_classes.House;
import stored_classes.enums.Furnish;
import stored_classes.enums.Transport;
import stored_classes.enums.View;
import utility.auxiliary.UserData;

import java.sql.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

public class DBQueryManager {
    private static DBQueryManager instance;
    private Connection connection;

    private DBQueryManager() {
    }

    public static DBQueryManager getInstance() {
        if (instance == null) {
            instance = new DBQueryManager();
        }
        return instance;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setInstanceConnection(Connection connection) {
        instance.setConnection(connection);
    }

    public void prepareDB() throws SQLException {
        PreparedStatement dbPreparationQuery = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS COORDINATES (" +
                        "ID SERIAL PRIMARY KEY," +
                        "X INTEGER NOT NULL," +
                        "Y INTEGER NOT NULL);" +
                        "CREATE TABLE IF NOT EXISTS HOUSES (" +
                        "ID SERIAL PRIMARY KEY," +
                        "NAME TEXT," +
                        "YEAR BIGINT NOT NULL," +
                        "NUMBER_OF_FLOORS BIGINT NOT NULL," +
                        "NUMBER_OF_LIFTS INTEGER NOT NULL);" +
                        "CREATE TABLE IF NOT EXISTS USERS (" +
                        "LOGIN TEXT PRIMARY KEY," +
                        "PASSWORD TEXT NOT NULL);" +
                        "CREATE TABLE IF NOT EXISTS FLATS (" +
                        "ID SERIAL PRIMARY KEY," +
                        "NAME TEXT NOT NULL," +
                        "COORDINATES_ID INTEGER NOT NULL REFERENCES COORDINATES," +
                        "CREATION_DATE TIMESTAMP NOT NULL," +
                        "AREA DOUBLE PRECISION NOT NULL," +
                        "NUMBER_OF_ROOMS INTEGER NOT NULL," +
                        "FURNISH FURNISH NOT NULL," +
                        "VIEW VIEW NOT NULL," +
                        "TRANSPORT TRANSPORT NOT NULL," +
                        "HOUSE_ID INTEGER NOT NULL REFERENCES HOUSES," +
                        "USER_LOGIN TEXT NOT NULL REFERENCES USERS);"
        );
        dbPreparationQuery.executeUpdate();
    }

    private synchronized int insertCoordinates(Coordinates coordinates) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO COORDINATES (X, Y) VALUES (?, ?) RETURNING ID;");
        statement.setInt(1, coordinates.getX());
        statement.setInt(2, coordinates.getY());
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException();
        }
        return result.getInt(1);
    }

    private synchronized int insertHouse(House house) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO HOUSES (NAME, YEAR, NUMBER_OF_FLOORS, NUMBER_OF_LIFTS) VALUES (?, ?, ?, ?) RETURNING ID;");
        statement.setString(1, house.getName());
        statement.setLong(2, house.getYear());
        statement.setLong(3, house.getNumberOfFloors());
        statement.setInt(4, house.getNumberOfLifts());
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException();
        }
        return result.getInt(1);
    }

    public synchronized int insertFlat(Flat flat, UserData userData) throws SQLException {
        int coordinatesID = insertCoordinates(flat.getCoordinates());
        int houseID = insertHouse(flat.getHouse());
        PreparedStatement statement = connection.prepareStatement("INSERT INTO FLATS (NAME, COORDINATES_ID, CREATION_DATE, AREA, NUMBER_OF_ROOMS, FURNISH, VIEW, TRANSPORT, HOUSE_ID, USER_LOGIN) VALUES (?, ?, ?, ?, ?, CAST(? AS FURNISH), CAST(? AS VIEW), CAST(? AS TRANSPORT), ?, ?) RETURNING ID;");
        statement.setString(1, flat.getName());
        statement.setInt(2, coordinatesID);
        statement.setTimestamp(3, Timestamp.from(flat.getCreationDate().toInstant()));
        statement.setDouble(4, flat.getArea());
        statement.setInt(5, flat.getNumberOfRooms());
        statement.setString(6, flat.getFurnish().name());
        statement.setString(7, flat.getView().name());
        statement.setString(8, flat.getTransport().name());
        statement.setInt(9, houseID);
        statement.setString(10, userData.login());
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException();
        }
        return result.getInt(1);
    }

    public synchronized void deleteById(int id, String login) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM FLATS WHERE ID = ? AND USER_LOGIN = ?;");
        statement.setInt(1, id);
        statement.setString(2, login);
        statement.executeUpdate();
    }

    public synchronized ArrayList<Integer> deleteByUser(String login) throws SQLException {
        ArrayList<Integer> deletedIDs = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM FLATS WHERE USER_LOGIN = ? RETURNING ID;");
        statement.setString(1, login);
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException("В базе данных нет ни одного элемента, принадлежащего вам!");
        }
        while (result.next()) {
            deletedIDs.add(result.getInt(1));
        }
        return deletedIDs;
    }

    public synchronized ArrayList<Integer> removeGreater(int id, String login) throws SQLException {
        ArrayList<Integer> deletedIDs = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM FLATS WHERE ID > ? AND USER_LOGIN = ? RETURNING ID;");
        statement.setInt(1, id);
        statement.setString(2, login);
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Не было найдено подходящих для удаления элементов!");
        }
        while (result.next()) {
            deletedIDs.add(result.getInt(1));
        }
        return deletedIDs;
    }

    public synchronized ArrayList<Integer> removeLower(int id, String login) throws SQLException {
        ArrayList<Integer> deletedIDs = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM FLATS WHERE ID < ? AND USER_LOGIN = ? RETURNING ID;");
        statement.setInt(1, id);
        statement.setString(2, login);
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Не было найдено подходящих для удаления элементов!");
        }
        while (result.next()) {
            deletedIDs.add(result.getInt(1));
        }
        return deletedIDs;
    }

    public synchronized int update(int id, String login, Flat flat) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM FLATS WHERE ID = ? AND USER_USER_LOGIN = ?;");
        statement.setInt(1, id);
        statement.setString(2, login);
        ResultSet result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Данный элемент не принадлежит вам или элемент с данным id не найден!");
        }

        statement = connection.prepareStatement("SELECT COORDINATES_ID, HOUSE_ID FROM FLATS WHERE ID = ?;");
        statement.setInt(1, id);
        result = statement.executeQuery();
        if (!result.next()) {
            throw new SQLException("Ошибка при взаимодействии с БД!");
        }
        int coordinatesID = result.getInt(1);
        int houseID = result.getInt(2);
        statement = connection.prepareStatement("UPDATE COORDINATES SET X = ?, Y = ? WHERE ID = ?;");
        statement.setInt(1, flat.getCoordinates().getX());
        statement.setInt(2, flat.getCoordinates().getY());
        statement.setInt(3, coordinatesID);

        statement.executeUpdate();

        statement = connection.prepareStatement("UPDATE HOUSES SET NAME = ?, YEAR = ?, NUMBER_OF_FLOORS = ?, NUMBER_OF_LIFTS = ? WHERE ID = ?;");
        statement.setString(1, flat.getHouse().getName());
        statement.setLong(2, flat.getHouse().getYear());
        statement.setLong(3, flat.getHouse().getNumberOfFloors());
        statement.setInt(4, flat.getHouse().getNumberOfLifts());
        statement.setInt(5, houseID);

        statement.executeUpdate();

        statement = connection.prepareStatement("UPDATE FLATS SET NAME = ?, AREA = ?, NUMBER_OF_ROOMS = ?, FURNISH = ?, VIEW = ?, TRANSPORT = ? WHERE ID = ?");
        statement.setString(1, flat.getName());
        statement.setDouble(2, flat.getArea());
        statement.setInt(3, flat.getNumberOfRooms());
        statement.setString(4, flat.getFurnish().name());
        statement.setString(5, flat.getView().name());
        statement.setString(6, flat.getTransport().name());
        statement.setInt(7, id);

        statement.executeUpdate();

        return id;
    }

    public TreeSet<Flat> createCollectionFromDB() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM FLATS INNER JOIN COORDINATES ON (FLATS.COORDINATES_ID = COORDINATES.ID) INNER JOIN HOUSES ON (FLATS.HOUSE_ID = HOUSES.ID);");
        ResultSet result = statement.executeQuery();
        TreeSet<Flat> flats = new TreeSet<>();
        while (result.next()) {
            Coordinates coordinates = new Coordinates(result.getInt("X"), result.getInt("Y"));
            House house = new House(result.getString("NAME"), result.getLong("YEAR"), result.getLong("NUMBER_OF_FLOORS"), result.getInt("NUMBER_OF_LIFTS"));
            flats.add(new Flat(result.getInt("ID"), result.getString(2), coordinates,
                    result.getTimestamp("CREATION_DATE"), result.getDouble("AREA"), result.getInt("NUMBER_OF_ROOMS"),
                    Furnish.naming.get(result.getString("FURNISH")), View.naming.get(result.getString("VIEW")), Transport.naming.get(result.getString("TRANSPORT")), house));
        }
        return flats;
    }
    public boolean registerUser(String login, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS (LOGIN, PASSWORD) VALUES (?, ?);");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean checkUserPassword(String login, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT PASSWORD FROM USERS WHERE LOGIN = ?;");
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            result.next();
            return result.getString(1).equals(password);
        } catch (SQLException e) {
            return false;
        }
    }
}
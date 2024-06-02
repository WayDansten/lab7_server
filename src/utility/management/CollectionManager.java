package utility.management;

import utility.auxiliary.Validator;
import utility.auxiliary.Console;
import stored_classes.Flat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс, управляющий коллекцией
 */

public class CollectionManager {
    TreeSet<Flat> flats;
    Date initDate = new Date();

    /**
     * Добавляет элемент в коллекцию
     * @param flat Добавляемый элемент
     */
    public synchronized void add(Flat flat) {
        flats.add(flat);
    }

    /**
     * Удаляет элемент из коллекции по его id
     * @param id id удаляемого элемента
     */
    public synchronized String removeById(int id) {
        for (Flat flat : flats) {
            if (flat.getId() == id) {
                flats.remove(flat);
                Flat.removeUsedId(id);
                return "Квартира успешно удалена!";
            }
        }
        return "Квартира с данным id не найдена!";
    }

    /**
     * Очищает коллекцию
     */
    public synchronized void clearUser(ArrayList<Integer> deletedIDs) {
        flats.removeIf(flat -> deletedIDs.contains(flat.getId()));
    }

    /**
     * Выводит всю коллекцию в строковом представлении с порядковыми номерами элементов
     */
    public synchronized String show() {
        AtomicInteger counter = new AtomicInteger(0);
        StringBuilder info = new StringBuilder();
        flats.stream().peek(flat -> counter.getAndIncrement()).forEach(flat -> info.append(counter).append(") ").append(flat).append("\n"));
        return info.toString();
    }

    /**
     * Выводит информацию о коллекции (тип, дата инициализации, кол-во элементов)
     */
    public String info() {
        return ("Тип коллекции - " + flats.getClass() + ", Дата инициализации - " + initDate + ", Количество элементов - " + flats.size());
    }

    /**
     * Удаляет из коллекции все элементы, значение поля id которых больше, чем значение id у элемента с указанным id
     */
    public synchronized String removeGreater(ArrayList<Integer> deletedIDs){
        flats.removeIf(flat -> deletedIDs.contains(flat.getId()));
        return "Удаление успешно!";
    }
    /**
     * Удаляет из коллекции все элементы, значение поля area которых меньше, чем значение area у элемента с указанным id
     */
    public synchronized String removeLower(ArrayList<Integer> deletedIDs){
        flats.removeIf(flat -> deletedIDs.contains(flat.getId()));
        return "Удаление успешно!";
    }

    /**
     * Заполняет коллекцию значениями из файла в формате .csv
     */
    public void fillCollection(){
        try {
            flats = DBQueryManager.getInstance().createCollectionFromDB();
            Validator.validateAll(flats);
        } catch (SQLException e) {
            Console.getInstance().printError("Ошибка при заполнении коллекции из БД!");
            e.printStackTrace();
        }
    }

    /**
     * Выводит количество элементов коллекции, значение поля year которых больше, чем указанное значение year
     * @param year Значение, с которым производится сравнение
     */
    public synchronized String countGreaterThanHouse(long year) {
        long counter = flats.stream().filter(flat -> flat.getHouse().getYear() > year).count();
            return "" + counter;
    }
    /**
     * Выводит все элементы коллекции, значение поля furnish которых больше, чем указанное значение furnish (сравнение производится по целочисленным константам Furnish.quality)
     * @param quality Значение, с которым производится сравнение
     */
    public synchronized String filterLessThanFurnish(int quality) {
        StringBuilder info = new StringBuilder();
        flats.stream().filter(flat -> flat.getFurnish().getQuality() < quality).forEach(flat -> info.append(flat).append("\n"));
        return info.toString();
    }

    /**
     * Обновляет поля элемента коллекции с указанным id в интерактивном режиме
     * @param id id элемента, с которым производится сравнение
     */
    public synchronized String update(int id, Flat newFlat){
        for (Flat flat : flats) {
            if (flat.getId() == id) {
                flats.remove(flat);
                newFlat.setId(id);
                flats.add(newFlat);
                return "Поля квартиры успешно обновлены!";
            }
        }
        return "Квартира с данным id не найдена!";
    }

    /**
     * Выводит все элементы коллекции, поле name которых содержит введенную подстроку
     * @param searchedString Введенная подстрока
     */
    public synchronized String filterContainsName(String searchedString) {
        StringBuilder info = new StringBuilder();
        flats.stream().filter(flat -> flat.getName().contains(searchedString)).forEach(flat -> info.append(flat).append("\n"));
        return info.toString();
    }
}

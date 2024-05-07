package utility.management;

import utility.auxiliary.Unparser;
import utility.auxiliary.Validator;
import stored_classes.Flat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;
import static utility.auxiliary.Parser.parseFlat;

/**
 * Класс, управляющий коллекцией
 */

public class CollectionManager {
    TreeSet<Flat> flats = new TreeSet<>();
    Date initDate = new Date();

    /**
     * Добавляет элемент в коллекцию
     * @param flat Добавляемый элемент
     */
    public void add(Flat flat) {
        flats.add(flat);
    }

    /**
     * Удаляет элемент из коллекции по его id
     * @param id id удаляемого элемента
     */
    public String removeById(int id) {
        if (flats.stream().anyMatch(flat -> flat.getId() == id)) {
            flats.remove(flats.stream().filter(flat -> flat.getId() == id).findFirst());
            return "Квартира успешно удалена!";
        }
        return "Квартира с данным id не найдена!";
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        Flat.clearUsedIds();
        flats.clear();
    }

    /**
     * Выводит всю коллекцию в строковом представлении с порядковыми номерами элементов
     */
    public String show() {
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
     * @param id id элемента, с которым проводится сравнение
     */
    public String removeGreater(int id){
        boolean foundFlat = flats.stream().anyMatch(flat -> flat.getId() == id);
        if (!foundFlat) {
            return "Квартира с данным id не найдена!";
        } else {
            flats.removeIf(flat -> flat.getId() > id);
            for (int i : Flat.getUsedIds()) {
                if (i > id) {
                    Flat.removeUsedId(i);
                }
            }
            return "Удаление успешно!";
        }
    }
    /**
     * Удаляет из коллекции все элементы, значение поля area которых меньше, чем значение area у элемента с указанным id
     * @param id id элемента, с которым проводится сравнение
     */
    public String removeLower(int id){
        boolean foundFlat = flats.stream().anyMatch(flat -> flat.getId() == id);
        if (!foundFlat) {
            return "Квартира с данным id не найдена!";
        } else {
            flats.removeIf(flat -> flat.getId() < id);
            for (int i : Flat.getUsedIds()) {
                if (i < id) {
                    Flat.removeUsedId(i);
                } else {
                    break;
                }
            }
            return "Удаление успешно!";
        }
    }

    /**
     * Заполняет коллекцию значениями из файла в формате .csv
     * @param bis Буферизированный поток данных из файла
     */
    public void fillCollection(BufferedInputStream bis){
        Scanner scanner = new Scanner(bis);
        while (scanner.hasNext()) {
            String[] data = scanner.next().split(",");
            try {
                Flat flat = parseFlat(data);
                Flat.addUsedId(parseInt(data[0]));
                flats.add(flat);
            } catch (NumberFormatException e) {
                System.err.println("Некорректная строка данных! Квартира добавлена не будет");
            }
        Validator.validateAll(flats);
        }
    }

    /**
     * Сохраняет коллекцию в файл в формате .csv
     * @param file Файл, в который производится сохранение
     * @throws IOException Выбрасывается, если не хватает прав для записи в файл
     */
    public void saveCollection(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            flats.forEach(flat -> {
                try {
                    writer.write(Unparser.FlatToCSV(flat));
                } catch (IOException e) {
                    System.err.println("Ошибка при записи в файл!");
                }
            });
            }
    }

    /**
     * Выводит количество элементов коллекции, значение поля year которых больше, чем указанное значение year
     * @param year Значение, с которым производится сравнение
     */
    public String countGreaterThanHouse(long year) {
        long counter = flats.stream().filter(flat -> flat.getHouse().getYear() > year).count();
            return "" + counter;
    }
    /**
     * Выводит все элементы коллекции, значение поля furnish которых больше, чем указанное значение furnish (сравнение производится по целочисленным константам Furnish.quality)
     * @param quality Значение, с которым производится сравнение
     */
    public String filterLessThanFurnish(int quality) {
        StringBuilder info = new StringBuilder();
        flats.stream().filter(flat -> flat.getFurnish().getQuality() < quality).forEach(flat -> info.append(flat).append("\n"));
        return info.toString();
    }

    /**
     * Обновляет поля элемента коллекции с указанным id в интерактивном режиме
     * @param id id элемента, с которым производится сравнение
     */
    public String update(int id, Flat newflat){
        if (flats.stream().anyMatch(flat -> flat.getId() == id)) {
                flats.remove(flats.stream().filter(flat -> flat.getId() == id).findFirst());
                flats.add(newflat);
                return "Данные успешно обновлены!";
        }
        return "Квартира с данным id не найдена!";
    }

    /**
     * Выводит все элементы коллекции, поле name которых содержит введенную подстроку
     * @param searchedString Введенная подстрока
     */
    public String filterContainsName(String searchedString) {
        StringBuilder info = new StringBuilder();
        flats.stream().filter(flat -> flat.getName().contains(searchedString)).forEach(flat -> info.append(flat).append("\n"));
        return info.toString();
    }
}

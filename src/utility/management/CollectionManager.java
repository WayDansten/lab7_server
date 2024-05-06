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
    public void clear() {
        Flat.clearUsedIds();
        flats.clear();
    }

    /**
     * Выводит всю коллекцию в строковом представлении с порядковыми номерами элементов
     */
    public String show() {
        int counter = 1;
        StringBuilder info = new StringBuilder();
        for (Flat flat : flats) {
            info.append(counter).append(") ").append(flat).append("\n");
            counter++;
        }
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
        boolean foundFlat = false;
        for (Flat flat: flats) {
            if (flat.getId() == id) {
                foundFlat = true;
                break;
            }
        }
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
        boolean foundFlat = false;
        for (Flat flat: flats) {
            if (flat.getId() == id) {
                foundFlat = true;
                break;
            }
        }
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
            for (Flat flat : flats) {
                writer.write(Unparser.FlatToCSV(flat));
            }
        }
    }

    /**
     * Выводит количество элементов коллекции, значение поля year которых больше, чем указанное значение year
     * @param year Значение, с которым производится сравнение
     */
    public String countGreaterThanHouse(long year) {
        int counter = 0;
        for (Flat flat : flats) {
            if (flat.getHouse().getYear() > year) {
                counter++;
            }
        }
            return "" + counter;
    }
    /**
     * Выводит все элементы коллекции, значение поля furnish которых больше, чем указанное значение furnish (сравнение производится по целочисленным константам Furnish.quality)
     * @param quality Значение, с которым производится сравнение
     */
    public String filterLessThanFurnish(int quality) {
        StringBuilder info = new StringBuilder();
        for (Flat flat : flats) {
            if (flat.getFurnish().getQuality() < quality) {
                info.append(flat).append("\n");
            }
        }
        return info.toString();
    }

    /**
     * Обновляет поля элемента коллекции с указанным id в интерактивном режиме
     * @param id id элемента, с которым производится сравнение
     */
    public String update(int id, Flat flat){
        for (Flat flatSearch : flats) {
            if (flatSearch.getId() == id) {
                flats.remove(flatSearch);
                flats.add(flat);
                return "Данные успешно обновлены!";
            }
        }
        return "Квартира с данным id не найдена!";
    }

    /**
     * Выводит все элементы коллекции, поле name которых содержит введенную подстроку
     * @param searchedString Введенная подстрока
     */
    public String filterContainsName(String searchedString) {
        StringBuilder info = new StringBuilder();
        for (Flat flat : flats) {
            if (flat.getName().contains(searchedString)) {
                info.append(flat);
            }
        }
        return info.toString();
    }
}

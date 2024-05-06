package utility.auxiliary;

import stored_classes.Coordinates;
import stored_classes.Flat;
import stored_classes.House;
import stored_classes.enums.Furnish;
import stored_classes.enums.Transport;
import stored_classes.enums.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

/**
 * Преобразует строки в данные других типов
 */

public class Parser {

    /**
     * Преобразует массив строк в новый экземпляр класса Flat
     * @param data Массив строк со значениями полей класса Flat
     * @return Новый экземпляр класса Flat
     */
    public static Flat parseFlat(String[] data) throws NumberFormatException {
        return new Flat(parseInt(data[0]), data[1], new Coordinates(parseInt(data[2]), parseInt(data[3])), parseDate(data[4]),
                parseDouble(data[5]), parseInt(data[6]), Furnish.naming.get(data[7]), View.naming.get(data[8]), Transport.naming.get(data[9]),
                new House(data[10], parseLong(data[11]), parseLong(data[12]), parseInt(data[13].strip())));
    }

    /**
     * Преобразует строку, содержащую дату, в объект java.util.Date
     * @param data Введенная строка
     * @return Новый объект java.util.Date
     */
    public static Date parseDate(String data) {
        SimpleDateFormat template = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            return template.parse(data);
        } catch (ParseException e) {
            System.err.println("Недопустимый формат даты!");
            return null;
        }
    }
}

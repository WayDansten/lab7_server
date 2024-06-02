package utility.builders;

import exceptions.DataOutOfToleranceRegionException;
import utility.auxiliary.Console;
import stored_classes.Coordinates;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Класс-сборщик для класса Coordinates
 */

public class CoordinatesBuilder extends Builder<Coordinates> {
    private final Scanner receiver;
    public CoordinatesBuilder(Scanner receiver) {
        this.receiver = receiver;
    }

    /**
     * Собирает новый экземпляр класса Coordinates
     * @return Новый экземпляр класса Coordinates
     */
    @Override
    public Coordinates build(){
        return new Coordinates(createXCoordinate(), createYCoordinate());
    }

    /**
     * Запрашивает значение поля x для класса Coordinates
     * @return значение x
     */
    public int createXCoordinate(){
        int x;
        int MAX_X = 599;
        while (true) {
            Console.getInstance().printMessage("Введите целое число - координату по X (X < " + (MAX_X + 1) + "):");
            try {
                x = parseInt(receiver.next().strip());
                if (x > MAX_X) {
                    throw new DataOutOfToleranceRegionException("Недопустимое значение числа! X < " + (MAX_X + 1) + ".");
                }
                break;
            } catch (NumberFormatException e) {
                Console.getInstance().printMessage("Недопустимый формат данных! X - целое число.");
            } catch (DataOutOfToleranceRegionException e) {
                Console.getInstance().printMessage(e.getMessage());
            }
        }
        return x;
    }

    /**
     * Запрашивает значение поля y для класса Coordinates
     * @return значение y
     */
    public int createYCoordinate(){
        int y;
        while (true) {
            Console.getInstance().printMessage("Введите целое число - координату по Y:");
            try {
                y = parseInt(receiver.next().strip());
                break;
            } catch (NumberFormatException e) {
                Console.getInstance().printMessage("Недопустимый формат данных! Y - целое число.");
            }
        }
        return y;
    }
}

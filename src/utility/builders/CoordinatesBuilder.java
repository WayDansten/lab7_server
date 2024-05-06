package utility.builders;

import exceptions.DataOutOfToleranceRegionException;
import exceptions.ErrorInFunctionException;
import utility.management.Invoker;
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
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    @Override
    public Coordinates build() throws ErrorInFunctionException{
        return new Coordinates(createXCoordinate(), createYCoordinate());
    }

    /**
     * Запрашивает значение поля x для класса Coordinates
     * @return значение x
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public int createXCoordinate() throws ErrorInFunctionException{
        int x;
        int MAX_X = 599;
        while (true) {
            System.out.println("Введите целое число - координату по X (X < " + (MAX_X + 1) + "):");
            try {
                x = parseInt(receiver.next().strip());
                if (x > MAX_X) {
                    throw new DataOutOfToleranceRegionException("Недопустимое значение числа! X < " + (MAX_X + 1) + ".");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Недопустимый формат данных! X - целое число.");
                if (Invoker.getInstance().getInScriptState()) {
                    throw new ErrorInFunctionException("При исполнении скрипта произошла ошибка!");
                }
            } catch (DataOutOfToleranceRegionException e) {
                System.out.println(e.getMessage());
                if (Invoker.getInstance().getInScriptState()) {
                    throw new ErrorInFunctionException("При исполнении скрипта произошла ошибка!");
                }
            }
        }
        return x;
    }

    /**
     * Запрашивает значение поля y для класса Coordinates
     * @return значение y
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public int createYCoordinate() throws ErrorInFunctionException{
        int y;
        while (true) {
            System.out.println("Введите целое число - координату по Y:");
            try {
                y = parseInt(receiver.next().strip());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Недопустимый формат данных! Y - целое число.");
                if (Invoker.getInstance().getInScriptState()) {
                    throw new ErrorInFunctionException("При исполнении скрипта произошла ошибка!");
                }
            }
        }
        return y;
    }
}

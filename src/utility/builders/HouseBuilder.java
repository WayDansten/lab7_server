package utility.builders;

import exceptions.DataOutOfToleranceRegionException;
import exceptions.ErrorInFunctionException;
import utility.management.Invoker;
import stored_classes.House;

import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

/**
 * Класс-сборщик для класса House
 */

public class HouseBuilder extends Builder<House> {
    private final Scanner receiver;
    public HouseBuilder(Scanner receiver) {
        this.receiver = receiver;
    }
    /**
     * Собирает новый экземпляр класса House
     * @return Новый экземпляр класса House
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    @Override
    public House build() throws ErrorInFunctionException {
        return new House(createName(), createYear(), createNumberOfFloors(), createNumberOfLifts());
    }

    /**
     * Запрашивает значение поля name для класса House
     * @return значение name
     */
    public String createName() {
        String name;
        System.out.println("Введите название дома (оставьте строку пустой для значения null)");
        name = receiver.next().replaceAll("[\r\n]", "");
        if (name.isEmpty()) {
            name = null;
        }
        return name;
    }
    /**
     * Запрашивает значение поля year для класса House
     * @return значение year
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public long createYear() throws ErrorInFunctionException{
        long year;
        int MIN_YEAR = 0;
        while (true) {
            System.out.println("Введите целое число - год постройки дома Y (N > " + MIN_YEAR + "):");
            try {
                year = parseLong(receiver.next().strip());
                if (year <= MIN_YEAR) {
                    throw new DataOutOfToleranceRegionException("Недопустимое значение числа! Y > " + MIN_YEAR + ".");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Недопустимый формат данных! Y - целое число.");
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
        return year;
    }
    /**
     * Запрашивает значение поля numberOfFloors для класса House
     * @return значение numberOfFloors
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public long createNumberOfFloors() throws ErrorInFunctionException {
        long numberOfFloors;
        int MIN_NUMBER_OF_FLOORS = 0;
        while (true) {
            System.out.println("Введите целое число - количество этажей в доме N (N > " + MIN_NUMBER_OF_FLOORS + "):");
            try {
                numberOfFloors = parseLong(receiver.next().strip());
                if (numberOfFloors <= MIN_NUMBER_OF_FLOORS) {
                    throw new DataOutOfToleranceRegionException("Недопустимое значение числа! N > " + MIN_NUMBER_OF_FLOORS + ".");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Недопустимый формат данных! N - целое число.");
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
        return numberOfFloors;
    }
    /**
     * Запрашивает значение поля numberOfLifts для класса House
     * @return значение numberOfLifts
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public Integer createNumberOfLifts() throws ErrorInFunctionException{
        int numberOfLifts;
        int MIN_NUMBER_OF_LIFTS = 0;
        while (true) {
            System.out.println("Введите целое число - количество лифтов в доме N (N > " + MIN_NUMBER_OF_LIFTS + "):");
            try {
                numberOfLifts = parseInt(receiver.next().strip());
                if (numberOfLifts <= MIN_NUMBER_OF_LIFTS) {
                    throw new DataOutOfToleranceRegionException("Недопустимое значение числа! N > " + MIN_NUMBER_OF_LIFTS + ".");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Недопустимый формат данных! N - целое число.");
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
        return numberOfLifts;
    }

}

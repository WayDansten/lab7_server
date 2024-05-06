package utility.builders;

import exceptions.DataOutOfToleranceRegionException;
import exceptions.ErrorInFunctionException;
import exceptions.WrongInputException;
import utility.management.Invoker;
import stored_classes.Flat;
import stored_classes.enums.Furnish;
import stored_classes.enums.Transport;
import stored_classes.enums.View;

import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Класс-сборщик для класса Flat
 */

public class FlatBuilder extends Builder<Flat> {
    private final Scanner receiver;
    public FlatBuilder(Scanner receiver) {
        this.receiver = receiver;
    }
    /**
     * Собирает новый экземпляр класса Flat
     * @return Новый экземпляр класса Flat
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    @Override
    public Flat build() throws ErrorInFunctionException {
        return new Flat(createName(), new CoordinatesBuilder(receiver).build(), createArea(), createNumberOfRooms(), createFurnish(), createView(), createTransport(), new HouseBuilder(receiver).build());
    }
    /**
     * Запрашивает значение поля area для класса Flat
     * @return значение area
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public double createArea() throws ErrorInFunctionException {
        double s;
        int MIN_AREA = 0;
        while (true) {
            System.out.println("Введите вещественное число - площадь квартиры S (S > " + MIN_AREA + "):");
            try {
                s = parseDouble(receiver.next().strip());
                if (s <= MIN_AREA) {
                    throw new DataOutOfToleranceRegionException("Недопустимое значение числа! S > " + MIN_AREA + ".");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Недопустимый формат данных! S - вещественное число.");
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
        return s;
    }
    /**
     * Запрашивает значение поля numberOfRooms для класса Flat
     * @return значение numberOfRooms
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public Integer createNumberOfRooms() throws ErrorInFunctionException{
        int numberOfRooms;
        int MIN_NUMBER_OF_ROOMS = 0;
        while (true) {
            System.out.println("Введите целое число - количество комнат в квартире N (N > " + MIN_NUMBER_OF_ROOMS + "):");
            try {
                numberOfRooms = parseInt(receiver.next().strip());
                if (numberOfRooms <= MIN_NUMBER_OF_ROOMS) {
                    throw new DataOutOfToleranceRegionException("Недопустимое значение числа! N > " + MIN_NUMBER_OF_ROOMS + ".");
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
        return numberOfRooms;
    }
    /**
     * Запрашивает значение поля name для класса Flat
     * @return значение name
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public String createName() throws ErrorInFunctionException{
        String name;
        while (true) {
            System.out.println("Введите название квартиры (название - не пустая строка)");
            try {
                name = receiver.next().replaceAll("[\r\n]", "");
                if (name.isEmpty()) {
                    throw new WrongInputException("Название квартиры не может быть пустой строкой!");
                }
                break;
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
                if (Invoker.getInstance().getInScriptState()) {
                    throw new ErrorInFunctionException("При исполнении скрипта произошла ошибка!");
                }
            }
        }
        return name;
    }
    /**
     * Запрашивает значение поля furnish для класса Flat
     * @return значение furnish
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public Furnish createFurnish() throws ErrorInFunctionException{
        Furnish furnish;
        while (true) {
            System.out.println("Введите вид мебели в квартире (перечень приведен ниже):");
            System.out.println("""
                    NONE - мебель отсутствует
                    LITTLE - небольшое количество мебели
                    BAD - низкокачественная мебель
                    FINE - качественная мебель
                    DESIGNER - дизайнерская мебель
                    """);
            try {
                furnish = Furnish.naming.get(receiver.next().strip().toUpperCase());
                if (furnish == null) {
                    throw new WrongInputException("Несуществующий вид мебели! Пожалуйста, введите вид мебели из перечня.");
                }
                break;
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
                if (Invoker.getInstance().getInScriptState()) {
                    throw new ErrorInFunctionException("При исполнении скрипта произошла ошибка!");
                }
            }
        }
        return furnish;
    }
    /**
     * Запрашивает значение поля transport для класса Flat
     * @return значение transport
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public Transport createTransport() throws ErrorInFunctionException{
        Transport transport;
        while (true) {
            System.out.println("Введите степень транспортной доступности квартиры (перечень приведен ниже):");
            System.out.println("""
                    FEW - очень малое количество общественного транспорта
                    LITTLE - небольшое количество общественного транспорта
                    NORMAL - среднее количество общественного транспорта
                    ENOUGH - достаточное количество общественного транспорта
                    """);
            try {
                transport = Transport.naming.get(receiver.next().strip().toUpperCase());
                if (transport == null) {
                    throw new WrongInputException("Несуществующая степень транспортной доступности! Пожалуйста, введите степень из перечня.");
                }
                break;
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
                if (Invoker.getInstance().getInScriptState()) {
                    throw new ErrorInFunctionException("При исполнении скрипта произошла ошибка!");
                }
            }
        }
        return transport;
    }
    /**
     * Запрашивает значение поля view для класса Flat
     * @return значение view
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */
    public View createView() throws ErrorInFunctionException{
        View view;
        while (true) {
            System.out.println("Введите, какой у квартиры вид из окна (перечень приведен ниже):");
            System.out.println("""
                    BAD - плохой вид из окна
                    NORMAL - неплохой вид из окна
                    STREET - вид на улицу
                    YARD - вид на двор
                    PARK - вид на парк
                    """);
            try {
                view = View.naming.get(receiver.next().strip().toUpperCase());
                if (view == null) {
                    throw new WrongInputException("Несуществующий вид из окна! Пожалуйста, введите вид из перечня.");
                }
                break;
            } catch (WrongInputException e) {
                System.out.println(e.getMessage());
                if (Invoker.getInstance().getInScriptState()) {
                    throw new ErrorInFunctionException("При исполнении скрипта произошла ошибка!");
                }
            }
        }
        return view;
    }
}

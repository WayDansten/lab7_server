package exceptions;

/**
 * Класс исключений, связанных с данными, выходящими за ОДЗ для полей классов
 */

public class DataOutOfToleranceRegionException extends WrongInputException {
    public DataOutOfToleranceRegionException(String message) {
        super(message);
    }
}

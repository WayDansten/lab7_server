package exceptions;

/**
 * Родительский класс для всех исключений, связанных с неверно введенными пользователем данными
 */

public class WrongInputException extends Exception {
    public WrongInputException(String message) {
        super(message);
    }
}

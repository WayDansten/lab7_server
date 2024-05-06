package exceptions;

/**
 * Класс исключений, выбрасываемых в случае, если во время исполнения скрипта происходит любое другое исключение
 */

public class ErrorInFunctionException extends Exception{
    public ErrorInFunctionException(String message) {
        super(message);
    }
}

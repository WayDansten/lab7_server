package utility.commands;

import exceptions.ErrorInFunctionException;
import stored_classes.Flat;

/**
 * Интерфейс для всех команд
 */

public interface Command {
    /**
     * Исполняет команду
     * @param args От 0 до N аргументов
     */
    public String execute(String... args);
    public default void setExtraArgument(Flat flat) {};
}

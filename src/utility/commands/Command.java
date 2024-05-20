package utility.commands;

import exceptions.ErrorInFunctionException;
import stored_classes.Flat;
import utility.auxiliary.UserData;

/**
 * Интерфейс для всех команд
 */

public abstract class Command {
    UserData userData;
    /**
     * Исполняет команду
     * @param args От 0 до N аргументов
     */
    public abstract String execute(String... args);
    public void setExtraArgument(Flat flat) {}

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}

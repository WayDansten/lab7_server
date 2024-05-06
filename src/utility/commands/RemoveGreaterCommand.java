package utility.commands;

import exceptions.ErrorInFunctionException;
import utility.management.CollectionManager;
import utility.management.Invoker;

import static java.lang.Integer.parseInt;

/**
 * Команда, удаляющая из коллекции все элементы, значение поля id которых больше, чем значение id у элемента с указанным id
 */

public class RemoveGreaterCommand implements Command {
    CollectionManager cm;
    public RemoveGreaterCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        try {
            return cm.removeGreater(parseInt(args[0]));
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        }
    }
}

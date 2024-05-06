package utility.commands;

import exceptions.ErrorInFunctionException;
import utility.management.CollectionManager;
import utility.management.Invoker;

import static java.lang.Integer.parseInt;

/**
 * Команда, удаляющая из коллекции все элементы, значение поля id которых меньше, чем значение id у элемента с указанным id
 */

public class RemoveLowerCommand implements Command{
    CollectionManager cm;
    public RemoveLowerCommand (CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        try {
            return cm.removeLower(parseInt(args[0]));
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        }
    }
}

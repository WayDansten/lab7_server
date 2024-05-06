package utility.commands;

import exceptions.ErrorInFunctionException;
import utility.management.CollectionManager;
import utility.management.Invoker;

import static java.lang.Integer.parseInt;

/**
 * Команда, удаляющая элемент из коллекции по его id
 */

public class RemoveByIdCommand implements Command {
    CollectionManager cm;
    public RemoveByIdCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        String input = args[0];
        try {
            return cm.removeById(parseInt(input));
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        }
    }
}

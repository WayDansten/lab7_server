package utility.commands;

import exceptions.ErrorInFunctionException;
import utility.management.CollectionManager;

/**
 * Команда, выводящая все элементы коллекции, поле name которых содержит введенную подстроку
 */

public class FilterContainsNameCommand extends Command {
    CollectionManager cm;
    public FilterContainsNameCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        return cm.filterContainsName(args[0]);
    }
}

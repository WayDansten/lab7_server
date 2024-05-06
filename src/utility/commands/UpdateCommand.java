package utility.commands;

import exceptions.ErrorInFunctionException;
import stored_classes.Flat;
import utility.management.CollectionManager;
import utility.management.Invoker;

import static java.lang.Integer.parseInt;

/**
 * Команда, обновляющая поля элемента коллекции с указанным id
 */

public class UpdateCommand implements Command {
    CollectionManager cm;
    Flat updatedFlat;
    public UpdateCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public void setExtraArgument(Flat flat) {
        updatedFlat = flat;
    }
    @Override
    public String execute(String... args){
        try {
            return cm.update(parseInt(args[0]), updatedFlat);
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        }
    }
}

package utility.commands;

import exceptions.ErrorInFunctionException;
import stored_classes.Flat;
import utility.management.CollectionManager;
import utility.management.Invoker;
import utility.builders.FlatBuilder;

/**
 * Команда, добавляющая элемент в коллекцию
 */

public class AddCommand implements Command {
    CollectionManager cm;
    Flat addedFlat;
    public AddCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public void setExtraArgument(Flat flat) {
        addedFlat = flat;
    }
    @Override
    public String execute(String... args){
        Invoker.getInstance().getCollectionManager().add(addedFlat);
        return "Квартира успешно добавлена!";
    }
}

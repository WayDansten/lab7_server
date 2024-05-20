package utility.commands;

import stored_classes.Flat;
import utility.auxiliary.UserData;
import utility.management.CollectionManager;
import utility.management.DBQueryManager;
import utility.management.CommandExecutionManager;

import java.sql.SQLException;

/**
 * Команда, добавляющая элемент в коллекцию
 */

public class AddCommand extends Command {
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
        try {
            int id = DBQueryManager.getInstance().insertFlat(addedFlat, userData);
            addedFlat.setId(id);
            CommandExecutionManager.getInstance().getCollectionManager().add(addedFlat);
        } catch (SQLException e) {
            return "Ошибка при добавлении данных в БД!";
        }
        return "Квартира успешно добавлена!";
    }
}

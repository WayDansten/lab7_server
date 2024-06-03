package utility.commands;

import stored_classes.Flat;
import utility.management.CollectionManager;
import utility.management.DBQueryManager;

import java.sql.SQLException;

import static java.lang.Integer.parseInt;

/**
 * Команда, обновляющая поля элемента коллекции с указанным id
 */

public class UpdateCommand extends Command {
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
            int id = parseInt(args[0]);
            DBQueryManager.getInstance().update(id, userData.login(), updatedFlat);
            return cm.update(id, updatedFlat);
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}

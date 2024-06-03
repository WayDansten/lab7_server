package utility.commands;

import utility.management.CollectionManager;
import utility.management.DBQueryManager;

import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Команда, удаляющая из коллекции все элементы, значение поля id которых меньше, чем значение id у элемента с указанным id
 */

public class RemoveLowerCommand extends Command {
    CollectionManager cm;
    public RemoveLowerCommand (CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        try {
            int id = parseInt(args[0]);
            ArrayList<Integer> deletedIDs = DBQueryManager.getInstance().removeLower(id, userData.login());
            return cm.removeLower(deletedIDs);
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}

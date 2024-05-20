package utility.commands;

import utility.management.CollectionManager;
import utility.management.DBQueryManager;

import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Команда, удаляющая из коллекции все элементы, значение поля id которых больше, чем значение id у элемента с указанным id
 */

public class RemoveGreaterCommand extends Command {
    CollectionManager cm;
    public RemoveGreaterCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        try {
            int id = parseInt(args[0]);
            ArrayList<Integer> deletedIDs = DBQueryManager.getInstance().removeGreater(id, userData.login());
            return cm.removeGreater(deletedIDs);
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        } catch (SQLException e) {
            return "Ошибка при удалении данных из БД!";
        }
    }
}

package utility.commands;

import utility.management.CollectionManager;
import utility.management.DBQueryManager;

import java.sql.SQLException;

import static java.lang.Integer.parseInt;

/**
 * Команда, удаляющая элемент из коллекции по его id
 */

public class RemoveByIdCommand extends Command {
    CollectionManager cm;
    public RemoveByIdCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        String input = args[0];
        try {
            int id = parseInt(input);
            DBQueryManager.getInstance().deleteById(id, userData.login());
            return cm.removeById(id);
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        } catch (SQLException e) {
            return "Ошибка при удалении данных из БД!";
        }
    }
}

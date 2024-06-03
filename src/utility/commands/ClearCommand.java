package utility.commands;

import utility.management.CollectionManager;
import utility.management.DBQueryManager;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Команда, очищающая коллекцию
 */

public class ClearCommand extends Command {
    CollectionManager cm;
    public ClearCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        try {
            ArrayList<Integer> deletedIDs = DBQueryManager.getInstance().deleteByUser(userData.login());
            cm.clearUser(deletedIDs);
            return "Коллекция успешно очищена!";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}

package utility.commands;

import utility.management.CollectionManager;

/**
 * Команда, очищающая коллекцию
 */

public class ClearCommand implements Command{
    CollectionManager cm;
    public ClearCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        cm.clear();
        return "Коллекция успешно очищена!";
    }
}

package utility.commands;

import utility.management.CollectionManager;

/**
 * Команда, выводящая коллекцию в строковом представлении
 */

public class ShowCommand implements Command{
    CollectionManager cm;
    public ShowCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        return cm.show();
    }
}

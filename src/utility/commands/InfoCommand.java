package utility.commands;

import utility.management.CollectionManager;

/**
 * Команда, выводящая информацию о коллекции
 */

public class InfoCommand extends Command {
    CollectionManager cm;
    public InfoCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        return cm.info();
    }
}

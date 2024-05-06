package utility.commands;

import utility.management.CollectionManager;
import utility.management.Invoker;

/**
 * Команда, выводящая имена последних 10 команд
 */

public class HistoryCommand implements Command {
    CollectionManager cm;
    public HistoryCommand (CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        return Invoker.getInstance().getCommandHistory().toString();
    }
}

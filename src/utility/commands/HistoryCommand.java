package utility.commands;

import utility.management.CollectionManager;
import utility.management.CommandExecutionManager;

/**
 * Команда, выводящая имена последних 10 команд
 */

public class HistoryCommand extends Command {
    CollectionManager cm;
    public HistoryCommand (CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        return CommandExecutionManager.getInstance().getCommandHistory().toString();
    }
}

package utility.management;

import exceptions.ErrorInFunctionException;
import utility.commands.*;
import utility.requests.Request;
import utility.requests.RequestWithFlatCreation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Управляет командами и вводом/выводом
 */

public class Invoker {
    private static Invoker instance;
    private boolean inScript = false;
    public static Invoker getInstance() {
        if (instance == null) {
            instance = new Invoker();
        }
        return instance;
    }
    private final CollectionManager cm = new CollectionManager();
    private final ArrayList<String> commandHistory = new ArrayList<>();
    Map<String, Command> commands = new HashMap<>() {
        {
            put("add", new AddCommand(cm));
            put("remove_by_id", new RemoveByIdCommand(cm));
            put("help", new HelpCommand(cm));
            put("clear", new ClearCommand(cm));
            put("show", new ShowCommand(cm));
            put("info", new InfoCommand(cm));
            put("remove_greater", new RemoveGreaterCommand(cm));
            put("remove_lower", new RemoveLowerCommand(cm));
            put("history", new HistoryCommand(cm));
            put("filter_less_than_furnish", new FilterLessThanFurnishCommand(cm));
            put("filter_contains_name", new FilterContainsNameCommand(cm));
            put("count_greater_than_house", new CountGreaterThanHouseCommand(cm));
            put("update", new UpdateCommand(cm));
        }
    };

    /**
     * Исполняет одну введенную команду
     * @return Сигнал о конце ввода. Это может быть команда exit, конец файла или сигнал об окончании ввода
     * @throws ErrorInFunctionException Выбрасывается, если произошло любое другое исключение во время исполнения скрипта
     */

    public String executeCommand(Request request){
        try {
            String[] args = request.extract();
            Command command = commands.get(args[0].strip().toLowerCase());
            if (commandHistory.size() > 10) {
                commandHistory.remove(0);
            }
            if (command == null) {
                return "Несуществующая команда!";
            } else {
                commandHistory.add(args[0].strip().toLowerCase());
                if (request instanceof RequestWithFlatCreation) {
                    command.setExtraArgument(((RequestWithFlatCreation) request).getExtraArgument());
                }
                if (args.length == 1) {
                    return command.execute("");
                } else {
                    return command.execute(args[1].strip());
                }
            }
        } catch (NoSuchElementException e) {
            return "Обнаружено прерывание!";
        }
    }
    /**
     * Выводит последние 10 команд
     * @return Массив имен последних десяти команд
     */
    public ArrayList<String> getCommandHistory() {
        return commandHistory;
    }

    public CollectionManager getCollectionManager() {
        return cm;
    }
    public boolean getInScriptState() {
        return inScript;
    }
}

package utility.commands;

import utility.management.CollectionManager;
import stored_classes.enums.Furnish;

/**
 * Команда, выводящая все элементы коллекции, значение поля furnish (константы Furnish.quality) которых меньше указанного
 */

public class FilterLessThanFurnishCommand extends Command {
    CollectionManager cm;
    public FilterLessThanFurnishCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        int quality;
        try {
            quality = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return "Недопустимый тип данных!";
        }
        if (!Furnish.qualities.contains(quality)) {
            return "Несуществующий вид мебели!";
        } else {
            return cm.filterLessThanFurnish(quality);
        }
    }
}

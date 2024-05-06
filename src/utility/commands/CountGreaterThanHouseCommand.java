package utility.commands;

import exceptions.ErrorInFunctionException;
import utility.management.CollectionManager;
import utility.management.Invoker;

import static java.lang.Long.parseLong;

/**
 * Команда, считающая все элементы коллекции, у которых значение поля house.year больше, чем указанное значение
 */

public class CountGreaterThanHouseCommand implements Command {
    CollectionManager cm;
    public CountGreaterThanHouseCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args){
        String data = args[0];
        long year;
            try {
                year = parseLong(data);
            } catch (NumberFormatException e) {
                return "Недопустимый тип данных! Пожалуйста, введите целое число";
            }
        return cm.countGreaterThanHouse(year);
    }
}

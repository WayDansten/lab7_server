package utility.commands;

import utility.management.CollectionManager;

/**
 * Команда, выводящая информацию по всем командам
 */

public class HelpCommand implements Command {
    CollectionManager cm;
    public HelpCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        return """
                help - выводит справку по всем доступным командам
                info - выводит информацию о коллекции (тип, дата инициализации, кол-во элементов)
                show - выводит все элементы коллекции в строковом представлении
                add - добавляет новый элемент в коллекцию
                update id - обновляет значение элемента коллекции, id которого равен заданному
                remove_by_id id - удаляет элемент из коллекции по его id
                clear - очищает коллекцию
                save - сохраняет коллекцию в файл
                execute_script file_name - считывает и запускает скрипт из указанного файла
                exit - завершает работу в программы (без сохранения в файл)
                remove_greater - удаляет из коллекции все элементы, превышающие заданный (по значению поля id)
                remove_lower - удаляет из коллекции все элементы, меньшие заданного (по значению поля id)
                history - выводит последние 10 команд без аргументов
                count_greater_than_house house - выводит количество элементов, значение поля house (поля House.year) которых больше заданного
                filter_contains_name name - выводит элементы, значение поля name которых содержит введенную подстроку
                filter_less_than_furnish furnish - выводит элементы, значение поля furnish (константы Furnish.quality) которых меньше введенного. Виды мебели (необходимо ввести число quality):
                    NONE (0) - мебель отсутствует
                    BAD (1) - низкокачественная мебель
                    LITTLE (2) - небольшое количество мебели
                    FINE (3) - качественная мебель
                    DESIGNER (4) - дизайнерская мебель
                """;
    }
}

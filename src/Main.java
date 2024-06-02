import utility.auxiliary.Console;
import utility.management.ServerModule;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        ServerModule serverModule = ServerModule.getInstance();
        try {
            serverModule.launch();
        } catch (IOException e) {
            Console.getInstance().printError("Ошибка подключения к клиенту!");
        }
    }
}
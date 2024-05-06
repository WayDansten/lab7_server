import utility.management.ServerModule;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ServerModule serverModule = ServerModule.getInstance();
            try {
                serverModule.launch();
            } catch (IOException e) {
                System.err.println("Ошибка подключения!");
                e.printStackTrace();
            }
    }
}
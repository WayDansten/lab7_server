package utility.management;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Класс, отвечающий за ввод
 */

public class InputManager {
    private static InputManager instance;
    private Scanner receiver = new Scanner(System.in);
    private InputManager(){}
    /**
     * Переводит приложение в режим интерактивного ввода с консоли
     */
    public void setInteractiveMode() {
        receiver = new Scanner(System.in);
        receiver.useDelimiter("\n");
    }
    /**
     * Переводит приложение в режим считывания данных из файла
     * @param bis Поток входных данных
     */
    public void setFileMode(BufferedInputStream bis) {
        receiver = new Scanner(bis);
        receiver.useDelimiter("\n");
    }
    public Scanner getReceiver() {
        return receiver;
    }
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }
}

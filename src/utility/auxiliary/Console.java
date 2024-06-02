package utility.auxiliary;

public class Console {
    private static Console instance;
    private Console() {}
    public static synchronized Console getInstance() {
        if (instance == null) {
            instance = new Console();
        }
        return instance;
    }
    public synchronized void printMessage(String message) {
        System.out.println(message);
    }
    public synchronized void printError(String error) {
        System.err.println(error);
    }
}

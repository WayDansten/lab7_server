package utility.management;

import utility.requests.MessageRequest;
import utility.requests.Request;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
public class ServerModule {
    private static ServerModule instance;
    private static Reader reader;
    private static Scanner scanner;
    private static final Invoker invoker = Invoker.getInstance();
    private final static File file = new File("data.csv");
    private ServerModule(){}
    public static ServerModule getInstance() {
        if (instance == null) {
            instance = new ServerModule();
        }
        return instance;
    }
    public void launch() throws IOException {
        reader = new InputStreamReader(System.in);
        scanner = new Scanner(reader);
        invoker.getCollectionManager().fillCollection(new BufferedInputStream(new FileInputStream(file)));
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5678));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        while (!handleAdminCommand()) {
            selector.selectNow();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                    System.out.println("Клиент подключен");
                }
                if (key.isReadable()) {
                    System.out.println("Получен запрос. Исполняю...");
                    String message;
                    try {
                        Request request = receiveRequest(key);
                        if (request != null) {
                            message = invoker.executeCommand(request);
                        } else {
                            break;
                        }
                    } catch (ClassNotFoundException e) {
                        message = "Команда не найдена!";
                    }
                    MessageRequest response = new MessageRequest(message);
                    byte[] serializedResponse = serialize(response);
                    try (SocketChannel client = (SocketChannel) key.channel()) {
                        ByteBuffer buffer = ByteBuffer.wrap(serializedResponse);
                        client.write(buffer);
                    }
                }
                iter.remove();
            }
        }
    }
    private static Request receiveRequest(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(9192);
        int r;
        try {
            r = client.read(buffer);
        } catch (IOException e) {
            client.close();
            System.out.println("Соединение с клиентом окончено");
            invoker.getCollectionManager().saveCollection(file);
            return null;
        }
        if (r == -1) {
            client.close();
            System.out.println("Соединение с клиентом окончено");
            invoker.getCollectionManager().saveCollection(file);
            return null;
        }
        else {
            return (Request) deserialize(buffer.array());
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }
    public static Serializable deserialize(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Serializable) ois.readObject();
    }
    public static byte[] serialize(Object object) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(object);
        return out.toByteArray();
    }
    private static boolean handleAdminCommand() throws IOException {
        if (reader.ready()) {
            String commandKey = scanner.next();
            if (commandKey.equals("save")) {
                invoker.getCollectionManager().saveCollection(file);
                System.out.println("Коллекция успешно сохранена");
                return false;
            } else if (commandKey.equals("shutdown")) {
                System.out.println("Завершение работы сервера...");
                return true;
            } else {
                System.out.println("Неизвестная команда админа!");
                return false;
            }
        }
        return false;
    }
}

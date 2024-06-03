package utility.management;

import utility.auxiliary.ExecutionTask;
import utility.auxiliary.SendingTask;
import utility.auxiliary.Serializer;
import utility.auxiliary.Console;
import utility.handlers.RequestExecutor;
import utility.handlers.RequestReader;
import utility.handlers.RequestSender;
import utility.requests.Request;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

public class ServerModule {
    private static ServerModule instance;
    private static Reader reader;
    private static Scanner scanner;
    private static final DBQueryManager dbManager = DBQueryManager.getInstance();
    private static final CommandExecutionManager commandManager = CommandExecutionManager.getInstance();
    private final static File file = new File("data.csv");
    private final static HashMap<String, String> userData = new HashMap<>();
    private final static int NUMBER_OF_THREADS = 50;
    private final ExecutorService readPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private final ExecutorService sendPool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    ForkJoinPool executionPool = ForkJoinPool.commonPool();
    private ServerModule(){}
    public static ServerModule getInstance() {
        if (instance == null) {
            instance = new ServerModule();
        }
        return instance;
    }
    public void launch() throws IOException {
        try {
            BlockingQueue<ExecutionTask> executionTasks = new LinkedBlockingQueue<>();
            BlockingQueue<SendingTask> sendingTasks = new LinkedBlockingQueue<>();
            BlockingQueue<SelectionKey> readableKeys = new LinkedBlockingQueue<>();
            ArrayList<SelectionKey> processedKeys = new ArrayList<>();
            reader = new InputStreamReader(System.in);
            scanner = new Scanner(reader);
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app?user=app&password=app&ssl=false");
            dbManager.setInstanceConnection(connection);
            dbManager.prepareDB();
            commandManager.getCollectionManager().fillCollection();
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
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            register(selector, serverSocket);
                            Console.getInstance().printMessage("Клиент подключен");
                        }
                        if (key.isReadable() && !readableKeys.contains(key)) {
                            readableKeys.add(key);
                        }
                        iter.remove();
                    }
                }
                readPool.execute(new RequestReader(readableKeys, executionTasks, processedKeys));
                executionPool.execute(new RequestExecutor(executionTasks, sendingTasks, commandManager));
                sendPool.execute(new RequestSender(sendingTasks, processedKeys));
                if (handleAdminCommand()) {
                    break;
                }
            }
        } catch (SQLException e) {
            Console.getInstance().printError("Ошибка соединения с базой данных!");
        } catch (ClassNotFoundException e) {
            Console.getInstance().printError("Драйвер не найден!");
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
            Console.getInstance().printMessage("Соединение с клиентом окончено");
            return null;
        }
        if (r == -1) {
            client.close();
            Console.getInstance().printMessage("Соединение с клиентом окончено");
            return null;
        }
        else {
            return (Request) Serializer.deserialize(buffer.array());
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }
    private static boolean handleAdminCommand() throws IOException {
        if (reader.ready()) {
            String commandKey = scanner.next();
            if (commandKey.equals("shutdown")) {
                Console.getInstance().printMessage("Завершение работы сервера...");
                return true;
            } else {
                Console.getInstance().printMessage("Неизвестная команда админа!");
                return false;
            }
        }
        return false;
    }
    public HashMap<String, String> getUserData () {
        return userData;
    }
}

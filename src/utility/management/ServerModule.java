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
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class ServerModule {
    private static ServerModule instance;
    private static final Invoker invoker = Invoker.getInstance();
    private ServerModule(){}
    public static ServerModule getInstance() {
        if (instance == null) {
            instance = new ServerModule();
        }
        return instance;
    }
    public void launch() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", 5678));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    register(selector, serverSocket);
                    System.out.println("Клиент подключился");
                }
                if (key.isReadable()) {
                    System.out.println("Получен запрос. Исполняю...");
                    String message;
                    try {
                        Request request = receiveRequest(buffer, key);
                        if (request != null) {
                            message = invoker.executeCommand(request);
                        } else {
                            message = "Команда не найдена!";
                        }
                    } catch (ClassNotFoundException e) {
                        message = "Команда не найдена!";
                    }
                    MessageRequest response = new MessageRequest(message);
                    byte[] serializedResponse = serialize(response);
                    try (SocketChannel client = (SocketChannel) key.channel()) {
                        buffer.flip();
                        buffer.clear();
                        client.write(buffer.put(serializedResponse));
                    }
                }
                iter.remove();
            }
        }
    }
    private static Request receiveRequest(ByteBuffer buffer, SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel client = (SocketChannel) key.channel();
        int r = client.read(buffer);
        if (r == -1 || new String(buffer.array()).trim().equals("exit")) {
            client.close();
            System.out.println("Соединение с клиентом окончено");
            return null;
        }
        else {
            System.out.println(Arrays.toString(buffer.array()));
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
}

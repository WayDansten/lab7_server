package utility.handlers;

import utility.auxiliary.SendingTask;
import utility.auxiliary.Console;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import static utility.auxiliary.Serializer.serialize;

public class RequestSender implements Runnable {
    private final BlockingQueue<SendingTask> sendingTasks;
    private final ArrayList<SelectionKey> processedKeys;
    public RequestSender(BlockingQueue<SendingTask> sendingTasks, ArrayList<SelectionKey> processedKeys) {
        this.sendingTasks = sendingTasks;
        this.processedKeys = processedKeys;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SendingTask task = sendingTasks.take();
                byte[] serializedResponse = serialize(task.response());
                try (SocketChannel client = (SocketChannel) task.key().channel()) {
                    ByteBuffer buffer = ByteBuffer.wrap(serializedResponse);
                    client.write(buffer);
                    processedKeys.remove(task.key());
                    Console.getInstance().printMessage("Послано");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                Console.getInstance().printError("Ошибка подключения к клиенту!");
            }
        }
    }
}

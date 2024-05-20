package utility.handlers;

import utility.auxiliary.SendingTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;

import static utility.auxiliary.Serializer.serialize;

public class RequestSender implements Runnable {
    private final BlockingQueue<SendingTask> sendingTasks;
    public RequestSender(BlockingQueue<SendingTask> sendingTasks) {
        this.sendingTasks = sendingTasks;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                SendingTask task = sendingTasks.take();
                SelectionKey key = task.key();
                byte[] serializedResponse = serialize(task.response());
                try (SocketChannel client = (SocketChannel) key.channel()) {
                    ByteBuffer buffer = ByteBuffer.wrap(serializedResponse);
                    client.write(buffer);
                    System.out.println("Послано");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                System.err.println("Ошибка подключения к клиенту!");
            }
        }
    }
}

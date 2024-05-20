package utility.handlers;

import utility.auxiliary.ExecutionTask;
import utility.auxiliary.Serializer;
import utility.requests.MessageRequest;
import utility.requests.Request;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;

public class RequestReader implements Runnable {
    private final BlockingQueue<SelectionKey> readableKeys;
    private final BlockingQueue<ExecutionTask> executionTasks;
    public RequestReader(BlockingQueue<SelectionKey> readableKeys, BlockingQueue<ExecutionTask> executionTasks) {
        this.executionTasks = executionTasks;
        this.readableKeys = readableKeys;
    }
    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            SelectionKey key;
            try {
                key = readableKeys.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(9192);
            int r;
            try {
                r = client.read(buffer);
                if (r == -1) {
                    client.close();
                    System.out.println("Соединение с клиентом окончено");
                }
                else {
                    try {
                        Request request;
                        try {
                            request = (Request) Serializer.deserialize(buffer.array());
                        } catch (ClassNotFoundException e) {
                            request = new MessageRequest("Несуществующая команда!");
                        }
                        System.out.println("Считано");
                        executionTasks.put(new ExecutionTask(request, key));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } catch (IOException e) {
                try {
                    client.close();
                } catch (IOException ex) {
                    System.out.print("");
                }
                System.out.println("Соединение с клиентом окончено");
            }
        }
    }
}

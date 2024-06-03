package utility.handlers;

import utility.auxiliary.ExecutionTask;
import utility.auxiliary.SendingTask;
import utility.auxiliary.Console;
import utility.management.CommandExecutionManager;
import utility.requests.MessageRequest;

import java.util.concurrent.BlockingQueue;

public class RequestExecutor implements Runnable {
    private final BlockingQueue<ExecutionTask> executionTasks;
    private final BlockingQueue<SendingTask> sendingTasks;
    private final CommandExecutionManager commandManager;
    public RequestExecutor(BlockingQueue<ExecutionTask> executionTasks, BlockingQueue<SendingTask> sendingTasks, CommandExecutionManager commandManager) {
        this.executionTasks = executionTasks;
        this.sendingTasks = sendingTasks;
        this.commandManager = commandManager;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ExecutionTask task = executionTasks.take();
                if (task.request() instanceof MessageRequest) {
                    sendingTasks.put(new SendingTask((MessageRequest) task.request(), task.key()));
                } else {
                    String message = commandManager.executeCommand(task.request());
                    sendingTasks.put(new SendingTask(new MessageRequest(message), task.key()));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

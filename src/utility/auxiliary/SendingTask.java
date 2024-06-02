package utility.auxiliary;

import utility.requests.MessageRequest;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public record SendingTask(MessageRequest response, SelectionKey key) {
}

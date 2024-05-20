package utility.auxiliary;

import utility.requests.MessageRequest;

import java.nio.channels.SelectionKey;

public record SendingTask(MessageRequest response, SelectionKey key) {
}

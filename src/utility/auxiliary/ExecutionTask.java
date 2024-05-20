package utility.auxiliary;

import utility.requests.Request;

import java.nio.channels.SelectionKey;

public record ExecutionTask(Request request, SelectionKey key) {
}

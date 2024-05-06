package utility.requests;

public class MessageRequest extends Request {
    private final String message;
    public MessageRequest(String message) {
        this.message = message;
    }
    public String[] extract() {
        return new String[]{message};
    }
}

package utility.requests;

public class RequestForData extends Request {
    private final String commandKey;
    private final String argument;
    public RequestForData(String commandKey, String argument) {
        this.argument = argument;
        this.commandKey = commandKey;
    }
    @Override
    public String[] extract(){
        return new String[]{commandKey, argument};
    }
}

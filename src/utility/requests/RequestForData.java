package utility.requests;

import utility.auxiliary.UserData;

public class RequestForData extends Request {
    private final String commandKey;
    private final String argument;
    public RequestForData(String commandKey, String argument, UserData userData) {
        this.argument = argument;
        this.commandKey = commandKey;
        this.userData = userData;
    }
    @Override
    public String[] extract(){
        return new String[]{commandKey, argument, userData.login()};
    }
}

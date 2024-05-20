package utility.requests;

import exceptions.ErrorInFunctionException;
import stored_classes.Flat;
import utility.auxiliary.UserData;
import utility.builders.FlatBuilder;
import utility.management.InputManager;

public class RequestWithFlatCreation extends Request {
    private final String commandKey;
    private final String argument;
    private Flat extraArgument;
    public RequestWithFlatCreation(String commandKey, String argument, UserData userData) {
        this.argument = argument;
        this.commandKey = commandKey;
        this.userData = userData;
    }
    public void addExtraArgument() throws ErrorInFunctionException {
       extraArgument = new FlatBuilder(InputManager.getInstance().getReceiver()).build();
    }
    @Override
    public String[] extract(){
        return new String[]{commandKey, argument, userData.login()};
    }
    public Flat getExtraArgument() {
        return extraArgument;
    }
}

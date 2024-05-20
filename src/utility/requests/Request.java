package utility.requests;

import utility.auxiliary.UserData;

import java.io.Serializable;

public abstract class Request implements Serializable {
    UserData userData;
    public abstract String[] extract();
    public UserData getUserData() {
        return userData;
    }
}

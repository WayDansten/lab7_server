package utility.requests;

import utility.auxiliary.UserData;

public class AuthorizationRequest extends Request {
    private final UserData userData;
    private final boolean registrationFlag;
    public AuthorizationRequest(String login, String password, boolean flag) {
        userData = new UserData(login, password);
        this.registrationFlag = flag;
    }
    @Override
    public String[] extract() {
        return new String[]{userData.login(), userData.password()};
    }

    public boolean getRegistrationFlag() {
        return registrationFlag;
    }

    public UserData getUserData() {
        return userData;
    }
}

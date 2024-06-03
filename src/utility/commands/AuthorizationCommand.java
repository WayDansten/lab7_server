package utility.commands;

import utility.management.CollectionManager;
import utility.management.DBQueryManager;

public class AuthorizationCommand extends Command{
    CollectionManager cm;
    private boolean regFlag;
    public AuthorizationCommand(CollectionManager cm) {
        this.cm = cm;
    }
    @Override
    public String execute(String... args) {
        if (regFlag) {
             if (DBQueryManager.getInstance().registerUser(userData.login(), userData.password())) {
                 return "Авторизация успешна! Вы зарегистрировались!";
             } else {
                 return "Пользователь с данным логином уже существует!";
             }
        } else {
            if (DBQueryManager.getInstance().checkUserPassword(userData.login(), userData.password())) {
                return "Авторизация успешна! Вы вошли в систему!";
            } else {
                return "Введен неверный пароль или не найден пользователь с данным логином!";
            }
        }
    }
    public void setRegFlag(boolean regFlag) {
        this.regFlag = regFlag;
    }
}

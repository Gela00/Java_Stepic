package frontend;

import base.AccountService;
import base.UserProfile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//для управления пользователями
public class AccountServiceImpl implements AccountService { //реализация всех методов, объявленных в интерфейсе
    //коллекция для хранения зарегистрированных пользователей.
    //ключ - строка (логин пользователя),
    // значением — объект UserProfile, содержащий информацию о пользователе (логин и пароль).
    private final Map<String, UserProfile> signedUpUsers = new ConcurrentHashMap<>();

    @Override
    //Регистрация нового юзера
    public void signUp(String login, String password) {
        //объект UserProfile с переданными логином и паролем,
        //добавляется в коллекцию signedUpUsers под логином
        signedUpUsers.put(login, new UserProfile(login, password));
    }
    @Override
    //Вход юзера в систему
    public boolean signIn(String login, String password) {
        //Извлекает объект UserProfile по логину из коллекции signedUpUsers.
        UserProfile profile = signedUpUsers.get(login);
        // Возвращает true, если профиль существует и пароль совпадает
        return profile != null && profile.getPassword().equals(password);
    }
}

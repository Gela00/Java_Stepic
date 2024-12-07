package frontend;

import base.AccountService;
import base.DBService;
import base.UserProfile;
import dbService.DBException;

//для управления пользователями
public class AccountServiceImpl implements AccountService {
    private final DBService dbService;
    public AccountServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    //Регистрация нового юзера
    public void singUp(String login, String password) {
        try
        {
            dbService.addUser(new UserProfile(login, password));
        }

        catch (DBException e)
        {
            System.out.println("Can't sign up: " + e.getMessage());
        }
    }

    @Override
    //Вход юзера в систему
    public boolean singIn(String login, String password) {
        //UserProfile profile = signedUpUsers.get(login);
        //return profile != null && profile.getPassword().equals(password);
        try
        {
            // Профиль пользователя берется из бд
            UserProfile profile = dbService.getUser(login);
            return profile != null && profile.getPassword().equals(password);
        }

        catch (DBException e)
        {
            System.out.println("Can't sign in: " + e.getMessage());
            return false;
        }
    }
}

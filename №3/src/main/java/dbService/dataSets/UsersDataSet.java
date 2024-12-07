package dbService.dataSets;

//@SuppressWarnings("UnusedDeclaration")
public class UsersDataSet {
    private final long id;
    private final String login;
    private final String password;

    public UsersDataSet(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    // Для получения логина
    public String getLogin() {
        return login;
    }

    // Для получения пароля
    public String getPassword() {
        return password;
    }

    // Для получения айди
    public long getId() {
        return id;
    }
}

package dbService;

import base.DBService;
import base.UserProfile;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Класс DBServiceImpl реализует интерфейс DBService
public class DBServiceImpl implements DBService {
    private final Connection connection; //для хранения соединения с базой данных

    public DBServiceImpl() {
        this.connection = getH2Connection(); // Получение соединения H2
    }

    // Создать таблицы пользователей если ее нет
    public void create() throws DBException {
        try {
            System.out.println("Creating table users if needed");
            (new UsersDAO(connection)).createTable();// Таблица через DAO
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    // Получение профиля пользователя по логину
    public UserProfile getUser(String login) throws DBException {
        try
        {
            UsersDAO dao = new UsersDAO(connection); // Создание DAO для работы с пользователями
            UsersDataSet dataSet = dao.get(login); // загружаем набор данных пользователя
            return new UserProfile(dataSet.getLogin(), dataSet.getPassword()); //новый профиль
        }
        catch (SQLException e)
        {
            throw new DBException(e);
        }
    }

    // Добавление нового пользователя в бд
    public long addUser(UserProfile userProfile) throws DBException {
        try {
            connection.setAutoCommit(false); //  выкл авто коммита
            UsersDAO dao = new UsersDAO(connection); // Создание DAO для работы с п
            dao.insertUser(userProfile); // Вставка нового п
            connection.commit(); // Коммит транзакции
            return dao.getUserId(userProfile.getLogin()); //ID нового п
        }

        catch (SQLException e)
        {
            try {
                connection.rollback(); // Откат если ошибка
            }
            catch (SQLException ignore) {
                // Игнорируем ошибки при откате
            }
            // Обработка исключений, связанных с SQL
            throw new DBException(e);
        }

        finally {
            try {
                connection.setAutoCommit(true); // Вкл авто коммит
            } catch (SQLException ignore) {
                // Игнорируем ошибки при вкл авто коммита
            }
        }
    }

    // Метод для проверки состояния соединения и количества пользователей
    public void check() throws DBException {
        try {
            // Вывод информации о драйвере
            System.out.println("Driver name: " + connection.getMetaData().getDriverName());
            System.out.println("Driver version: " + connection.getMetaData().getDriverVersion());

            UsersDAO dao = new UsersDAO(connection);
            int count = dao.getUsersCount(); // Получение колво пользователей
            System.out.println("Count of records in users: " + count);
        } catch (SQLException e) {
            // Обработка исключений, связанных с SQL
            throw new DBException(e);
        }
    }

    // Метод для очистки таблицы пользователей
    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.cleanup(); // Очистка таблицы
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    // Получение соединения с бд H2
    private static Connection getH2Connection() {
        try {
            // URL, имя пользователя и пароль для подключения к базе данных
            String url = "jdbc:h2:./h2db";
            String name = "tully";
            String pass = "tully";

            // Создание источника данных H2
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            // Получение соединения через DriverManager
            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection; // Возврат соединения
        }
        catch (SQLException e)
        {
            e.printStackTrace(); // Вывод стека ошибок в случае исключения
        }
        return null; // Возврат null, если соединение не удалось получить
    }

}

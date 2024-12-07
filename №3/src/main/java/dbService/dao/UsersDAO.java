package dbService.dao;

import base.UserProfile;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

// Взаимодействие с таблицей пользователей в бд
public class UsersDAO {
    private Executor executor;

    //соединение с базой данных
    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    // Получения данных  по логину
    public UsersDataSet get(String login) throws SQLException {
        // Выполнение SQL для получения данных
        return executor.execQuery("select * from users where login='" + login + "'", result -> {
            result.next(); // Переход к первой строке результата
            // Создание и возврат объекта UsersDataSet с данными из ResultSet
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    // для получения id по логину
    public long getUserId(String login) throws SQLException {
        return executor.execQuery("select id from users where login='" + login + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    // Для получения количества пользователей в таблице
    public int getUsersCount() throws SQLException {
        return executor.execQuery("select count(id) from users", result -> {
            result.next();
            return result.getInt(1);
        });
    }

    // Для вставки нового пользователя в таблицу
    public void insertUser(UserProfile profile) throws SQLException {
        executor.execUpdate("insert into users (login, password) values ('" + profile.getLogin() + "','" + profile.getPassword() + "')");
    }

    // Для создания таблицы пользователей если ее нет
    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id))");
    }

    // Удаления таблицы
    public void cleanup() throws SQLException {
        executor.execUpdate("drop table users");
    }
}

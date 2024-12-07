package dbService.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Dыполнение SQL-запросов к базе данных
public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    // Запросы insert update delete
    public void execUpdate(String update) throws SQLException {
        Statement statment = connection.createStatement();// Statement нужен для выполнения запроса
        // Выполнение запроса
        statment.execute(update);
        // Закрытие Statement
        statment.close();
    }

    // Запросы SELECT
    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet(); // результаты запроса
        // Обработка результата с помощью переданного обработчика
        T value = handler.handle(result);
        result.close();
        stmt.close();
        return value;
    }
}

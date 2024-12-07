package dbService.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

//под интерфейс
public interface ResultHandler<T> {
	T handle(ResultSet resultSet) throws SQLException;
}

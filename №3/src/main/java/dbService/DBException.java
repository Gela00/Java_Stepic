package dbService;

//расширение DBException
public class DBException extends Exception {
    public DBException(Throwable throwable) {
        super(throwable);
    }
}

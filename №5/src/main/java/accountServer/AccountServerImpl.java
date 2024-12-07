package accountServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountServerImpl implements AccountServer {
    static final Logger logger = LogManager.getLogger(AccountServerImpl.class.getName());

    private final static int DEFAULT_LIMIT = 10; //лимит пользователей по умолчанию
    private int usersLimit;

    public AccountServerImpl() {
        this.usersLimit = DEFAULT_LIMIT;
    }

    @Override
    public int getUsersLimit() {
        return usersLimit;
    }

    // Установка нового лимита
    @Override
    public void setUsersLimit(int usersLimit) {
        logger.info("Set limit to: " + usersLimit);
        this.usersLimit = usersLimit;
    }
}

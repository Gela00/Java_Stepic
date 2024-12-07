package servlets;

import accountServer.AccountServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {
    public static final String PAGE_URL = "/admin";
    private final AccountServer accountServer; //ссылки на сервер аккаунтов

    public AdminPageServlet(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

    // обработка GETзапросов
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        int limit = accountServer.getUsersLimit();
        response.getWriter().println(limit);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}

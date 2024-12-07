package frontend.servlets;

import base.AccountService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Регистрация пользователей
public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //Для обработки POST запросов
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String password = request.getParameter("password");


        accountService.singUp(login, password);  //Вызов метода регистрации

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("SignedUp");

        response.setStatus(HttpServletResponse.SC_OK);
    }
}

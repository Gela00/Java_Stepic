package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MirrorRequestServlet extends HttpServlet { //для работы с сервлетами и http

    public void doGet(HttpServletRequest request, //HTTP-запрос и ответ
                      HttpServletResponse response) throws ServletException, IOException {

        //response.setContentType("text/html;charset=UTF-8");

        String value = request.getParameter("key"); //Извлекает параметр key из запроса, если существует выводить в ответ.
        if (value != null)
            response.getWriter().println(value);

        response.setStatus(value.isEmpty() ?/* если истинно, то первое действие */ HttpServletResponse.SC_FORBIDDEN : HttpServletResponse.SC_OK);
        //SC_FORBIDDEN - статус HTTP 403
        //SC_OK - статус HTTP 200 (OK)

        response.getWriter().write(value);
    }
}

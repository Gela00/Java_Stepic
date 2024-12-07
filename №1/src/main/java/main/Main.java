package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.MirrorRequestServlet;

public class Main {
    public static void main(String[] args) throws Exception {

        MirrorRequestServlet mirrorServlet = new MirrorRequestServlet();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS); //настройка контекста для поддержания сессии
        context.addServlet(new ServletHolder(mirrorServlet), "/mirror");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        System.out.println("Server started");
        server.join();
    }
}

package main;

import accountServer.ResourceServer;
import accountServer.ResourceServerController;
import accountServer.ResourceServerControllerMBean;
import accountServer.ResourceServerImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ResourcePageServlet;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;

public class Main {
    // Создание логгера  Log4j
    private static final Logger logger = configureLog4j();

    // Метод для настройки Log4j
    private static Logger configureLog4j() {
        // Получение контекста логирования
        LoggerContext context = (LoggerContext) LogManager.getContext();
        Configuration config = context.getConfiguration();

        // формат вывода логов
        PatternLayout layout = PatternLayout.createLayout("%m%n", null, null, Charset.defaultCharset(), false, false, null, null);
        // аппендер для вывода логов
        Appender appender = ConsoleAppender.createAppender(layout, null, null, "CONSOLE_APPENDER", null, null);
        appender.start();

        // ссфлка на аппендер
        AppenderRef ref = AppenderRef.createAppenderRef("CONSOLE_APPENDER", null, null);
        AppenderRef[] refs = new AppenderRef[]{ref};

        // Создание конфигурации логгера
        LoggerConfig loggerConfig = LoggerConfig.createLogger("false", Level.INFO, "CONSOLE_LOGGER", "com", refs, null, null, null);
        loggerConfig.addAppender(appender, null, null);

        // Добавление аппендера в конфигурацию
        config.addAppender(appender);
        config.addLogger("Main.class", loggerConfig);
        context.updateLoggers(config);
        return LogManager.getContext().getLogger("Main.class");
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        logger.info("Starting at http://127.0.0.1:" + port);
        ResourceServer resourceServer = new ResourceServerImpl();

        // управление сервером
        createMBean(resourceServer);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ResourcePageServlet(resourceServer)), ResourcePageServlet.PAGE_URL);
        ResourceHandler resource_handler = new ResourceHandler();

        // Создание списка обработчиков
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");
        server.join();
    }

    // Создание и регистрация MBean
    private static void createMBean(ResourceServer resourceServer) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        ResourceServerControllerMBean serverStatistics = new ResourceServerController(resourceServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=ResourceServerController");//имя для MBean
        mbs.registerMBean(serverStatistics, name);// Регистрация на сервере
    }
}

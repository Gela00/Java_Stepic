package chat;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"}) // Регистрация сервлета с именем и URL-шаблоном
public class WebSocketChatServlet extends WebSocketServlet { // Определяем класс WebSocketChatServlet, наследующий WebSocketServlet
    private final static int LOGOUT_TIME = 10 * 60 * 1000; // Время ожидания (тайм-аут) для отключения неактивных пользователей (10 минут)
    private final ChatService chatService; // Ссылка на сервис чата для управления соединениями

    public WebSocketChatServlet() {
        this.chatService = new ChatService();
    }

    // Для настройки WebSocketServletFactory
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME); //таймаут для неактивных соединений
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService)); //  WebSocket создаеь экземпляры ChatWebSocket
    }
}

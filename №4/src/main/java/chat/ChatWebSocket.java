package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
@SuppressWarnings("UnusedDeclaration") // Предупреждение компилятора о неиспользуемых объявлениях


@WebSocket
public class ChatWebSocket {
    private ChatService chatService;
    private Session session;

    public ChatWebSocket(ChatService chatService) {
        this.chatService = chatService;
    }

    //Вызывается при подключении нового WebSocket
    @OnWebSocketConnect
    public void onOpen(Session session) {
        chatService.add(this);
        this.session = session;
    }

    //Вызывается при получении сообщения от клиента
    @OnWebSocketMessage
    public void onMessage(String data) {
        chatService.sendMessage(data);
    }

    // при закрытии WebSocket
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        chatService.remove(this);
    }

    // Отправка строки через WebSocket
    public void sendString(String data) {
        try
        {
            session.getRemote().sendString(data); // Отправляем данные клиенту
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

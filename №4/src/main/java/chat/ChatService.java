package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
//добавление и удаление пользователей, возможность отправки сообщений всем подключенным пользователям

public class ChatService {
    private Set<ChatWebSocket> webSockets; // Объявляем множество для хранения активных WebSocket-соединений

    public ChatService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());//ConcurrentHashMap нужно для потокобезопасности
    }

    // Отправка сообщения всем пользователям
    public void sendMessage(String data) {
        for (ChatWebSocket user : webSockets) {// идем по каждому активному WebSocket
            try
            {
                user.sendString(data);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    // добавление нового вебсоединения
    public void add(ChatWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    // удаление
    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }
}

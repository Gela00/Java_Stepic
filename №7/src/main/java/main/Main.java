package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final long TIME_TO_WORK = TimeUnit.SECONDS.toMillis(10); //время работы сервера
    private static final int CLIENTS_NUMBER = 1; //макс кол-во клиентов

    public static void main(String[] args) throws Exception {
        int port = 5050;
        ExecutorService executor = Executors.newFixedThreadPool(CLIENTS_NUMBER); // Создать пул потоков с кол-вом потоков что равны клиентам

        try (ServerSocket serverSocket = new ServerSocket(port)) { //ServerSocket мнужен для прослушивания входящих подключений
            System.out.println("Server started on port: " + port);
            long startTime = new Date().getTime(); //записать начало работы сервера

            // Основной цикл сервера, работает в течение TIME_TO_WORK миллисекунд
            while (new Date().getTime() < startTime + TIME_TO_WORK) {
                // Принимаем входящее соединение и передаем его в новый Runnable для обработки
                executor.submit(new MirrorSocketRunnable(serverSocket.accept()));
            }
        }

        executor.awaitTermination(1, TimeUnit.MINUTES); //ожидание завершение в теч минуты
        executor.shutdown();
        System.out.println("Bye.");
    }

    // Обработка соединений с клиентами
    private static class MirrorSocketRunnable implements Runnable {
        private final Socket clientSocket; // Сокет для общения с клиентом

        private MirrorSocketRunnable(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        // Обработка входящих и исходящих сообщений
        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String inputLine;
                String outputLine;
                int lineIndex = 0; // Счет обработанных строк

                while ((inputLine = in.readLine()) != null) {
                    outputLine = inputLine; // отправляем обратно то же сообщение
                    out.println(outputLine); // Отправляем ответ клиенту
                    ++lineIndex;
                    if (outputLine.equals("Bye."))
                        break;
                }
                System.out.println("Lines processed: " + lineIndex);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}

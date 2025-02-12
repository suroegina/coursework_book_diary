package otus.java.basic.coursework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);
    private int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {
        Settings settings = new Settings();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            ExecutorService service = Executors.newFixedThreadPool(settings.getThreadNumbers());
            while (true) {
                Socket socket = serverSocket.accept();
                service.execute(() -> {
                    try {
                        System.out.println("Подключился новый клиент");
                        byte[] buffer = new byte[settings.getMaxReqSize()];
                        int n = socket.getInputStream().read(buffer);
                        HttpRequest request = new HttpRequest(new String(buffer, 0, n));
                        request.info(true);
                        dispatcher.execute(request, socket.getOutputStream());
                    } catch (IOException e) {
                        LOGGER.error("ERROR: ", e);
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            LOGGER.error("ERROR: ", e);
                        }
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("ERROR: ", e);
        }
    }
}

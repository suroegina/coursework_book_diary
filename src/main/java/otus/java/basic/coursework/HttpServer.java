package otus.java.basic.coursework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.application.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final Logger LOGGER = LogManager.getLogger(HttpServer.class);
    private int port;
    private Dispatcher dispatcher;
    private ExecutorService executorService;

    private ThreadLocal<byte[]> requestBuffer;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        Settings settings = new Settings();
        executorService = Executors.newFixedThreadPool(settings.getThreadNumbers());
        requestBuffer = ThreadLocal.withInitial(() -> new byte[settings.getMaxReqSize()]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Сервер запущен на порту: {}", port);
            this.dispatcher = new Dispatcher();
            Storage.init();
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> executeRequest(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private void executeRequest(Socket socket) {
        try {
            byte[] buffer = requestBuffer.get();
            int n = socket.getInputStream().read(buffer);
            if (n > 0) {
                String rawRequest = new String(buffer, 0, n);
                HttpRequest request = new HttpRequest(rawRequest);
                dispatcher.execute(request, socket.getOutputStream());
                socket.getOutputStream().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

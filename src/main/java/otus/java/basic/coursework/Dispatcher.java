package otus.java.basic.coursework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.application.BookService;
import otus.java.basic.coursework.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Dispatcher {
    private static final Logger LOGGER = LogManager.getLogger(Dispatcher.class);

    private Map<String, RequestProcessor> router;
    private ExceptionProcessor exceptionProcessor;


    public Dispatcher() {
        BookService bookService = new BookService();
        this.router = new HashMap<>();
        this.router.put("GET /welcome", new WelcomeProcessor());
        this.router.put("GET /book", new GetBookProcessor(bookService));
        this.router.put("POST /book", new CreateBookProcessor(bookService));
        this.router.put("DELETE /book", new DeleteBookProcessor(bookService));
        this.router.put("PUT /book", new UpdateBookProcessor(bookService));
        this.exceptionProcessor = new ExceptionProcessor();

    }

    public void execute(HttpRequest request, OutputStream output) throws IOException {
        try {
            if (!router.containsKey(request.getRoutingKey())) {
                Set<String> setKeys = router.keySet();
                for(String k: setKeys){
                    if (k.indexOf(request.getRoutingKey().split("/")[1]) != -1) {
                        exceptionProcessor.start(405, request, output);
                        return;
                    }
                }
                exceptionProcessor.start(404, request, output);
                return;
            }
            router.get(request.getRoutingKey()).execute(request, output);
        } catch (BadRequestException e) {
            LOGGER.error(e.getMessage());
            request.setErrorCause(e);
            exceptionProcessor.start(400, request, output);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            exceptionProcessor.start(500, request, output);
        }
    }
}

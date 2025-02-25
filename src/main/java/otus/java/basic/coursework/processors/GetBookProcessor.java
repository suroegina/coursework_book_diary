package otus.java.basic.coursework.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.HttpRequest;
import otus.java.basic.coursework.application.Book;
import otus.java.basic.coursework.application.BookService;
import otus.java.basic.coursework.application.Storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

public class GetBookProcessor implements RequestProcessor{
    private static final Logger LOGGER = LogManager.getLogger(GetBookProcessor.class);
    private BookService bookService;

    public GetBookProcessor(BookService bookService) {
        this.bookService = bookService;
    }


    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (request.containsParameter("id")) {
            Long id = Long.parseLong(request.getParameter("id"));
            LOGGER.info("ИД = " + id);
            Book updateBook = bookService.getBookById(id);
            Storage.getBookById(id);
            Gson gson = new Gson();
            String jsonOutItem = gson.toJson(updateBook);
            LOGGER.debug("JSON TEXT: " + jsonOutItem);

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Access-Control-Allow-Origin: *\r\n" +
                    "\r\n" + jsonOutItem;
            output.write(response.getBytes(StandardCharsets.UTF_8));
        } else {
            Storage.init();
            List<Book> books = Storage.getBooks();
            Gson gson = new Gson();
            String jsonOutItem = gson.toJson(books);
            LOGGER.debug("JSON TEXT: " + jsonOutItem);

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Access-Control-Allow-Origin: *\r\n" +
                    "\r\n" + jsonOutItem;
            output.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}

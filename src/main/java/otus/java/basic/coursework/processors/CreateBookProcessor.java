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

public class CreateBookProcessor implements RequestProcessor{
    private static final Logger LOGGER = LogManager.getLogger(CreateBookProcessor.class);

    private BookService bookService;

    public CreateBookProcessor(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Gson gson = new Gson();
        LOGGER.debug("New BOOK REQUEST: " + request.getBody());
        Book newBook = gson.fromJson(request.getBody(), Book.class);
        LOGGER.debug("New BOOK: " + newBook.toString());
        bookService.createNewBook(newBook);
        LOGGER.info("Создание книги - ОК: " + newBook.toString());
        Storage.save(newBook);
        String jsonOutItem = gson.toJson(newBook);

        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Origin: *\r\n" +
                "\r\n" + jsonOutItem;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }

}

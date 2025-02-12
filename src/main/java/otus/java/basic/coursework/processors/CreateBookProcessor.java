package otus.java.basic.coursework.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.HttpRequest;
import otus.java.basic.coursework.application.Book;
import otus.java.basic.coursework.application.BookService;

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
        String response = "" +
                "HTTP/1.1 201 Created\r\n" +
                "Connect-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>CREATE BOOK</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }

}

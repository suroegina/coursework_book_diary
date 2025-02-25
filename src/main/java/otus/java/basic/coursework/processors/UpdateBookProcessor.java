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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class UpdateBookProcessor implements RequestProcessor{
    private static final Logger LOGGER = LogManager.getLogger(UpdateBookProcessor.class);

    private BookService bookService;

    public UpdateBookProcessor(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        try {
            String jsonResult = null;
            Gson gson = new Gson();
            Book updateBook = gson.fromJson(request.getBody(), Book.class);
            Long newId = updateBook.getId();
            LOGGER.info("Update Book ID  = " + newId);
            bookService.updateBook(updateBook);
            LOGGER.info("Обновление заголовка книги - ОК: " + updateBook.toString());
            jsonResult = gson.toJson(updateBook);
            Storage.update(updateBook);

            String response = "HTTP/1.1 200 OK\r\n" +
                    "Cache-Control: no-cache, no-store, must-revalidate\r\n" +
                    jsonResult +
                    "\r\n";
            output.write(response.getBytes());

        } catch (NoSuchElementException e) {
            String response = "" +
                    "HTTP/1.1 200 OK\r\n" +
                    "Connect-Type: text/html\r\n" +
                    "\r\n" +
                    "<html><body><h1>Book not found!</h1></body></html>";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            LOGGER.info("Обновление книги - Книга не найдена по ИД");
        }

    }
}

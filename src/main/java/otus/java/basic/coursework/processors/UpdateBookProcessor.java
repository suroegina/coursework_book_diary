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
            Book newBook = gson.fromJson(request.getBody(), Book.class);
            Long newId = newBook.getId();
            try {
                Book book = bookService.getBookById(newId);
                bookService.updateBook(newBook);
                LOGGER.info("Обновление заголовка книги - ОК: " + newBook.toString());
            } catch (NoSuchElementException e) {
                bookService.createNewBook(newBook);
                LOGGER.info("Обновление книги - Книги в списке нет. Создание: " + newBook.toString());
            }

            jsonResult = gson.toJson(newBook);

            String response = "" +
                    "HTTP/1.1 200 OK\r\n" +
                    "Connect-Type: application/json\r\n" +
                    "\r\n" +
                    jsonResult;
            output.write(response.getBytes(StandardCharsets.UTF_8));
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

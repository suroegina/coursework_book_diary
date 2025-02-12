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
        try {
            String jsonResult = null;
            Gson gson = new Gson();

            if (request.containsParameter("id")) {
                Long id = Long.parseLong(request.getParemeter("id"));
                LOGGER.info("ИД = " + id);
                Book book = bookService.getBookById(id);
                jsonResult = gson.toJson(book);
                LOGGER.info("Получение книги по ИД - ОК");
            } else {
                List<Book> books = bookService.getAllBooks();
                LOGGER.debug("books: " + books.toString());
                jsonResult = gson.toJson(books);
                LOGGER.info("Получение всех книг - ОК");
            }

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
            LOGGER.info("Получение книги - Книга не найдена по ИД");
        }
    }
}

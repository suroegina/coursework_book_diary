package otus.java.basic.coursework.processors;

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
            String Result = "";
            //Gson gson = new Gson();

            if (request.containsParameter("id")) {
                Long id = Long.parseLong(request.getParameter("id"));
                LOGGER.info("ИД = " + id);
                Book book = bookService.getBookById(id);
                //Result = gson.toJson(book);
                Result = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\"\n" +
                        "<head>\n" +
                        "<meta charset=\"UTF-8\">" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" +
                        "<title>Books</title>" +
                        "</head>\n" +
                        "<body>\n" +
                        "<h1>Книги</h1>\n" +
                        "<table>" +
                        "<thread>" +
                        "<tr>" +
                        "                        <th data-title=\"Название\">Название</td>\n" +
                        "                        <th data-title=\"Автор\">Автор</td>\n" +
                        "                        <th data-title=\"Описание\">Описание</td>" +
                        "</tr>"+
                        "</thread>" +
                        "<tbody>" +
                        "<tr>" +
                        "                        <td data-title=\"Название\">" + book.getTitle() + "</td>\n" +
                        "                        <td data-title=\"Автор\">" + book.getAuthor() + "</td>\n" +
                        "                        <td data-title=\"Описание\">" + book.getDescription() + "</td>" +
                        "</tr>" +
                        "</tbody>" +
                        "</table>" +
                        "</h2>\n" +
                        "</body>\n" +
                        "</html>";

                LOGGER.info("Получение книги по ИД - ОК");
            } else {
                List<Book> books = bookService.getAllBooks();
                LOGGER.debug("books: " + books.toString());
                //jsonResult = gson.toJson(books);
                String strBooks = "";
                for (Book b : books) {
                    strBooks = strBooks +
                            "<tr>" +
                            "                        <td data-title=\"Название\">" + b.getTitle() + "</td>\n" +
                            "                        <td data-title=\"Автор\">" + b.getAuthor() + "</td>\n" +
                            "                        <td data-title=\"Описание\">" + b.getDescription() + "</td>" +
                            "</tr>";
                }
                Result = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\"\n" +
                        "<head>\n" +
                        "<meta charset=\"UTF-8\">" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" +
                        "<title>Document</title>" +
                        "</head>\n" +
                        "<body>\n" +
                        "<h1>Книги</h1>\n" +
                        "<table>" +
                        "<thread>" +
                        "<tr>" +
                        "                        <th data-title=\"Название\">Название</td>\n" +
                        "                        <th data-title=\"Автор\">Автор</td>\n" +
                        "                        <th data-title=\"Описание\">Описание</td>" +
                        "</tr>"+
                        "</thread>" +
                        "<tbody>" +
                        strBooks +
                        "</tbody>" +
                        "</h2>\n" +
                        "</body>\n" +
                        "</html>";
                LOGGER.info("Получение всех книг - ОК");
            }

            String response = "" +
                    "HTTP/1.1 200 OK\r\n" +
                    "Connect-Type: application/json\r\n" +
                    "\r\n" +
                    Result;

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

package otus.java.basic.coursework;

import otus.java.basic.coursework.application.Book;

import java.sql.Date;
import java.util.List;

public interface BookServiceJDBC {
    List<Book> getAll();
    void addBook(Long id, String author, String title, int pages, java.sql.Date date_started, Date date_finished, String description, int format_id, int status_id, int rating, String notes);
    void deleteBook(Long id);
    void updateBook(Long id, String author, String title, int pages, String description, int rating, String notes);
}

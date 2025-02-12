package otus.java.basic.coursework.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.BookServiceJDBC;
import otus.java.basic.coursework.BookServiceJDBCImpl;

import java.util.Collections;
import java.util.List;

public class BookService {
    private static final Logger LOGGER = LogManager.getLogger(BookService.class);
    private List<Book> books;
    private BookServiceJDBC bookServiceJDBC = new BookServiceJDBCImpl();

    public BookService() {
        try {
            this.books = bookServiceJDBC.getAll();
            LOGGER.debug("bookServiceJDBC.getAll() = " + this.books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

    public Book getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().get();
    }

    public void createNewBook(Book book) {
        Long newId = books.stream().mapToLong(Book::getId).max().getAsLong() + 1;
        LOGGER.debug("NewId BOOK = " + newId);
        books.add(new Book(newId, book.getAuthor(), book.getTitle(), book.getPages(), book.getDateStarted(), book.getDateFinished(), book.getDescription(), book.getFormat(), book.getStatus(), book.getRating(), book.getNote()));
        bookServiceJDBC.addBook(newId, book.getAuthor(), book.getTitle(), book.getPages(), book.getDateStarted(), book.getDateFinished(), book.getDescription(), book.getFormat(), book.getStatus(), book.getRating(), book.getNote());
    }

    public void deleteBookById(Long id) {
        books.remove(getBookById(id));
        bookServiceJDBC.deleteBook(id);
    }

    public void deleteAllBooks() {
        books.clear();
    }

    public void updateBook(Book newBook) {
        Book book = getBookById(newBook.getId());
        String title = book.getTitle();
        String author = book.getAuthor();
        int pages = book.getPages();
        String description = book.getDescription();
        int rating = book.getRating();
        String notes = book.getNote();
        if (newBook.getTitle() != null) {
            title = newBook.getTitle();
            book.setTitle(title);
        }
        if (newBook.getAuthor() != null) {
            author = newBook.getAuthor();
            book.setAuthor(author);
        }
        if (newBook.getPages() != 0) {
            pages = newBook.getPages();
            book.setPages(pages);
        }
        if (newBook.getDescription() != null) {
            description = newBook.getDescription();
            book.setDescription(description);
        }
        if (newBook.getRating() != 0) {
            rating = newBook.getRating();
            book.setRating(rating);
        }
        if (newBook.getNote() != null) {
            notes = newBook.getNote();
            book.setNote(notes);
        }
        bookServiceJDBC.updateBook(newBook.getId(), author, title, pages, description, rating, notes);
    }
}

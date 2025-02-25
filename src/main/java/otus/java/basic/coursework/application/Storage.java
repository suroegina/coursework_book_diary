package otus.java.basic.coursework.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Storage {
    private static List<Book> books = new ArrayList<>();
    private static BookService bookService = new BookService();

    public static void init() {
        System.out.println("Хранилище проинициализировано");
        books = bookService.getAllBooks();

    }

    public static List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    public static Book getBookById(Long id) {
        return bookService.getBookById(id);
    }

    public static void save(Book book) {
        bookService.createNewBook(book);
    }

    public static void delete(Long id) {
        bookService.deleteBookById(id);
    }

    public static void update(Book book) {
        bookService.updateBook(book);
    }
}

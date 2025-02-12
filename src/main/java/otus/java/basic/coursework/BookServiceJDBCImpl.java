package otus.java.basic.coursework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import otus.java.basic.coursework.application.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookServiceJDBCImpl implements BookServiceJDBC{
    private static final Logger LOGGER = LogManager.getLogger(BookServiceJDBCImpl.class);

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/book_diary_db";
    private static final String BOOKS_QUERY = "select b.id, b.author, b.title, b.pages, b.date_started, b.date_finished, b.description, b.format_id, b.status_id, b.rating, b.notes from book b";

    private static final String BOOK_ADD_QUERY = "" +
            "insert into book (author, title, pages, date_started, date_finished, description, format_id, status_id, rating,notes, id) " +
            "values (?,?,?,?,?,?,?,?,?,?,?)";

    private static final String BOOK_UPDATE_QUERY = "update book set author = ?, title = ?, pages = ?, description = ?, rating = ?, notes = ? where id = ?";

    private static final String BOOK_DELETE_QUERY = "delete from book where id = ?";


    private Connection connection;

    public BookServiceJDBCImpl() {
        try {
            LOGGER.info("Подключение к БД....");
            this.connection = DriverManager.getConnection(DATABASE_URL, "admin", "admin");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

    }

    @Override
    public List<Book> getAll() {
        List<Book> allBooks = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            try (ResultSet resultSet = statement.executeQuery(BOOKS_QUERY)) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String author = resultSet.getString("author");
                    String title = resultSet.getString("title");
                    int pages = resultSet.getInt("pages");
                    Date date_started = resultSet.getDate("date_started");
                    Date date_finished = resultSet.getDate("date_finished");
                    String description = resultSet.getString("description");
                    int format = resultSet.getInt("format_id");
                    int status = resultSet.getInt("status_id");
                    int rating = resultSet.getInt("rating");
                    String notes = resultSet.getString("notes");
                    Book book = new Book(id, author, title, pages, date_started, date_finished, description, format, status, rating, notes);
                    allBooks.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return allBooks;
    }


    @Override
    public void addBook(Long id,String author, String title, int pages, Date date_started, Date date_finished, String description, int format_id, int status_id, int rating, String notes) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOK_ADD_QUERY)) {
            preparedStatement.setString(1, author);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, pages);
            preparedStatement.setDate(4, date_started);
            preparedStatement.setDate(5, date_finished);
            preparedStatement.setString(6, description);
            if (format_id == 0) {
                preparedStatement.setInt(7, 2);
            } else {
                preparedStatement.setInt(7, format_id);
            }
            if (status_id == 0) {
                preparedStatement.setInt(8, 3);
            } else {
                preparedStatement.setInt(7, status_id);
            }
            preparedStatement.setInt(9, rating);
            preparedStatement.setString(10, notes);
            preparedStatement.setLong(11, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Ошибка добавления книги: " + e.getMessage());
        }
    }
    @Override
    public void deleteBook(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOK_DELETE_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            LOGGER.debug("Удаление книги из БД - ОК!");
        } catch (SQLException e) {
            LOGGER.error("Ошибка удаления книги: " + e.getMessage());
        }
    }

    @Override
    public void updateBook(Long id, String author, String title, int pages, String description, int rating, String notes) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BOOK_UPDATE_QUERY)) {
            preparedStatement.setString(1, author);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, pages);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, rating);
            preparedStatement.setString(6, notes);
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Ошибка обновления книги: " + e.getMessage());
        }
    }
}

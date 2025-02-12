package otus.java.basic.coursework.application;

import java.sql.Date;

public class Book {
    private Long id;
    private String author;
    private String title;
    private int pages;
    private Date dateStarted;
    private Date dateFinished;
    private String description;
    private int format;
    private int status;
    private int rating;
    private String note;

    public Book(Long id, String author, String title, int pages, Date dateStarted, Date dateFinished, String description, int format, int status, int rating, String note) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.pages = pages;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.description = description;
        this.format = format;
        this.status = status;
        this.rating = rating;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

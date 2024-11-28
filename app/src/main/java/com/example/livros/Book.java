package com.example.livros;

public class Book {
    private String title;
    private String author;
    private String genre;
    private int year;
    private String notes;

    public Book(String title, String author, String genre, int year, String notes) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public String getNotes() {
        return notes;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

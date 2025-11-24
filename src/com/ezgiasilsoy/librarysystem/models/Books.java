package com.ezgiasilsoy.librarysystem.models;

import com.ezgiasilsoy.librarysystem.interfaces.IBorrowable;
import java.util.Objects;

public class Books implements IBorrowable {
    private final long id;
    private Author author;
    private String title;
    private double price;
    private Status status;
    private Category category;

    public Books(long id, String title, Author author, double price, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.status = Status.AVAILABLE;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public Status getStatus() { return status; }
    public Category getCategory() { return category; }
    public Author getAuthor() { return author; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public boolean borrow(User user) {
        if (this.status != Status.AVAILABLE) return false;
        if (user.getBorrowedBooksCount() >= User.MAX_BORROW_LIMIT) return false;

        this.status = Status.BORROWED;
        return true;
    }

    @Override
    public boolean returnBook(User user) {
        if (this.status == Status.AVAILABLE) return false;

        this.status = Status.AVAILABLE;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Books books)) return false;
        return id == books.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
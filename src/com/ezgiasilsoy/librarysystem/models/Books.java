package com.ezgiasilsoy.librarysystem.models;


import java.time.LocalDate;
import java.util.Objects;


public class Books {
    private long id;
    private Author author;
    private String title;
    private double price;
    private int edition;
    private LocalDate dateofpurchase;
    private Status status;
    private Category category;

    public Books(long id,
                String title,
                Author author,
                Category category,
                double price,
                LocalDate dateOfPurchase) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.dateofpurchase = dateOfPurchase;

        this.status = Status.AVAILABLE;

    }

    public long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getEdition() {
        return edition;
    }

    public LocalDate getDateofpurchase() {
        return dateofpurchase;
    }

    public Status getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }

    public void setDateofpurchase(LocalDate dateofpurchase) {
        if(dateofpurchase==null) {
            throw new IllegalArgumentException("Date of purchase can not be null");
        }
        this.dateofpurchase = dateofpurchase;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public void setEdition(int edition) {
        if (edition <= 0) {
            throw new IllegalArgumentException("Edition must be positive.");
        }
        this.edition = edition;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Books books)) return false;
        return id == books.id && Objects.equals(author, books.author) && Objects.equals(title, books.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title);
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", edition=" + edition +
                ", dateofpurchase=" + dateofpurchase +
                ", status=" + status +
                ", category=" + category +
                '}';
    }
}

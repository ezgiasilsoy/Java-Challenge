package com.ezgiasilsoy.librarysystem.manager;


import com.ezgiasilsoy.librarysystem.models.Books;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

public class Library {
    private static final String name = System.getenv("LIBRARY_NAME");
    private static final String address= System.getenv("LIBRARY_ADDRESS");
    private static final String phoneNumber = System.getenv("LIBRARY_PHONENUMBER");

    public static Library instance;
    private List<Books> books = new ArrayList<>();

    private Library(){};
    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }
    public List<Books> getBooks(){
        return Collections.unmodifiableList(this.books);
    }
    public void addBook(Books book) {
        if (book == null)
            throw new IllegalArgumentException("Book null olamaz!");
        if (!books.contains(book))
            books.add(book);
    }
    public void removeBook(Books book){
        books.remove(book);
    }
    public void setBooks(List<Books> newBooks){

        books.clear();


        if (newBooks != null) {
            books.addAll(newBooks);
        }
    }

    @Override
    public String toString() {
        return "Library " + name  +"has these books: " + books;

    }
}

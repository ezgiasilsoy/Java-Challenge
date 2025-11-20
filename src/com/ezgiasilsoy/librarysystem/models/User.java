package com.ezgiasilsoy.librarysystem.models;

public class User extends Person{
    private int borrowedBooks;
    public static final int MAX_BORROW_LIMIT = 5;
    public User(long id, String name, String surname) {
        super(id, name, surname);
        this.borrowedBooks = 0;
    }

    public void incrementBorrowedCount() {
        if (this.borrowedBooks >= MAX_BORROW_LIMIT) {
            throw new IllegalStateException("Kullanıcı zaten maksimum (5) kitap limitine ulaştı.");
        }
        this.borrowedBooks++;
    }

    public void decrementBorrowedCount() {
        if (this.borrowedBooks > 0) {
            this.borrowedBooks--;
        }
    }

    public int getBorrowedBooksCount() {
        return borrowedBooks;
    }

    @Override
    public void whouare() {
        System.out.println("Ben, ID'si " + getId() + " olan " + getName() + " " + getSurname() + " adında, bu kütüphanenin bir üyesiyim.");

    }
    @Override
    public String toString() {
        // Person'ın temel bilgilerini alıp, kendi alanını ekler
        return "User{" +
                super.toString() +
                ", borrowedBooksCount=" + borrowedBooks +
                ", maxBorrowLimit=" + MAX_BORROW_LIMIT +
                '}';
    }

}

package com.ezgiasilsoy.librarysystem.models;

import java.time.LocalDate;

public class Loan {
    private final long loanId;
    private final User user;
    private final Books book;
    private final LocalDate loanDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public Loan(long loanId, User user, Books book) {
        this.loanId = loanId;
        this.user = user;
        this.book = book;
        this.loanDate = LocalDate.now();
        this.isReturned = false;
    }

    public long getLoanId() { return loanId; }
    public User getUser() { return user; }
    public Books getBook() { return book; }
    public LocalDate getLoanDate() { return loanDate; }
    public boolean isReturned() { return isReturned; }

    public void markReturned() {
        this.returnDate = LocalDate.now();
        this.isReturned = true;
    }
}
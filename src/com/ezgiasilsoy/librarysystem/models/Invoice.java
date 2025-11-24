package com.ezgiasilsoy.librarysystem.models;

import java.time.LocalDateTime;

public class Invoice {
    private final long invoiceId;
    private final User user;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String description;

    public Invoice(long invoiceId, User user, double amount, String description) {
        this.invoiceId = invoiceId;
        this.user = user;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
        this.description = description;
    }

    public long getInvoiceId() { return invoiceId; }
    public double getAmount() { return amount; }
    public User getUser() { return user; }

    @Override
    public String toString() {
        return String.format("Fatura ID: %d, Kullanıcı: %s, Tutar: %.2f TL, Açıklama: %s",
                invoiceId, user.getName(), amount, description);
    }
}
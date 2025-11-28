package com.ezgiasilsoy.librarysystem.services;

import com.ezgiasilsoy.librarysystem.interfaces.IFinanceService;
import com.ezgiasilsoy.librarysystem.models.Invoice;
import com.ezgiasilsoy.librarysystem.models.Loan;
import com.ezgiasilsoy.librarysystem.models.User;
import com.ezgiasilsoy.librarysystem.models.Books;

public class FinanceServiceImpl implements IFinanceService {

    private static final double LOAN_FEE = 5.00;
    private long nextInvoiceId = 1000;

    @Override
    public Invoice issueInvoice(User user, Books book) {
        long currentId = nextInvoiceId++;
        String description = book.getTitle() + " kitabı için ödünç alma ücreti.";

        Invoice invoice = new Invoice(currentId, user, LOAN_FEE, description);
        System.out.println("-> FATURA KESİLDİ: " + invoice);
        return invoice;
    }

    @Override
    public boolean processRefund(Loan loan, User user) {
        double refundAmount = LOAN_FEE;


        System.out.printf("-> İADE İŞLEMİ: Kullanıcı %s için %.2f TL iade/ceza kontrolü yapıldı.\n",
                user.getName(), refundAmount);

        return true;
    }
}
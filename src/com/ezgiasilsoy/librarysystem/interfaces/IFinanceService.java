package com.ezgiasilsoy.librarysystem.interfaces;

import com.ezgiasilsoy.librarysystem.models.Invoice;
import com.ezgiasilsoy.librarysystem.models.Loan;
import com.ezgiasilsoy.librarysystem.models.User;
import com.ezgiasilsoy.librarysystem.models.Books;

public interface IFinanceService {
    Invoice issueInvoice(User user, Books book);
    boolean processRefund(Loan loan, User user);
}
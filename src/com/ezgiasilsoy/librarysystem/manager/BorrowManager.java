package com.ezgiasilsoy.librarysystem.manager;

import com.ezgiasilsoy.librarysystem.models.Books;
import com.ezgiasilsoy.librarysystem.models.User;
import com.ezgiasilsoy.librarysystem.models.Loan;
import com.ezgiasilsoy.librarysystem.services.FinanceServiceImpl;
import com.ezgiasilsoy.librarysystem.interfaces.IFinanceService;
import java.util.List;

public class BorrowManager {

    private final Library library = Library.getInstance();
    private final IFinanceService financeService = new FinanceServiceImpl();
    private static long loanIdCounter = 1;

    public boolean borrowBook(User user, Books book) {
        if (!library.containsBook(book.getId())) {
            throw new IllegalArgumentException("Kitap kütüphanede kayıtlı değil!");
        }

        boolean bookStatusUpdated = book.borrow(user);

        if (bookStatusUpdated) {
            user.incrementBorrowedCount();

            Loan newLoan = new Loan(loanIdCounter++, user, book);
            library.addLoan(newLoan); // Library'nin public metodu kullanıldı

            financeService.issueInvoice(user, book);
            System.out.println("Kitap başarıyla ödünç verildi ve fatura kesildi.");
            return true;
        }
        return false;
    }

    public boolean returnBook(User user, Books book) {
        if (!library.containsBook(book.getId())) {
            throw new IllegalArgumentException("Kitap bu kütüphanede kayıtlı değil!");
        }

        Loan currentLoan = findActiveLoan(user, book);

        boolean bookStatusUpdated = book.returnBook(user);

        if (bookStatusUpdated && currentLoan != null) {
            user.decrementBorrowedCount();

            currentLoan.markReturned();
            library.removeLoan(currentLoan); // Loan listesinden kaydı kaldır

            financeService.processRefund(currentLoan, user);
            System.out.println("Kitap iade edildi ve finansal işlem tamamlandı.");
            return true;
        }
        return false;
    }

    // Hata Giderildi: Kapsülleme sağlandı (library.activeLoans yerine public metot kullanıldı)
    private Loan findActiveLoan(User user, Books book) {
        List<Loan> loans = library.getActiveLoans(); // Public metot ile erişim

        for (Loan loan : loans) {
            if (loan.getUser().equals(user)
                    && loan.getBook().equals(book)
                    && !loan.isReturned()) {
                return loan;
            }
        }
        return null;
    }
}
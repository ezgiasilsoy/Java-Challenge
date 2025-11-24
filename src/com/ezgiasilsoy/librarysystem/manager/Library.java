package com.ezgiasilsoy.librarysystem.manager;

import com.ezgiasilsoy.librarysystem.models.*;
import java.util.*;

public class Library {
    private static final String name = System.getenv("LIBRARY_NAME");
    private static final String address= System.getenv("LIBRARY_ADDRESS");
    private static final String phoneNumber = System.getenv("LIBRARY_PHONENUMBER");

    public static Library instance;

    // Map'ler ve Set
    private final Map<Long, Books> bookMap = new HashMap<>();
    private final Map<Long, User> userMap = new HashMap<>();
    private final Set<Author> uniqueAuthors = new HashSet<>();

    // Loan Yönetimi: Kapsülleme için private bırakıldı
    private final List<Loan> activeLoans = new ArrayList<>();

    private Library(){};
    public static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    // --- KAPSÜLLEME İÇİN EK METOTLAR ---

    // BorrowManager'ın Loan kaydını tarayabilmesi için public erişim (salt okunur)
    public List<Loan> getActiveLoans() {
        return Collections.unmodifiableList(this.activeLoans);
    }

    // Yeni bir Loan kaydını eklemek için (Loan sınıfı tarafından kullanılır)
    public void addLoan(Loan loan) {
        if (loan == null) throw new IllegalArgumentException("Loan kaydı null olamaz.");
        this.activeLoans.add(loan);
    }

    // Loan kaydını listeden kaldırmak için (Return işlemi sırasında kullanılacak)
    public boolean removeLoan(Loan loan) {
        return this.activeLoans.remove(loan);
    }

    // --- Diğer Yönetim Metotları ---

    public List<Books> getBooks(){
        return Collections.unmodifiableList(new ArrayList<>(this.bookMap.values()));
    }

    public boolean containsBook(long bookId) { return this.bookMap.containsKey(bookId); }

    public void addBook(Books book) {
        if (book == null) throw new IllegalArgumentException("Kitap nesnesi null olamaz!");
        long bookId = book.getId();
        if (this.bookMap.containsKey(bookId)) {
            throw new IllegalArgumentException("Hata: ID " + bookId + " ile kitap zaten mevcut.");
        }
        this.bookMap.put(bookId, book);
        this.uniqueAuthors.add(book.getAuthor());
    }

    public boolean removeBookById(long bookId){
        return this.bookMap.remove(bookId) != null;
    }

    public void addUser(User user) { this.userMap.put(user.getId(), user); }
    public Books findBookById(long bookId) { return bookMap.get(bookId); }

    @Override
    public String toString() {
        return "Library " + name  +" (" + address + ") has " + this.bookMap.size() + " unique books.";
    }
}
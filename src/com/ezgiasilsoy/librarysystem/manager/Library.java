package com.ezgiasilsoy.librarysystem.manager;

import com.ezgiasilsoy.librarysystem.models.*;
import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private static final String name = System.getenv("LIBRARY_NAME");
    private static final String address= System.getenv("LIBRARY_ADDRESS");
    private static final String phoneNumber = System.getenv("LIBRARY_PHONENUMBER");

    public static Library instance;

    private final Map<Long, Books> bookMap = new HashMap<>();
    private final Map<Long, User> userMap = new HashMap<>();
    private final Set<Author> uniqueAuthors = new HashSet<>();

    private final List<Loan> activeLoans = new ArrayList<>();

    private Library(){};
    public static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }


    public List<Loan> getActiveLoans() {
        return Collections.unmodifiableList(this.activeLoans);
    }

    public void addLoan(Loan loan) {
        if (loan == null) throw new IllegalArgumentException("Loan kaydı null olamaz.");
        this.activeLoans.add(loan);
    }

    public boolean removeLoan(Loan loan) {
        return this.activeLoans.remove(loan);
    }


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


    public User findUserById(long userId) { return userMap.get(userId); }


    public List<Books> searchBooksByTitle(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lowerCaseTerm = searchTerm.toLowerCase();

        return bookMap.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerCaseTerm))
                .collect(Collectors.toList());
    }


    public List<Books> searchBooksByCategory(Category category) {
        if (category == null) {
            return Collections.emptyList();
        }

        return bookMap.values().stream()
                .filter(book -> book.getCategory().equals(category))
                .collect(Collectors.toList());
    }


    public List<Books> searchBooksByAuthorName(String authorName) {
        if (authorName == null || authorName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String lowerCaseName = authorName.toLowerCase();

        return bookMap.values().stream()
                .filter(book ->
                        book.getAuthor().getFirstName().toLowerCase().contains(lowerCaseName) ||
                                book.getAuthor().getLastName().toLowerCase().contains(lowerCaseName))
                .collect(Collectors.toList());
    }


    public List<Books> listBooksByIdSorted() {
        return bookMap.values().stream()
                .sorted(Comparator.comparing(Books::getId)) // ID'ye göre sıralama
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return "Library " + name  +" (" + address + ") has " + this.bookMap.size() + " unique books.";
    }
}
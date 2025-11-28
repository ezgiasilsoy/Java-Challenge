package com.ezgiasilsoy.librarysystem.app;

import com.ezgiasilsoy.librarysystem.manager.BorrowManager;
import com.ezgiasilsoy.librarysystem.manager.Library;
import com.ezgiasilsoy.librarysystem.models.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Library libraryManager = Library.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final BorrowManager borrowManager = new BorrowManager();

    public static void main(String[] args) {
        System.out.println(">>> KÜTÜPHANE OTOMASYON SİSTEMİ BAŞLATILIYOR <<<");
        loadInitialData();
        displayMainMenu();
        System.out.println("Programdan çıkılıyor. Güle güle!");
        scanner.close();
    }

    private static void loadInitialData() {
        Author yazar1 = new Author(101, "Stephen", "King");
        Author yazar2 = new Author(102, "George", "Orwell");
        Author yazar3 = new Author(103, "Jeff", "Abbott");

        Books kitap1 = new Books(1, "IT", yazar1, 35.50, Category.ROMAN);
        Books kitap2 = new Books(2, "Misery", yazar1, 25.00, Category.ROMAN);
        Books kitap3 = new Books(3, "1984", yazar2, 40.00, Category.NEWS);
        Books kitap4 = new Books(4, "Adrenaline", yazar3, 30.00, Category.ROMAN);

        User user1 = new User(1001, "Ahmet", "Yılmaz");
        User user2 = new User(1002, "Gizem", "Kaya");
        User user3 = new User(1003, "Ezgi", "Asılsoy");

        libraryManager.addBook(kitap1);
        libraryManager.addBook(kitap2);
        libraryManager.addBook(kitap3);
        libraryManager.addUser(user1);
        libraryManager.addUser(user2);
        libraryManager.addBook(kitap4);
        libraryManager.addUser(user3);


        System.out.println(libraryManager.toString());
        System.out.println("Test verileri yüklendi. Toplam kitap: " + libraryManager.getBooks().size());
    }

    private static void displayMainMenu() {
        int choice;
        do {
            System.out.println("\n--- ANA MENÜ ---");
            System.out.println("1. Kitap Ödünç Al");
            System.out.println("2. Kitap İade Et");
            System.out.println("3. Kitap Ara/Listele");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: handleBorrow(); break;
                    case 2: handleReturn(); break;
                    case 3: handleSearch(); break;
                    case 0: break;
                    default: System.out.println("Geçersiz seçim. Tekrar deneyin.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Hata: Lütfen sadece sayı girin.");
                scanner.nextLine();
                choice = -1;
            } catch (Exception e) {
                System.err.println("Bir hata oluştu: " + e.getMessage());
                choice = -1;
            }
        } while (choice != 0);
    }

    private static void handleBorrow() {
        System.out.println("\n--- KİTAP ÖDÜNÇ ALMA ---");
        System.out.print("Kullanıcı ID girin: ");
        long userId = scanner.nextLong();
        scanner.nextLine();

        User user = libraryManager.findUserById(userId);
        if (user == null) {
            System.out.println("Kullanıcı bulunamadı.");
            return;
        }

        System.out.print("Kitap ID girin: ");
        long bookId = scanner.nextLong();
        scanner.nextLine();

        Books book = libraryManager.findBookById(bookId);
        if (book == null) {
            System.out.println("Kitap bulunamadı.");
            return;
        }

        try {
            if (borrowManager.borrowBook(user, book)) {
                System.out.println("Başarılı: '" + book.getTitle() + "' kitabı, " + user.getName() + " adına ödünç alındı. Kalan limit: " + (User.MAX_BORROW_LIMIT - user.getBorrowedBooksCount()));
            } else {
                System.out.println("Hata: Kitap ödünç alınamadı (Kitap müsait değil veya limit doldu).");
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.err.println("İşlem Hatası: " + e.getMessage());
        }
    }

    private static void handleReturn() {
        System.out.println("\n--- KİTAP İADE ETME ---");
        System.out.print("Kullanıcı ID girin: ");
        long userId = scanner.nextLong();
        scanner.nextLine();

        User user = libraryManager.findUserById(userId);
        if (user == null) {
            System.out.println("Kullanıcı bulunamadı.");
            return;
        }

        if (user.getBorrowedBooksCount() == 0) {
            System.out.println("Kullanıcının iade edilecek aktif kitabı bulunmamaktadır.");
            return;
        }

        System.out.print("İade edilecek Kitap ID girin: ");
        long bookId = scanner.nextLong();
        scanner.nextLine();

        Books book = libraryManager.findBookById(bookId);
        if (book == null) {
            System.out.println("Kitap kütüphane kaydında bulunamadı.");
            return;
        }

        try {
            if (borrowManager.returnBook(user, book)) {
                System.out.println("Başarılı: '" + book.getTitle() + "' kitabı, " + user.getName() + " tarafından iade edildi.");
                System.out.println("Kalan limit: " + (User.MAX_BORROW_LIMIT - user.getBorrowedBooksCount()));
            } else {
                System.out.println("Hata: Kitap iade edilemedi (Kitap ödünçte değil vb.).");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("İşlem Hatası: " + e.getMessage());
        }
    }

    private static void handleSearch() {
        System.out.println("\n--- KİTAP SEÇME VE LİSTELEME ---");
        System.out.println("1. ID'ye Göre Kitap Seç (Tek Sonuç)");
        System.out.println("2. Başlığa Göre Ara (Çoklu Sonuç)");
        System.out.println("3. Kategoriye Göre Filtrele (Çoklu Sonuç)");
        System.out.println("4. Yazara Göre Ara (Çoklu Sonuç)");
        System.out.println("5. Tüm Kitapları ID'ye Göre Sıralı Listele");
        System.out.print("Seçiminiz: ");

        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        List<Books> results = Collections.emptyList();

        try {
            switch (searchChoice) {
                case 1:
                    System.out.print("Lütfen Seçmek İstediğiniz Kitabın ID'sini Girin: "); // 🔑 ID İste
                    long bookId = scanner.nextLong();
                    scanner.nextLine();

                    Books selectedBook = libraryManager.findBookById(bookId); // Kitabı getir

                    if (selectedBook != null) {
                        results = Collections.singletonList(selectedBook);
                    } else {
                        System.out.println("UYARI: " + bookId + " ID'li kitap kütüphanede bulunamadı.");
                        return;
                    }
                    break;
                case 2:
                    System.out.print("Aramak istediğiniz başlık kelimesini girin: ");
                    String titleTerm = scanner.nextLine();
                    results = libraryManager.searchBooksByTitle(titleTerm);
                    break;
                case 3:
                    System.out.println("Mevcut Kategoriler: ROMANS, NEWS, STUDYBOOKS, JOURNALS, MAGAZINES");
                    System.out.print("Filtrelemek istediğiniz kategori adını girin: ");
                    String categoryName = scanner.nextLine().toUpperCase();
                    Category selectedCategory = Category.valueOf(categoryName);
                    results = libraryManager.searchBooksByCategory(selectedCategory);
                    break;
                case 4:
                    System.out.print("Aramak istediğiniz yazar adını veya soyadını girin: ");
                    String authorTerm = scanner.nextLine();
                    results = libraryManager.searchBooksByAuthorName(authorTerm);
                    break;
                case 5:
                    results = libraryManager.listBooksByIdSorted();
                    break;
                default:
                    System.out.println("Geçersiz arama seçeneği.");
                    return;
            }

            System.out.println("\n--- SEÇİM SONUÇLARI (" + results.size() + " adet) ---");
            if (results.isEmpty()) {
                System.out.println("Aradığınız kriterlere uygun kitap bulunamadı.");
            } else {
                results.forEach(b ->
                        System.out.printf("ID: %d | Başlık: %-20s | Yazar: %s | Durum: %s%n",
                                b.getId(),
                                b.getTitle(),
                                b.getAuthor().getFirstName() + " " + b.getAuthor().getLastName(),
                                b.getStatus())
                );
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Hata: Girdiğiniz değer geçersiz (Örn: Yanlış kategori adı).");
        } catch (java.util.InputMismatchException e) {
            System.err.println("Hata: Lütfen sadece sayı girin.");
            scanner.nextLine();
        }
    }
}
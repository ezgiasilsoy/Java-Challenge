package com.ezgiasilsoy.librarysystem.interfaces;

import com.ezgiasilsoy.librarysystem.models.User;

public interface IBorrowable {
    boolean borrow(User user);
    boolean returnBook(User user);
}

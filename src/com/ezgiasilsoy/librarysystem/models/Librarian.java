package com.ezgiasilsoy.librarysystem.models;

public class Librarian extends Person{

    private final String employeeId;



    public Librarian(long id, String name, String surname, String employeeId) {
        super(id, name, surname);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }




    @Override
    public void whouare() {
        System.out.println("Ben, ID'si " + getId() + " olan " + getName() + " " + getSurname() +
                " adında, bu kütüphanenin yetkili personeliyim (Çalışan ID: " + employeeId + ").");
    }

    @Override
    public String toString() {
        return "Librarian{" +
                super.toString() +
                ", employeeId='" + employeeId +
                '}';
    }


}

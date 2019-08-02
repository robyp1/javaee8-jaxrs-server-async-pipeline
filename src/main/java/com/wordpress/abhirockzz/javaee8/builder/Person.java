package com.wordpress.abhirockzz.javaee8.builder;

public class Person {

    private final String name;
    private final String surname;
    private String phone;
    private Address adress;


    public Person(String name, String surname, String phone, Address adress) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAdress() {
        return adress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", adress=" + adress +
                '}';
    }
}

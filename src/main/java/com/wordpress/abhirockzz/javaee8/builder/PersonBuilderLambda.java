package com.wordpress.abhirockzz.javaee8.builder;

import java.util.function.Consumer;

public class PersonBuilderLambda {

    private String name;
    private String surname;
    private String phone;
    private Address adress;


    public PersonBuilderLambda add(Consumer<PersonBuilderLambda> fun){
        fun.accept(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    public Person build(){
        return new Person(name,surname,phone,adress);
    }

    final static class AddressBuilderLambda {

        private String city;
        private String zipcode;
        private String street;

        AddressBuilderLambda add(Consumer<AddressBuilderLambda> fun) {
            fun.accept(this);
            return this;
        }

        public Address build() {
            return new Address(city, zipcode, street);
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }
    }


}

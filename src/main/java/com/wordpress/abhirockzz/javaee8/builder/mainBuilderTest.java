package com.wordpress.abhirockzz.javaee8.builder;

public class mainBuilderTest {

    public static void main(String[] args){
        Person person = new PersonBuilderLambda().add(
                $ -> {
                    $.setName("Bob");
                    $.setSurname("Marley");
                    $.setPhone("123");
                }).add(
                $ ->
                    $.setAdress( new PersonBuilderLambda.AddressBuilderLambda().add(
                            addressBuilder -> {
                                addressBuilder.setCity("New York");
                                addressBuilder.setStreet("XX");
                            }).build()//create an Address to add to person
                    )).build();//create a Person
        System.out.println(person);
    }
}

package com.wordpress.abhirockzz.javaee8.builder;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toMap;

/**
 * esempio di Builder Pattern con lambda e functional programming
 */
public class mainBuilderTest {

    public static void main(String[] args) throws ParseException {
        Person person = new PersonBuilderLambda().add(
                $ -> {
                    $.setName("Bob");
                    $.setSurname("Marley");
                    $.setPhone("123");
                }).add(
                $ ->
                        $.setAdress(new PersonBuilderLambda.AddressBuilderLambda().add(
                                addressBuilder -> {
                                    addressBuilder.setCity("New York");
                                    addressBuilder.setStreet("XX");
                                }).build()//create an Address to add to person
                        )).build();//create a Person
        System.out.println(person);

        //altro test
        //test heap, meanwwhile it run launch jvm profiler
        //crea una mappa che ha per chiave la funzione identità (1,2,3..n) e per valori i risultati del metodo  pointFrom,
        //la funzione di merge dice come comportarsi con chiavi duplicate, in questo caso torna una illegalStateException
        // e ultimo parametro è la funzione di inizializzazione o supplier  cioè la mappa vuota
        final Map<Long, FillMaps.Point> m = LongStream.range(0, 1_000_000)
                .boxed()
                .collect(
                        toMap(
                                Function.identity(),
                                FillMaps::pointFrom,
                                (u, v) -> {
                                    throw new IllegalStateException();
                                },
                                HashMap::new
                        )
                );

//        String num = "123,90";
//        NumberFormat nbf = NumberFormat.getInstance();
//        Number number = nbf.parse(num);
//        BigDecimal bigDecimal = new BigDecimal(number.toString()).setScale(2);
//        System.out.println(bigDecimal);

    }

    final static class FillMaps {


        // Conveniency method that creates a Point from
        // a long by applying modulo prime number operations
        private static Point pointFrom(long seed) {
            final Point point = new Point();
            point.setX((int) seed % 4517);
            point.setY((int) seed % 5011);
            return point;
        }


        final static class Point {
            int x;
            int y;

            public Point() {
                this.x = 0;
                this.y = 0;
            }

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }
        }
    }
}

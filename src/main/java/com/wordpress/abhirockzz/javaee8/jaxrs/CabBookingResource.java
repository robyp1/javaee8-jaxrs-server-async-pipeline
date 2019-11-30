package com.wordpress.abhirockzz.javaee8.jaxrs;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("cabs")
public class CabBookingResource {

    @Resource
    ManagedExecutorService mes;

    @GET
    @Path("{id}")
    // @Monitored --> commentato è stata creata una classe che implementa Extension e che trovi nel file
    // \META-INF\services\javax.enterprise.inject.spi.Extension il package.nome, che aggiunge l'annotazione
    // in automatico all'avvio del CDI container, in funzione della configurazione (performances.properties)
    //che abilita e dice  quali classi aggiungere l'annotazione
    /**
     * validateUserTask.complete è il primo task, il task completa sempre anche se validateuser non termina a livello di thread
     * (in tal caso torna false), il risultato boolean di ritorno viene passato a searchDriverTask che usa composeAsync
     * per inviare il risultato al metodo apply che esegue searchDriver(), il risultato "driver" ottenuto appena questo finisce (thenapplyAsync) invoca
     * il metodo notifyUSer passandolo in input
     *
     */
    public CompletionStage<String> getCab(@PathParam("id") final String name) {
        System.out.println("HTTP request handled by thread " + Thread.currentThread().getName());

        final CompletableFuture<Boolean> validateUserTask = new CompletableFuture<>();
        //qui uso thenCompose perchè apply invoca una funziona che torna un CompletionStage
        CompletableFuture<String> searchDriverTask = validateUserTask.thenComposeAsync(
                new Function<Boolean, CompletionStage<String>>() {
            @Override
            public CompletionStage<String> apply(Boolean t) {
                System.out.println("User validated ? " + t);
                return CompletableFuture.supplyAsync(() -> searchDriver(), mes);
            }
        }, mes);
        //qui invece uso thenApply perchè la funzione che invoca torna una stringa e quindi non un CompletionStage
        final CompletableFuture<String> notifyUserTask = searchDriverTask.thenApplyAsync(
                (driver) -> notifyUser(driver), mes);

        mes.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    validateUserTask.complete(validateUser(name));
                } catch (Exception ex) {
                    Logger.getLogger(CabBookingResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        return notifyUserTask;
    }

    boolean validateUser(String id) {
        System.out.println("searchDriverTask handled by thread " + Thread.currentThread().getName());
        System.out.println("validating user " + id);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(CabBookingResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    String searchDriver() {
        System.out.println("searchDriverTask handled by thread " + Thread.currentThread().getName());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException ex) {
            Logger.getLogger(CabBookingResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "john doe";
    }

    String notifyUser(String info) {
        System.out.println("searchDriverTask handled by thread " + Thread.currentThread().getName());

        return "Your driver is " + info + " and the OTP is " + (new Random().nextInt(999) + 1000);
    }


}

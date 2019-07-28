package com.wordpress.abhirockzz.javaee8.jaxrs.api;

import com.wordpress.abhirockzz.javaee8.jaxrs.model.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@ApplicationScoped
public class FailoverExample {

    /**
     * interroga uno o più siti web però si ferma restituendo il json della risposta
     * sul primo nella lista che risponde (findFirst)
     * @return
     */
    public Data sites(){
       return (Data) Stream.<Supplier<Data>>of(
               () -> getData("http://localhost:8080/booking/", "/1"),
               () -> getData("http://localhost:8080/booking/","/2"))
       .filter(Objects::nonNull)
               .findFirst()
               .orElseThrow(()-> new IllegalStateException("No avaliable remote server"));

        }

    private Data getData(String uri, String targetVal) {
        try {
            final Client client = ClientBuilder.newClient();
            return client.target(uri)
                    .path("/api/cabs" + targetVal)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(Data.class);
        } catch (WebApplicationException e) {
            if (supportFailOver(e)){
                return null;
            }
            throw  e;
        }
    }

    private boolean supportFailOver(WebApplicationException e) {
        Response response = e.getResponse();
        if (response == null) {
            return false;
        }
        return ( response.getStatus() > 412);//404 page non found or 412 are not
    }
}

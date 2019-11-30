package com.wordpress.abhirockzz.javaee8.beansExtension;

import com.wordpress.abhirockzz.javaee8.interceptors.Monitored;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PerformanceExtension implements Extension {

    private final AnnotationLiteral<Monitored> monitoredAnnotation = new AnnotationLiteral<Monitored>(){};

    private final Properties configuration = new Properties();

    private boolean enabled;

    /**
     * carica file di configurazioni per applicare poi dinamicamente le annotazioni
     * @param beforeBeanDiscovery
     */
    void loadConfiguration(final @Observes BeforeBeanDiscovery beforeBeanDiscovery){
        try (final InputStream configStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("performances.properties")
        ){
            if (configStream != null){
                configuration.load(configStream);
            }
        } catch (final IOException ex){
            throw new IllegalArgumentException(ex);
        }
        enabled = Boolean.parseBoolean(configuration.getProperty("enabled", "false"));
    }

    /**
     * aggiunge dinamicamente l'annotazione monitor a tutte le classi che sono abilitate nel file
     * di configurazione caricato nel metodo sopra
     * @param pat
     * @param <A>
     */
    <A> void processAnnotatedType(final @Observes ProcessAnnotatedType<A> pat){
        if (!enabled){
            return;
        }
        final String beanClassName = pat.getAnnotatedType().getJavaClass().getName();
        if (Boolean.parseBoolean(configuration.getProperty(beanClassName + ".monitor","false" ))){
            System.out.println("addinf monitored Annotation for Monitoring class " + beanClassName);
            pat.setAnnotatedType(new WrapperAnnotatedType<>(pat.getAnnotatedType(), monitoredAnnotation));
        }
    }
}

package com.wordpress.abhirockzz.javaee8.service;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
@AccessTimeout(3000)
public class StartupApplication {

    @Inject
    FailoverExample failoverExampleBean;

    @PostConstruct
    public void OnStart(){
//        failoverExampleBean.sites().toString();
    }
}

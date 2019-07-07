package com.wordpress.abhirockzz.javaee8.interceptors;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Interceptor
@Monitored
@Priority(Interceptor.Priority.PLATFORM_AFTER)
public class MonitoredInterceptor implements Serializable {

    @AroundInvoke
    @AroundTimeout
    public Object invoke(final InvocationContext invocationContext) throws Exception{
        long startTime = System.nanoTime();
        Exception error = null;
        try {
            return invocationContext.proceed();
        } catch (Exception e) {
            error = e;
            throw e;
        }
        finally {
            if (error == null){
                System.out.println("execution time in ms : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime ));
            }
            else {
                System.err.println("Error, execution time in ms : " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime ));
            }
        }
    }
}

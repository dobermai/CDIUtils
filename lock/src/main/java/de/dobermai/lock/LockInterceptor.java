package de.dobermai.lock;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * @author dobermai
 */
@Lock
@Interceptor
public class LockInterceptor implements Serializable {

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Throwable {

        System.out.println("YEEEP");
       // final Lock annotation = ic.getTarget().getClass().getAnnotation(Lock.class);
        System.out.println("Lock!");
        return ic.proceed();
    }
}

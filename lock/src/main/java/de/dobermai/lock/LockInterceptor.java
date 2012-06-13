package de.dobermai.lock;

import javax.inject.Inject;
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

    @Inject
    LockImpl impl;

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Throwable {

        final boolean lock = impl.getLock();
        if (lock) {
            try {
                return ic.proceed();
            }
            // final Lock annotation = ic.getTarget().getClass().getAnnotation(Lock.class);
            finally {
                impl.releaseLock();
            }
        } else {
            return null;
        }
    }
}

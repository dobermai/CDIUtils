package de.dobermai.lock;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * @author dobermai
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Lock {
//    @Nonbinding Class value() default GlobalLock.class;
}

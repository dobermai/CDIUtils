package de.dobermai.lock;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.CountDownLatch;

@Stateless
public class TestLock {

    public static CountDownLatch LATCH = new CountDownLatch(2);

    public static int counter = 0;

    private Integer anInt;

    @Lock
    @Asynchronous
    public void locked() {
        //VERY ugly hack to simulate waiting since we are not allowed to use Thread.sleep
        for (int i = 0; i < 500000000; i++) {
            anInt = i;
        }
        LATCH.countDown();
    }
}
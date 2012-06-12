package de.dobermai.lock;

public class TestLock {

    @Lock
    public void locked() {

        System.out.println("blub");

    }
}
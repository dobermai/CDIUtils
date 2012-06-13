package de.dobermai.lock;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author dobermai
 */
@ApplicationScoped
public class LockImpl {

    private boolean locked;

    @PostConstruct
    public void init() {
        locked = false;
    }

    public synchronized boolean getLock() {
        if (locked) {
            return false;
        }
        locked = true;
        return true;
    }

    public synchronized void releaseLock() {
        if (locked) {
            locked = false;
        }
    }
}

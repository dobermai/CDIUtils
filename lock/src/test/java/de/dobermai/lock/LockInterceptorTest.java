package de.dobermai.lock;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * @author dobermai
 */
@RunWith(Arquillian.class)
public class LockInterceptorTest {

    @Deployment
    public static Archive<?> createTestArchive() {

        BeansDescriptor beans = Descriptors.create(BeansDescriptor.class)
                .createInterceptors().clazz("de.dobermai.lock.LockInterceptor").up();

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(GlobalLock.class, Lock.class, LockInterceptor.class, TestLock.class)
                .addAsWebInfResource(new StringAsset(beans.exportAsString()), "beans.xml");
    }

    @Inject
    TestLock lock;

    @Test
    public void test() {

        Assert.assertTrue(true);

        lock.locked();

    }

}

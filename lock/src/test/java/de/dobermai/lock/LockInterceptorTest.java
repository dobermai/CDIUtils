package de.dobermai.lock;

import com.jayway.awaitility.Awaitility;
import com.jayway.awaitility.Duration;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertTrue;

/**
 * @author dobermai
 */
@RunWith(Arquillian.class)
public class LockInterceptorTest {

    @Deployment
    public static Archive<?> createTestArchive() {

        BeansDescriptor beans = Descriptors.create(BeansDescriptor.class)
                .createInterceptors().clazz("de.dobermai.lock.LockInterceptor").up();

        MavenDependencyResolver resolver = DependencyResolvers
                .use(MavenDependencyResolver.class)
                .loadMetadataFromPom("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(GlobalLock.class, Lock.class, LockInterceptor.class, TestLock.class, LockImpl.class)
                .addAsLibraries(resolver.artifact("com.jayway.awaitility:awaitility:1.3.4").resolveAsFiles())
                .addAsWebInfResource(new StringAsset(beans.exportAsString()), "beans.xml");
    }

    @Inject
    TestLock lock;

    @Test
    public void test() throws Exception {

        lock.locked();

        Awaitility.setDefaultPollInterval(Duration.ONE_SECOND);
        Awaitility.await().until(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                lock.locked();
                TestLock.counter++;
                return TestLock.LATCH.getCount() == 0;
            }
        });

        System.out.println("Needed " + TestLock.counter + " attempts");

        assertTrue(TestLock.counter > 2);
    }

}

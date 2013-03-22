package datacollector.ioc;

import com.google.inject.Provider;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.InjectorFactory;
import org.jboss.resteasy.spi.ResourceFactory;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public class GuiceResourceFactory implements ResourceFactory {
    private final Class<?> resourceClass;
    private final Provider<?> provider;

    GuiceResourceFactory(Class<?> resourceClass, Provider<?> provider) {
        this.resourceClass = resourceClass;
        this.provider = provider;
    }

    @Override
    public Class<?> getScannableClass() {
        return resourceClass;
    }

    @Override
    public void registered(InjectorFactory injectorFactory) {
        // nop
    }

    @Override
    public Object createResource(HttpRequest httpRequest, HttpResponse httpResponse, InjectorFactory injectorFactory) {
        return provider.get();
    }

    @Override
    public void requestFinished(HttpRequest httpRequest, HttpResponse httpResponse, Object o) {
        //nop
    }

    @Override
    public void unregistered() {
        //nop
    }
}

package datacollector.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public class GuiceFactory {

    public static final GuiceFactory INSTANCE = new GuiceFactory();
    private final Injector injector;

    private GuiceFactory() {
        injector = Guice.createInjector(new AppGuiceModule());
    }

    public Injector getInjector() {
        return injector;
    }
}

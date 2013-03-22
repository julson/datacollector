package datacollector.ioc;

import com.google.inject.Injector;
import datacollector.service.CollectorService;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.spi.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public class GuiceResteasyBootstrap extends ResteasyBootstrap implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        ServletContext context = sce.getServletContext();
        Registry registry = (Registry) context.getAttribute(Registry.class.getName());
        Injector injector = GuiceFactory.INSTANCE.getInjector();

        registry.addResourceFactory(new GuiceResourceFactory(CollectorService.class, injector.getProvider(CollectorService.class)));
    }

}

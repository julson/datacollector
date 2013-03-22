package datacollector.service;

import com.google.inject.Injector;
import datacollector.ioc.GuiceFactory;
import datacollector.model.Transaction;
import datacollector.process.TransactionProcessor;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
@Singleton
public class CollectorServiceImpl implements CollectorService {

    private final TransactionProcessor processor;

    @Inject
    CollectorServiceImpl(TransactionProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void save(Transaction transaction) {
        processor.submit(transaction);
    }

    @Override
    public String ping() {
        return "OK";
    }
}

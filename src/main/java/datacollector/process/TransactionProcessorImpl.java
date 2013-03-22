package datacollector.process;

import datacollector.dao.TransactionDao;
import datacollector.model.Transaction;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
@Singleton
public class TransactionProcessorImpl implements TransactionProcessor {

    private final ExecutorService executorService;
    private final TransactionDao dao;

    @Inject
    TransactionProcessorImpl(@Named("processor") ExecutorService executorService,
                             TransactionDao dao) {
        this.executorService = executorService;
        this.dao = dao;
    }

    @Override
    public void submit(final Transaction transaction) {
        executorService.submit(new Callable<Transaction>() {
            @Override
            public Transaction call() throws Exception {
                dao.save(transaction);
                return transaction;
            }
        });
    }

}

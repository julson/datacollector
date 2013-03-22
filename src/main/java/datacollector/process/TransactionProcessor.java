package datacollector.process;

import datacollector.model.Transaction;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public interface TransactionProcessor {

    void submit(Transaction transaction);
}

package datacollector.dao;

import datacollector.model.Transaction;

import java.io.IOException;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public interface TransactionDao {

    void save(Transaction transaction) throws IOException, InterruptedException;
}

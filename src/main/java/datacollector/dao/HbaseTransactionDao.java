package datacollector.dao;

import com.google.common.collect.Lists;
import datacollector.model.Event;
import datacollector.model.Transaction;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.util.Bytes;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
@Singleton
public class HbaseTransactionDao implements TransactionDao {

    private final Provider<HTableInterface> hTableProvider;

    @Inject
    HbaseTransactionDao(Provider<HTableInterface> hTableProvider) {
        this.hTableProvider = hTableProvider;
    }

    @Override
    public void save(Transaction transaction) throws IOException, InterruptedException {
        HTableInterface hTable = hTableProvider.get();
        hTable.setAutoFlush(false);
        List<Put> ops = Lists.newArrayList();
        for (Event event : transaction.getEvents()) {
            ops.add(getPutRowOperation(event));
        }
        hTable.put(ops);
        hTable.flushCommits();
        hTable.close();
    }

    private Put getPutRowOperation(Event event) {
        Put put = new Put(Bytes.toBytes(event.getMessageId()));
        addColumn(put, "event", "name", Bytes.toBytes(event.getName()));
        addColumn(put, "event", "flowName", Bytes.toBytes(event.getFlowName()));
        addColumn(put, "event", "domain", Bytes.toBytes(event.getDomain()));
        addColumn(put, "event", "type", Bytes.toBytes(event.getType()));
        addColumn(put, "event", "timestamp", Bytes.toBytes(event.getTimestamp()));

        Map<String, String> props = event.getProperties();
        for (String key : props.keySet()) {
            byte[] value = Bytes.toBytes(props.get(key));
            addColumn(put, "props", key, value);
        }
        return put;
    }

    private void addColumn(Put put, String columnFamily, String qualifier, byte[] value) {
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), value);
    }
}

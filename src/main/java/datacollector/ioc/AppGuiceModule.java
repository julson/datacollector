package datacollector.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import datacollector.dao.HbaseTransactionDao;
import datacollector.dao.TransactionDao;
import datacollector.process.TransactionProcessor;
import datacollector.process.TransactionProcessorImpl;
import datacollector.service.CollectorService;
import datacollector.service.CollectorServiceImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public class AppGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TransactionProcessor.class).to(TransactionProcessorImpl.class);
        bind(TransactionDao.class).to(HbaseTransactionDao.class);
        bind(CollectorService.class).to(CollectorServiceImpl.class);
    }

    @Provides
    @Singleton
    HTablePool providesHTablePool() {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost");

        return new HTablePool(config, 20);
    }

    @Provides
    HTableInterface providesTransactionTable(HTablePool pool) {
        return pool.getTable("events");
    }

    @Provides
    @Singleton
    @Named("processor")
    ExecutorService providesProcessorExecutorService() {
        return Executors.newFixedThreadPool(20);
    }

    @Provides
    @Singleton
    @Named("logger")
    ScheduledExecutorService providesLoggerExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

}

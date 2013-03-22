package datacollector.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public class Transaction {

    private final List<Event> events;

    public Transaction(@JsonProperty("events") List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}

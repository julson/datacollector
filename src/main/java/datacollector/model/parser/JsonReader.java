package datacollector.model.parser;

import com.google.common.collect.Lists;
import datacollector.model.Event;
import datacollector.model.Transaction;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

/**
 * A custom JSON reader that parses the payload in chunks as an optimization,
 * in order to avoid out of memory errors associated with binding large JSON data
 *
 * @author Julson Lim (julsonlim@gmail.com)
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JsonReader implements MessageBodyReader<Object> {

    private final JsonFactory factory = new JsonFactory();
    private final ObjectReader reader = (new ObjectMapper()).reader(Event.class);

    @Override
    public boolean isReadable(Class<?> clazz, Type type, Annotation[] annotations, MediaType mediaType) {
        return isJson(mediaType) && (isTransactionClass(clazz) || isEventListClass(clazz, type));
    }

    @Override
    public Object readFrom(Class<Object> clazz,
                           Type type,
                           Annotation[] annotations,
                           MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders,
                           InputStream inputStream) throws IOException, WebApplicationException {
        JsonParser p = factory.createJsonParser(inputStream);
        if (isTransactionClass(clazz)) {
            return parseTransaction(p);

        } else if (isEventListClass(clazz, type)) {
            return parseEventList(p);

        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    private Transaction parseTransaction(JsonParser p) throws IOException {
        p.nextToken();
        return reader.readValue(p, Transaction.class);
    }

    private List<Event> parseEventList(JsonParser p) throws IOException {
        List<Event> events = Lists.newArrayList();
        p.nextToken();
        while (p.nextToken() == JsonToken.START_OBJECT) {
            events.add(reader.readValue(p, Event.class));
        }
        return events;
    }

    private boolean isJson(MediaType mediaType) {
        return mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    private boolean isTransactionClass(Class<?> clazz) {
        return clazz.equals(Transaction.class);
    }

    private boolean isEventListClass(Class<?> clazz, Type type) {
        return clazz.equals(List.class) && type.getClass().equals(Event.class);
    }

}

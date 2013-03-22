package datacollector.model;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
public class Event {
    private final String domain;
    private final String name;
    private final String type;
    private final long timestamp;
    private final String messageId;
    private final String flowName;
    private final Map<String, String> properties;

    @JsonCreator
    public Event(@JsonProperty("name") String name,
                 @JsonProperty("type") String type,
                 @JsonProperty("timestamp") long timestamp,
                 @JsonProperty("messageId") String messageId,
                 @JsonProperty("flowName") String flowName,
                 @JsonProperty("domain") String domain,
                 @JsonProperty("properties") Map<String,String> properties) {
        if ( name == null || flowName == null || messageId == null){
            throw new IllegalArgumentException("Name,FlowName and messageId must not be null");
        }
        this.name = name;
        this.type = StringUtils.defaultString(type);
        this.timestamp = timestamp;
        this.messageId = StringUtils.defaultString(messageId);
        this.flowName = StringUtils.defaultString(flowName);
        this.domain = domain;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void addProperty(String key, String value){
        this.properties.put(key,value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public String getDomain() {
        return domain;
    }

}

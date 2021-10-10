package ie.credit.suisse.parser.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import ie.credit.suisse.parser.model.ApplicationLogEntry;
import ie.credit.suisse.parser.model.LogEntry;
import ie.credit.suisse.parser.model.enums.LogState;

public class LogEntryDeserializer extends StdDeserializer<LogEntry> { 

	
	private static final long serialVersionUID = 1L;

	public LogEntryDeserializer() { 
        this(null); 
    } 

    public LogEntryDeserializer(Class<?> vc) { 
        super(vc); 
    }

    @Override
    public LogEntry deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        
        String id = node.get("id").asText();
        LogState state = LogState.valueOf(node.get("state").asText());
        long timestamp = node.get("timestamp").asLong();

        boolean applicationLogEntry = node.has("type");
        if (!applicationLogEntry) {
          return new LogEntry(id, state, timestamp);
        }

        String type = node.get("type").asText();
        String host = node.get("host").asText();
        return new ApplicationLogEntry(id, state, timestamp, type, host);
    }

}
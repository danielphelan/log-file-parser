package ie.credit.suisse.parser.model;

import ie.credit.suisse.parser.model.enums.LogState;

public class LogEntry {
	private String id;
	private LogState state;
	private Long timestamp;
	
	public LogEntry(String id, LogState state, long timestamp) {
		this.id = id;
		this.state = state;
		this.timestamp = timestamp;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LogState getState() {
		return state;
	}
	public void setState(LogState state) {
		this.state = state;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "LogEntry [id=" + id + ", state=" + state + ", timestamp=" + timestamp + "]";
	}	

	
	
}

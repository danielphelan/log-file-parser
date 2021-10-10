package ie.credit.suisse.parser.model;

import ie.credit.suisse.parser.model.enums.LogState;

public class ApplicationLogEntry extends LogEntry{

	private String type;
	private String host;
	
	public ApplicationLogEntry(String id, LogState state, long timestamp, String type, String host) {
		super(id, state, timestamp);
		this.type = type;
		this.host = host;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "ApplicationLogEntry [type=" + type + ", host=" + host + ", getId()=" + getId() + ", getState()="
				+ getState() + ", getTimestamp()=" + getTimestamp() + ", toString()=" + super.toString()+"]";
	}
	
	

}

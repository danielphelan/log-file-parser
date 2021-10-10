package ie.credit.suisse.parser.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LogEvent {

	@Id
	private String id;
	private long duration;
	private String type;
	private String host;
	private boolean alert;

	public LogEvent() {
	}

	public LogEvent(LogEntry entry, long duration) {
		this.id = entry.getId();
		this.duration = duration;
		this.alert = duration > 4;

		boolean isApplicationLogEntry = entry instanceof ApplicationLogEntry;
		this.type = isApplicationLogEntry ? (((ApplicationLogEntry) entry).getType()) : null;
		this.host = isApplicationLogEntry ? (((ApplicationLogEntry) entry).getHost()) : null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
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

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
	

}

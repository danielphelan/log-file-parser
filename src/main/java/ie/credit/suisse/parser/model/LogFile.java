package ie.credit.suisse.parser.model;

import java.util.ArrayList;
import java.util.List;


public class LogFile {

	private String filePath;
	private List<LogEntry> fileEntries;
		
	public LogFile(String absolutePath) {
		this.filePath = absolutePath;
		this.fileEntries = new ArrayList<>();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<LogEntry> getFileEntries() {
		return fileEntries;
	}

	public void setFileEntries(List<LogEntry> fileEntries) {
		this.fileEntries = fileEntries;
	}

	@Override
	public String toString() {
		return "LogFile [filePath=" + filePath + ", fileEntries=" + fileEntries + "]";
	}
	
	
}

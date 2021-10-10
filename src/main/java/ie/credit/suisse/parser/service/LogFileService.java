package ie.credit.suisse.parser.service;

import ie.credit.suisse.parser.model.LogFile;

public interface LogFileService {

	public LogFile generateLogFileFromPath(String logPath);

	public void populateLogDatabase(LogFile logFile);
}

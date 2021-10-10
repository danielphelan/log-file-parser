package ie.credit.suisse.parser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ie.credit.suisse.parser.model.LogFile;
import ie.credit.suisse.parser.service.LogFileService;

@Controller
public class LogFileController {

	private static Logger logger = LoggerFactory.getLogger(LogFileController.class);

	@Autowired
	LogFileService logFileService;

	public LogFile createLogFileFromArgs(String path) {
		LogFile logFile = logFileService.generateLogFileFromPath(path);
		return logFile;

	}

	public void populateLogFileData(LogFile logFileToParse) {
		logger.info("Processing log: number of entries found: {} ", logFileToParse.getFileEntries().size());
		logFileService.populateLogDatabase(logFileToParse);
	}

}

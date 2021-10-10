package ie.credit.suisse.parser.service;

import static java.util.stream.Collectors.groupingBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ie.credit.suisse.parser.model.LogEntry;
import ie.credit.suisse.parser.model.LogEvent;
import ie.credit.suisse.parser.model.LogFile;
import ie.credit.suisse.parser.model.enums.LogState;
import ie.credit.suisse.parser.repository.LogFileRepository;

@Service
public class LogFileServiceImpl implements LogFileService {

	private static Logger logger = LoggerFactory.getLogger(LogFileServiceImpl.class);

	@Autowired
	ObjectMapper mapper;

	@Autowired
	LogFileRepository logFileRepository;

	/**
	 * @param logPath - specified path from user
	 * @return generated LogFile containing each log entry and file path
	 */
	@Override
	public LogFile generateLogFileFromPath(String logPath) {
		LogFile logFile = new LogFile(logPath);
		try (Stream<String> stream = Files.lines(Paths.get(logPath))) {
			logFile.getFileEntries().addAll(stream.map(s -> convert(s)).collect(Collectors.toList()));
		} catch (IOException e) {
			logger.error("parseLogFileFromPath: Could not parse json to LogEntry: " + e.getMessage());
		}
		return logFile;
	}

	/**
	 * Jackson mapper converter for each entry of log file. Parses to LogEntry object
	 * 
	 * @param logEntryText
	 * @return
	 */
	private LogEntry convert(String logEntryText) {
		LogEntry mappedLog = null;
		try {
			mappedLog = mapper.readValue(logEntryText, LogEntry.class);
			logger.debug("Mapped log entry: {}", mappedLog.toString());
		} catch (JsonProcessingException e) {
			logger.error("convert: Could not parse json to LogEntry: " + e.getMessage());
		}
		return mappedLog;
	}

	/**
	 * Saves the log entries to the database. Groups by identifier and gets the
	 * start/finish entries.
	 * 
	 * @param logFile
	 */
	@Override
	public void populateLogDatabase(LogFile logFile) {
		Map<String, List<LogEntry>> eventsPerLogId = logFile.getFileEntries().stream()
				.collect(groupingBy(LogEntry::getId));

		for (Iterator<String> iterator = eventsPerLogId.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			List<LogEntry> logEntries = eventsPerLogId.get(key);

			LogEntry startingLog = logEntries.stream().filter(p -> p.getState().equals(LogState.STARTED)).findAny()
					.orElse(null);

			LogEntry endLog = logEntries.stream().filter(p -> p.getState().equals(LogState.FINISHED)).findAny()
					.orElse(null);

			if (startingLog == null || endLog == null) {
				logger.error("Could not create entry for log id {} due to missing log entry", key);
				break;
			}

			logger.info("Storing Log Id {} ", key);
			logger.debug("Starting log: {}", startingLog.toString());
			logger.debug("Ending log: {}", endLog.toString());

			long duration = endLog.getTimestamp() - startingLog.getTimestamp();
			LogEvent logEvent = new LogEvent(startingLog, duration);
			logFileRepository.save(logEvent);

		}

	}

}

package ie.credit.suisse.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import ie.credit.suisse.parser.controller.LogFileController;
import ie.credit.suisse.parser.model.LogFile;

@SpringBootApplication
public class LogFileParserApplication implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(LogFileParserApplication.class);

	@Autowired
	LogFileController logFileController;

	public static void main(String[] args) {
		SpringApplication.run(LogFileParserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		if (args == null || args.length != 1) {
			throw new IllegalArgumentException(
					"Invalid Arguments. Please enter 1 argument with the log file location.");
		}
		
		logger.info("LogFileParserApplication starting with args: {}", args[0]);
		LogFile logFileToParse = logFileController.createLogFileFromArgs(args[0]);

		if (!logFileToParse.getFileEntries().isEmpty()) {
			logFileController.populateLogFileData(logFileToParse);
		}

		System.exit(0);
	}
}

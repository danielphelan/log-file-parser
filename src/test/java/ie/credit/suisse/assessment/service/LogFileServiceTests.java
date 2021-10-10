package ie.credit.suisse.assessment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ie.credit.suisse.parser.model.LogEntry;
import ie.credit.suisse.parser.model.LogEvent;
import ie.credit.suisse.parser.model.LogFile;
import ie.credit.suisse.parser.model.enums.LogState;
import ie.credit.suisse.parser.repository.LogFileRepository;
import ie.credit.suisse.parser.service.LogFileServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LogFileServiceTests {

	@Mock
	private ObjectMapper mapper;

    @Mock
    private LogEntry logEntry;
    
	@Mock
	private LogFileRepository logFileRepository;

	@Captor
	ArgumentCaptor<LogEvent> eventCaptor;
	
    @InjectMocks 
    private LogFileServiceImpl logFileService;


    private String logPath;
    File resourcesDirectory = new File("src/test/resources/logFile.txt");
	
	@BeforeEach
	public void setUp() {
		logPath = resourcesDirectory.getAbsolutePath();
		
	}
	
	@Test
	public void testGenerateLogFileFromPath() throws JsonMappingException, JsonProcessingException {
		when(mapper.readValue(anyString(), eq(LogEntry.class))).thenReturn(logEntry);
		LogFile logFile = logFileService.generateLogFileFromPath(logPath);
		assertEquals(6, logFile.getFileEntries().size());
		
	}

	@Test
	public void testPopulateLogDatabase_falseAlert() {
		LogFile logFile = new LogFile(logPath);
		generateLogEntries(logFile, false);		
		
		logFileService.populateLogDatabase(logFile);
		
		Mockito.verify(logFileRepository).save(eventCaptor.capture());
		LogEvent value = eventCaptor.getValue();	    
		assertEquals(false, value.isAlert());
	}
	
	@Test
	public void testPopulateLogDatabase_trueAlert() {
		LogFile logFile = new LogFile(logPath);
		generateLogEntries(logFile, true);		
		
		logFileService.populateLogDatabase(logFile);
		
		Mockito.verify(logFileRepository).save(eventCaptor.capture());
		LogEvent value = eventCaptor.getValue();	    
		assertEquals(true, value.isAlert());
	}

	private void generateLogEntries(LogFile logFile, boolean triggerAlert) {
		if(triggerAlert) {
			LogEntry started = new LogEntry("test", LogState.STARTED, 12345);
			LogEntry finished = new LogEntry("test", LogState.FINISHED, 12355);
			logFile.getFileEntries().add(started);
			logFile.getFileEntries().add(finished);
		} else {
			LogEntry started = new LogEntry("test", LogState.STARTED, 12345);
			LogEntry finished = new LogEntry("test", LogState.FINISHED, 12346);
			logFile.getFileEntries().add(started);
			logFile.getFileEntries().add(finished);
		}
		
	}
	
	
}



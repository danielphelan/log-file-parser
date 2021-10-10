package ie.credit.suisse.assessment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import ie.credit.suisse.parser.controller.LogFileController;
import ie.credit.suisse.parser.model.LogFile;
import ie.credit.suisse.parser.service.LogFileService;

@ExtendWith(MockitoExtension.class)
public class LogFileControllerTests {

	@Mock
	private LogFileService logFileService;
	
    @InjectMocks 
    private LogFileController logFileController;

    private LogFile logFile;
    private String logPath;
    
	
	@BeforeEach
	public void setUp() {
		logPath = "/users/dir/file.txt";
		logFile = new LogFile(logPath);
	}
	
	@Test
	public void testGenerateLogFileFromPath() {
		when(logFileService.generateLogFileFromPath(logPath)).thenReturn(logFile);
		logFileController.createLogFileFromArgs(logPath);
		assertEquals(logPath, logFile.getFilePath());
	}

}

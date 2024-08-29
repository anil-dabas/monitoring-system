package com.system.monitoring.monitoring;

import com.system.monitoring.domain.SensorData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class CentralMonitoringServiceTest {

    @InjectMocks
    private CentralMonitoringService service;

    @Mock
    private SensorData sensorData;

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outputStream));

        // Set the thresholds using reflection
        setField(service, "temperatureThreshold", 35);
        setField(service, "humidityThreshold", 50);
    }

    @Test
    void testReceiveMessageTemperatureThresholdExceeded() {
        // Arrange
        when(sensorData.getSensorId()).thenReturn("t1");
        when(sensorData.getValue()).thenReturn(40);

        // Act
        service.receiveMessage(sensorData);

        // Assert
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Alarm! Temperature exceeded: 40°C"));
    }

    @Test
    void testReceiveMessageHumidityThresholdExceeded() {
        // Arrange
        when(sensorData.getSensorId()).thenReturn("h1");
        when(sensorData.getValue()).thenReturn(60);

        // Act
        service.receiveMessage(sensorData);

        // Assert
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Alarm! Humidity exceeded: 60%"));
    }

    @Test
    void testReceiveMessageNoAlarm() {
        // Arrange
        when(sensorData.getSensorId()).thenReturn("t1");
        when(sensorData.getValue()).thenReturn(30); // Below threshold

        // Act
        service.receiveMessage(sensorData);

        // Assert
        String output = outputStream.toString().trim();
        System.out.println(output);
        assertFalse(output.contains("Alarm! Temperature exceeded:")); // No output should be present
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outputStream.reset();
    }
}

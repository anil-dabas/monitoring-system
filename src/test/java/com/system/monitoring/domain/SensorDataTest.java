package com.system.monitoring.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorDataTest {

    @Test
    void testParseSensorData() {
        // Arrange
        String message = "sensor_id=t1;value=25";

        // Act
        SensorData sensorData = SensorData.parseSensorData(message);

        // Assert
        assertNotNull(sensorData);
        assertEquals("t1", sensorData.getSensorId());
        assertEquals(25, sensorData.getValue());
    }

    @Test
    void testParseSensorDataInvalidFormat() {
        // Arrange
        String message = "sensor_idt1value25"; // Invalid format

        // Act & Assert
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            SensorData.parseSensorData(message);
        });
    }

}
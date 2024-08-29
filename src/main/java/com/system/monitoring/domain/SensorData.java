package com.system.monitoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorData implements Serializable {
    private String sensorId;
    private int value;

    public static SensorData parseSensorData(String message) {
        String[] parts = message.split(";");
        String sensorId = parts[0].split("=")[1];
        int value = Integer.parseInt(parts[1].split("=")[1]);
        return new SensorData(sensorId, value);
    }
}

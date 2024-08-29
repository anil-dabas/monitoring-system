package com.system.monitoring.monitoring;

import com.system.monitoring.domain.SensorData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.stereotype.Service;

import static com.system.monitoring.util.Constants.SENSOR_DATA;

@Service
public class CentralMonitoringService {

    @Value("${app.sensor.threshold.temperature:35}")
    private int temperatureThreshold;
    @Value("${app.sensor.threshold.humidity:50}")
    private int humidityThreshold;

    @JmsListener(destination = SENSOR_DATA)
    public void receiveMessage(SensorData data) {
        checkThreshold(data);
    }

    private void checkThreshold(SensorData data) {
        if (data.getSensorId().startsWith("t") && data.getValue() > temperatureThreshold) {
            System.out.printf("Alarm! Temperature exceeded:     Sensor Id %s        Current Temperature %d°C\n" ,data.getSensorId(),data.getValue());
        } else if (data.getSensorId().startsWith("h") && data.getValue() > humidityThreshold) {
            System.out.printf("Alarm! Humidity exceeded:        Sensor Id %s        Current Humidity %d%% \n", data.getSensorId(),data.getValue());
        }
    }

}

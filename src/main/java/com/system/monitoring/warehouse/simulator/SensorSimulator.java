package com.system.monitoring.warehouse.simulator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

import static com.system.monitoring.util.Constants.HUMIDITY_PORT;
import static com.system.monitoring.util.Constants.TEMPERATURE_PORT;

@Slf4j
@Component
public class SensorSimulator {

    private static final Timer TIMER = new Timer();
    private final Random random = new Random();

    @Value("${app.sensor.list:t1,h1,t2,h2,t3,h3}")
    private String sensors;

    public void initSimulators(){
        CompletableFuture.runAsync(() -> Arrays.stream(sensors.split(",")).forEach(sensor -> new Task(sensor).run()));
    }

    private class Task extends TimerTask {
        final String sensorId;

        public Task(String sensorId) {
            this.sensorId = sensorId;
        }

        @Override
        public void run() {
            int delay = (5 + random.nextInt(20)) * 100;
            TIMER.schedule(new Task(sensorId), delay);
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress address = InetAddress.getByName("localhost");
                int value = random.nextInt(60 - 15) + 15;
                String message = String.format("sensor_id=%s;value=%d", sensorId, value);
                byte[] buffer = message.getBytes();

                int port = sensorId.startsWith("t") ? TEMPERATURE_PORT : HUMIDITY_PORT;
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
            } catch (Exception e) {
                log.error("Error Generating the sensor results", e);
            }
        }
    }

}

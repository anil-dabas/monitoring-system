package com.system.monitoring.warehouse.service;

import com.system.monitoring.domain.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static com.system.monitoring.domain.SensorData.parseSensorData;
import static com.system.monitoring.util.Constants.SENSOR_DATA;

@Service
public class WarehouseService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void startListeningAndPublishing(int port) {
        Flux.<SensorData>create(sink -> {
                    try (DatagramSocket socket = new DatagramSocket(port)) {
                        byte[] buffer = new byte[1024];
                        while (true) {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);
                            String received = new String(packet.getData(), 0, packet.getLength());
                            SensorData data = parseSensorData(received);
                            sink.next(data);
                        }
                    } catch (Exception e) {
                        sink.error(e);
                    }
                })
                .doOnNext(this::publishToJms)
                .subscribe();
    }

    private void publishToJms(SensorData data) {
        jmsTemplate.convertAndSend(SENSOR_DATA, data);
    }
}

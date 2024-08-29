package com.system.monitoring;

import com.system.monitoring.warehouse.service.WarehouseService;
import com.system.monitoring.warehouse.simulator.SensorSimulator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

import java.util.concurrent.CompletableFuture;

import static com.system.monitoring.util.Constants.HUMIDITY_PORT;
import static com.system.monitoring.util.Constants.TEMPERATURE_PORT;

@EnableJms
@SpringBootApplication
public class MonitoringSystemApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MonitoringSystemApplication.class, args);
		SensorSimulator simulator = context.getBean(SensorSimulator.class);
		simulator.initSimulators();
		WarehouseService warehouseService = context.getBean(WarehouseService.class);

		// Run the listeners asynchronously
		CompletableFuture.runAsync(() -> warehouseService.startListeningAndPublishing(HUMIDITY_PORT));
		CompletableFuture.runAsync(() -> warehouseService.startListeningAndPublishing(TEMPERATURE_PORT));
	}

}

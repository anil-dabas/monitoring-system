package com.system.monitoring.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {
    public static final String TEMPERATURE_SYMBOL = "t";
    public static final int TEMPERATURE_PORT = 3344;
    public static final int HUMIDITY_PORT = 3355;
    public static final String SENSOR_DATA = "sensorData";
    public static final InetAddress ADDRESS;
    public static final int DELAY_BOUND = 200;
    public static final int VALUE_BOUND = 60 - 15;

    static {
        try {
            ADDRESS = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

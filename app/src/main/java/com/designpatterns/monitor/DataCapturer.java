package com.designpatterns.monitor;

import com.designpatterns.datasource.DataSource;

import java.util.ArrayList;
import java.util.List;

public class DataCapturer implements DataSource {
    private final List<TemperatureSensor> sensors;

    public DataCapturer() {
        sensors = new ArrayList<>();
        sensors.add(new TemperatureSensor("SENSOR-1"));
        sensors.add(new TemperatureSensor("SENSOR-2"));
        sensors.add(new TemperatureSensor("SENSOR-3"));
    }

    @Deprecated
    public List<TemperatureSensor> getSensors() {
        return sensors;
    }

    public List<Double> captureAllTemperatures() {
        List<Double> temps = new ArrayList<>();
        for (TemperatureSensor sensor : sensors) {
            temps.add(sensor.getTemperature());
        }
        return temps;
    }

    @Override
    public List<Double> readData() {
        return captureAllTemperatures();
    }
}

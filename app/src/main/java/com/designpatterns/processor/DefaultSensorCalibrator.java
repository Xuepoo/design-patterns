package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;
import java.util.ArrayList;
import java.util.List;

public class DefaultSensorCalibrator implements SensorCalibrator {
    @Override
    public void calibrate(TemperatureData data, double offset) {
        if (data == null || data.isEmpty()) return;
        List<Double> temps = data.getAllTemps();
        List<Double> calibratedTemps = new ArrayList<>();
        for (double t : temps) {
            calibratedTemps.add(t + offset);
        }
        data.setAllTemps(calibratedTemps);
        System.out.println("数据已校准，偏差值: " + offset);
    }
}

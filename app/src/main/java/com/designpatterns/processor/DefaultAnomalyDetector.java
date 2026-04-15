package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;
import java.util.ArrayList;
import java.util.List;

public class DefaultAnomalyDetector implements AnomalyDetector {
    private static final double MIN_NORMAL = 20.0;
    private static final double MAX_NORMAL = 30.0;

    @Override
    public List<Double> identifyAbnormal(TemperatureData data) {
        List<Double> abnormal = new ArrayList<>();
        if (data == null || data.isEmpty()) return abnormal;
        for (double temp : data.getAllTemps()) {
            if (temp < MIN_NORMAL || temp > MAX_NORMAL) {
                abnormal.add(temp);
            }
        }
        return abnormal;
    }
}

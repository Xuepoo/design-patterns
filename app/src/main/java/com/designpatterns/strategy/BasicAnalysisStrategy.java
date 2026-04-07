package com.designpatterns.strategy;

import com.designpatterns.model.TemperatureData;
import java.util.ArrayList;
import java.util.List;

public class BasicAnalysisStrategy implements AnalysisStrategy {
    private static final double NORMAL_MIN = 20.0;
    private static final double NORMAL_MAX = 30.0;

    @Override
    public double findMax(TemperatureData data) {
        return data.getAllTemps().stream()
            .mapToDouble(Double::doubleValue)
            .max()
            .orElse(Double.NaN);
    }

    @Override
    public double findMin(TemperatureData data) {
        return data.getAllTemps().stream()
            .mapToDouble(Double::doubleValue)
            .min()
            .orElse(Double.NaN);
    }

    @Override
    public List<Double> getAbnormalTemps(TemperatureData data) {
        List<Double> abnormal = new ArrayList<>();
        for (double temp : data.getAllTemps()) {
            if (temp < NORMAL_MIN || temp > NORMAL_MAX) {
                abnormal.add(temp);
            }
        }
        return abnormal;
    }
}

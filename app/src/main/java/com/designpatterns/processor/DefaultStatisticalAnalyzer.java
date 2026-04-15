package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;
import java.util.List;

public class DefaultStatisticalAnalyzer implements StatisticalAnalyzer {
    @Override
    public double calculateMean(TemperatureData data) {
        if (data == null || data.isEmpty()) return Double.NaN;
        List<Double> temps = data.getAllTemps();
        double sum = 0;
        for (double t : temps) sum += t;
        return sum / temps.size();
    }

    @Override
    public double calculateVariance(TemperatureData data) {
        if (data == null || data.isEmpty()) return Double.NaN;
        double mean = calculateMean(data);
        List<Double> temps = data.getAllTemps();
        double varianceSum = 0;
        for (double t : temps) {
            varianceSum += Math.pow(t - mean, 2);
        }
        return varianceSum / temps.size();
    }

    @Override
    public double findMax(TemperatureData data) {
        if (data == null || data.isEmpty()) return Double.NaN;
        double max = -Double.MAX_VALUE;
        for (double t : data.getAllTemps()) {
            if (t > max) max = t;
        }
        return max;
    }

    @Override
    public double findMin(TemperatureData data) {
        if (data == null || data.isEmpty()) return Double.NaN;
        double min = Double.MAX_VALUE;
        for (double t : data.getAllTemps()) {
            if (t < min) min = t;
        }
        return min;
    }
}

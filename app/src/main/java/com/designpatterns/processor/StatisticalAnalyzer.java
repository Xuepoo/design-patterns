package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;

public interface StatisticalAnalyzer {
    double calculateMean(TemperatureData data);
    double calculateVariance(TemperatureData data);
    double findMax(TemperatureData data);
    double findMin(TemperatureData data);
}

package com.designpatterns.strategy;

import com.designpatterns.model.TemperatureData;
import java.util.List;

public interface AnalysisStrategy {
    double findMax(TemperatureData data);
    double findMin(TemperatureData data);
    List<Double> getAbnormalTemps(TemperatureData data);
}

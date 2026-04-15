package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;
import java.util.List;

public interface AnomalyDetector {
    List<Double> identifyAbnormal(TemperatureData data);
}

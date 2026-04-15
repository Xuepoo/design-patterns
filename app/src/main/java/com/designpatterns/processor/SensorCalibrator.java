package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;

public interface SensorCalibrator {
    void calibrate(TemperatureData data, double offset);
}

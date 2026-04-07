package com.designpatterns.observer;

import com.designpatterns.model.TemperatureData;

public interface DataObserver {
    void update(TemperatureData data);
}

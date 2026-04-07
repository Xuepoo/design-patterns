package com.designpatterns.observer;

import com.designpatterns.model.TemperatureData;

public class DisplayObserver implements DataObserver {
    @Override
    public void update(TemperatureData data) {
        System.out.println("显示: " + data.getAllTemps());
    }
}

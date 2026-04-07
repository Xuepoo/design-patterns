package com.designpatterns.observer;

import com.designpatterns.model.TemperatureData;

import java.util.ArrayList;
import java.util.List;

public class DataSubject {
    private final List<DataObserver> observers = new ArrayList<>();
    private TemperatureData temperatureData;

    public void attach(DataObserver observer) {
        observers.add(observer);
    }

    public void detach(DataObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (DataObserver observer : observers) {
            observer.update(temperatureData);
        }
    }

    public void setData(TemperatureData data) {
        this.temperatureData = data;
        notifyObservers();
    }

    public TemperatureData getData() {
        return temperatureData;
    }

    public int getObserverCount() {
        return observers.size();
    }
}

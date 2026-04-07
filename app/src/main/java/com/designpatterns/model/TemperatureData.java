package com.designpatterns.model;

import java.util.ArrayList;
import java.util.List;

public class TemperatureData {
    private List<Double> temperatures;

    public TemperatureData() {
        this.temperatures = new ArrayList<>();
    }

    public void addTemp(double temp) {
        temperatures.add(temp);
    }

    public List<Double> getAllTemps() {
        return new ArrayList<>(temperatures);
    }

    public boolean isEmpty() {
        return temperatures.isEmpty();
    }

    public int size() {
        return temperatures.size();
    }
}

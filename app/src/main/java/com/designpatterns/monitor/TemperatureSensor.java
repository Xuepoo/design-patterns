package com.designpatterns.monitor;

import java.util.Random;

public class TemperatureSensor {
    private final String id;
    private final Random random = new Random();

    public TemperatureSensor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public double getTemperature() {
        double temp;
        if (random.nextDouble() < 0.15) {
            temp = random.nextDouble() * 10 + 5;
        } else if (random.nextDouble() < 0.1) {
            temp = random.nextDouble() * 10 + 35;
        } else {
            temp = 20.0 + random.nextDouble() * 10.0;
        }
        return Math.round(temp * 10) / 10.0;
    }
}

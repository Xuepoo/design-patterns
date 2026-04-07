package com.designpatterns.datasource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SensorDataSource implements DataSource {
    private static final double NORMAL_MIN = 20.0;
    private static final double NORMAL_MAX = 30.0;

    @Override
    public List<Double> readData() {
        Random random = new Random();
        int count = 5 + random.nextInt(6);
        Double[] temps = new Double[count];

        for (int i = 0; i < count; i++) {
            if (random.nextDouble() < 0.15) {
                temps[i] = random.nextDouble() * 15 + 3;
            } else if (random.nextDouble() < 0.1) {
                temps[i] = random.nextDouble() * 5 + 32;
            } else {
                temps[i] = NORMAL_MIN + random.nextDouble() * (NORMAL_MAX - NORMAL_MIN);
            }
            temps[i] = Math.round(temps[i] * 10) / 10.0;
        }

        return Arrays.asList(temps);
    }
}

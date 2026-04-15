package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;
import java.util.Collections;
import java.util.List;

public class DefaultDataSorter implements DataSorter {
    @Override
    public List<Double> sortData(TemperatureData data) {
        if (data == null || data.isEmpty()) return Collections.emptyList();
        List<Double> sorted = data.getAllTemps();
        Collections.sort(sorted);
        return sorted;
    }
}

package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;
import java.util.List;

public interface DataSorter {
    List<Double> sortData(TemperatureData data);
}

package com.designpatterns.context;

import com.designpatterns.model.TemperatureData;
import com.designpatterns.strategy.AnalysisStrategy;
import java.util.List;

public class TemperatureAnalyzer {
    private AnalysisStrategy strategy;

    public TemperatureAnalyzer(AnalysisStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(AnalysisStrategy strategy) {
        this.strategy = strategy;
    }

    public double findMax(TemperatureData data) {
        return strategy.findMax(data);
    }

    public double findMin(TemperatureData data) {
        return strategy.findMin(data);
    }

    public List<Double> getAbnormalTemps(TemperatureData data) {
        return strategy.getAbnormalTemps(data);
    }
}

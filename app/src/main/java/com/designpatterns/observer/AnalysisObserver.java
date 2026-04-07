package com.designpatterns.observer;

import com.designpatterns.context.TemperatureAnalyzer;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.strategy.AdvancedAnalysisStrategy;

import java.util.List;

public class AnalysisObserver implements DataObserver {
    private final TemperatureAnalyzer analyzer = new TemperatureAnalyzer(new AdvancedAnalysisStrategy());

    @Override
    public void update(TemperatureData data) {
        double max = analyzer.findMax(data);
        double min = analyzer.findMin(data);
        List<Double> abnormal = analyzer.getAbnormalTemps(data);
        System.out.println("分析: max=" + max + ", min=" + min + ", 异常=" + abnormal);
    }
}

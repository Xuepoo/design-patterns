package com.designpatterns.observer;

import com.designpatterns.context.TemperatureAnalyzer;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.strategy.AdvancedAnalysisStrategy;

import java.util.List;

public class AnalysisObserver implements DataObserver {
    private static int instanceCount = 0;
    private final String name;
    private final TemperatureAnalyzer analyzer;

    public AnalysisObserver() {
        instanceCount++;
        this.name = "AnalysisObserver-" + instanceCount;
        this.analyzer = new TemperatureAnalyzer(new AdvancedAnalysisStrategy());
    }

    @Override
    public void update(TemperatureData data) {
        System.out.println("\n[" + name + "] 分析模块收到通知:");
        System.out.println("  最高温度: " + analyzer.findMax(data) + "℃");
        System.out.println("  最低温度: " + analyzer.findMin(data) + "℃");

        List<Double> abnormal = analyzer.getAbnormalTemps(data);
        System.out.println("  异常温度(排序): " + abnormal);

        if (!abnormal.isEmpty()) {
            System.out.println("  ⚠️ 检测到 " + abnormal.size() + " 个异常温度点!");
        }
    }
}

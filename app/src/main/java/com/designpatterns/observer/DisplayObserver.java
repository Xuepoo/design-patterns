package com.designpatterns.observer;

import com.designpatterns.model.TemperatureData;

public class DisplayObserver implements DataObserver {
    private static int instanceCount = 0;
    private final String name;

    public DisplayObserver() {
        instanceCount++;
        this.name = "DisplayObserver-" + instanceCount;
    }

    @Override
    public void update(TemperatureData data) {
        System.out.println("\n[" + name + "] 显示模块收到通知:");
        System.out.println("  原始数据: " + data.getAllTemps());
        System.out.println("  数据条数: " + data.size());
    }
}

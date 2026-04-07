package com.designpatterns.observer;

import com.designpatterns.model.TemperatureData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StorageObserver implements DataObserver {
    private final String filePath;

    public StorageObserver(String filePath) {
        this.filePath = filePath;
    }

    public StorageObserver() {
        this("log.txt");
    }

    @Override
    public void update(TemperatureData data) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath, true))) {
            out.println(data.getAllTemps());
            System.out.println("已保存到: " + filePath);
        } catch (IOException e) {
            System.out.println("保存失败: " + e.getMessage());
        }
    }
}

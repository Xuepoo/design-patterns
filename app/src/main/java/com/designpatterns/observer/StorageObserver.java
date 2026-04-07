package com.designpatterns.observer;

import com.designpatterns.model.TemperatureData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StorageObserver implements DataObserver {
    private static int instanceCount = 0;
    private final String name;
    private final String filePath;

    public StorageObserver(String filePath) {
        instanceCount++;
        this.name = "StorageObserver-" + instanceCount;
        this.filePath = filePath;
    }

    public StorageObserver() {
        this("temperature_log.txt");
    }

    @Override
    public void update(TemperatureData data) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String record = String.format("[%s] %s - %d records",
                timestamp, data.getAllTemps(), data.size());

        System.out.println("\n[" + name + "] 存储模块收到通知:");
        System.out.println("  记录: " + record);

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(record);
            System.out.println("  ✓ 已保存到: " + filePath);
        } catch (IOException e) {
            System.err.println("  ✗ 存储失败: " + e.getMessage());
        }
    }
}

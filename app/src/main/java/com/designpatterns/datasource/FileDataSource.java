package com.designpatterns.datasource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDataSource implements DataSource {
    private final String filePath;

    public FileDataSource(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Double> readData() {
        List<Double> temps = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                for (String part : parts) {
                    try {
                        temps.add(Double.parseDouble(part));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件失败: " + e.getMessage());
        }

        return temps;
    }
}

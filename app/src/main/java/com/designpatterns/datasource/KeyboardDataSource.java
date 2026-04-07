package com.designpatterns.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class KeyboardDataSource implements DataSource {
    private final Scanner scanner;
    private final String[] inputData;

    public KeyboardDataSource() {
        this.scanner = new Scanner(System.in);
        this.inputData = null;
    }

    public KeyboardDataSource(String inputData) {
        this.scanner = null;
        this.inputData = inputData.trim().split("\\s+");
    }

    @Override
    public List<Double> readData() {
        String[] parts;
        if (inputData != null) {
            parts = inputData;
        } else {
            System.out.print("请输入温度数据（空格分隔）: ");
            String input = scanner.nextLine();
            parts = input.trim().split("\\s+");
        }

        List<Double> temps = new ArrayList<>();
        for (String part : parts) {
            if (!part.isEmpty()) {
                try {
                    temps.add(Double.parseDouble(part));
                } catch (NumberFormatException e) {
                    System.err.println("无效数据: " + part);
                }
            }
        }

        return temps;
    }
}

package com.designpatterns.datasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class KeyboardDataSource implements DataSource {
    private final Scanner scanner;

    public KeyboardDataSource() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public List<Double> readData() {
        System.out.print("请输入温度数据（空格分隔）: ");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");

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

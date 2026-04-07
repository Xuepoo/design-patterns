package com.designpatterns;

import com.designpatterns.context.TemperatureAnalyzer;
import com.designpatterns.datasource.DataSource;
import com.designpatterns.datasource.DataSourceFactory;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.strategy.AdvancedAnalysisStrategy;
import com.designpatterns.strategy.BasicAnalysisStrategy;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== 温度数据分析系统 ==========\n");
        System.out.println("请选择数据来源：");
        System.out.println("1. 传感器数据（自动生成）");
        System.out.println("2. 文件数据");
        System.out.println("3. 键盘输入");
        System.out.print("> ");

        String choice = scanner.nextLine().trim();
        DataSource dataSource;

        switch (choice) {
            case "1" -> {
                dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_SENSOR);
                System.out.println("\n使用传感器数据源...");
            }
            case "2" -> {
                System.out.print("请输入文件路径: ");
                String filePath = scanner.nextLine().trim();
                dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_FILE, filePath);
                System.out.println("\n使用文件数据源: " + filePath);
            }
            case "3" -> {
                dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_KEYBOARD);
                System.out.println("\n使用键盘输入数据源...");
            }
            default -> {
                System.out.println("无效选择，使用键盘输入...");
                dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_KEYBOARD);
            }
        }

        List<Double> temps = dataSource.readData();

        if (temps.isEmpty()) {
            System.out.println("没有获取到数据！");
            return;
        }

        TemperatureData data = new TemperatureData();
        for (double temp : temps) {
            data.addTemp(temp);
        }

        System.out.println("\n原始数据: " + temps);
        System.out.println("\n========== 分析结果 ==========\n");

        TemperatureAnalyzer analyzer = new TemperatureAnalyzer(new BasicAnalysisStrategy());

        System.out.println("【基础分析】");
        System.out.println("最高温度: " + analyzer.findMax(data) + "℃");
        System.out.println("最低温度: " + analyzer.findMin(data) + "℃");
        System.out.println("异常温度: " + analyzer.getAbnormalTemps(data));

        analyzer.setStrategy(new AdvancedAnalysisStrategy());
        AdvancedAnalysisStrategy advanced = new AdvancedAnalysisStrategy();

        System.out.println("\n【高级分析】");
        System.out.println("均值: " + String.format("%.2f", advanced.calculateMean(data)) + "℃");
        System.out.println("方差: " + String.format("%.4f", advanced.calculateVariance(data)));
        System.out.println("异常温度(排序): " + analyzer.getAbnormalTemps(data));

        System.out.println("\n================================");
        System.out.println("温度判定: 正常 20~30℃ | 异常低 <20℃ | 异常高 >30℃");
    }
}

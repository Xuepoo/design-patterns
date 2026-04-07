package com.designpatterns;

import com.designpatterns.datasource.DataSource;
import com.designpatterns.datasource.DataSourceFactory;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.observer.AnalysisObserver;
import com.designpatterns.observer.DataObserver;
import com.designpatterns.observer.DataSubject;
import com.designpatterns.observer.DisplayObserver;
import com.designpatterns.observer.StorageObserver;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== 温度数据采集与分析系统 ==========");
        System.out.println("        (观察者模式 + 策略模式 + 工厂模式)\n");

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

        System.out.println("\n========== 初始化观察者 ==========\n");

        DataSubject subject = new DataSubject();

        DataObserver displayObserver = new DisplayObserver();
        DataObserver analysisObserver = new AnalysisObserver();
        DataObserver storageObserver = new StorageObserver();

        subject.attach(displayObserver);
        subject.attach(analysisObserver);
        subject.attach(storageObserver);

        System.out.println("已注册 " + subject.getObserverCount() + " 个观察者");

        System.out.println("\n========== 模拟数据变化（触发通知） ==========\n");

        System.out.println("--- 场景1: 传感器数据到达 ---");
        TemperatureData data = new TemperatureData();
        for (double temp : temps) {
            data.addTemp(temp);
        }
        subject.setData(data);

        System.out.println("\n--- 场景2: 模拟异常数据更新 ---");
        TemperatureData newData = new TemperatureData();
        newData.addTemp(25.0);
        newData.addTemp(28.5);
        newData.addTemp(10.0);
        newData.addTemp(35.0);
        newData.addTemp(22.0);
        subject.setData(newData);

        System.out.println("\n================================");
        System.out.println("温度判定: 正常 20~30℃ | 异常低 <20℃ | 异常高 >30℃");
        System.out.println("================================");
        System.out.println("设计模式: 工厂模式 + 策略模式 + 观察者模式");
    }
}

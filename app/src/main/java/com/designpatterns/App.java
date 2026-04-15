package com.designpatterns;

import com.designpatterns.datasource.DataSource;
import com.designpatterns.datasource.DataSourceFactory;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.notification.EmailNotificationService;
import com.designpatterns.observer.AnalysisObserver;
import com.designpatterns.observer.DataSubject;
import com.designpatterns.observer.DisplayObserver;
import com.designpatterns.observer.RemoteNotificationObserver;
import com.designpatterns.observer.StorageObserver;
import com.designpatterns.processor.DefaultAnomalyDetector;
import com.designpatterns.processor.DefaultDataSorter;
import com.designpatterns.processor.DefaultSensorCalibrator;
import com.designpatterns.processor.DefaultStatisticalAnalyzer;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("温度数据分析系统\n");

        DataSource dataSource;
        String inputData = "25.1 24.8 26.3 5.2 27.2 32.5 24.5";

        if (args.length > 0) {
            switch (args[0]) {
                case "1" -> dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_SENSOR);
                case "2" -> dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_FILE, args.length > 1 ? args[1] : "data.txt");
                case "3" -> dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_KEYBOARD, args.length > 1 ? args[1] : inputData);
                default -> dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_SENSOR);
            }
        } else {
            System.out.println("（直接按回车使用默认数据: " + inputData + "）");
            System.out.print("输入温度数据: ");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                line = inputData;
            }
            dataSource = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_KEYBOARD, line);
        }

        List<Double> temps = dataSource.readData();
        if (temps.isEmpty()) {
            System.out.println("无数据");
            return;
        }

        DataSubject subject = new DataSubject();
        subject.attach(new DisplayObserver());
        subject.attach(new AnalysisObserver());
        subject.attach(new StorageObserver("temperature_log.txt"));
        
        // 第四次作业新增：远程通知观察者
        subject.attach(new RemoteNotificationObserver(new EmailNotificationService(), new DefaultAnomalyDetector()));

        TemperatureData data = new TemperatureData();
        for (double t : temps) {
            data.addTemp(t);
        }
        subject.setData(data);
        
        // 第四次作业新增：使用符合接口隔离原则的接口处理数据
        System.out.println("\n--- 接口隔离原则 (ISP) 处理演示 ---");
        DefaultStatisticalAnalyzer statAnalyzer = new DefaultStatisticalAnalyzer();
        System.out.println("统计分析: 均值=" + String.format("%.2f", statAnalyzer.calculateMean(data)) 
                + ", 方差=" + String.format("%.2f", statAnalyzer.calculateVariance(data)));
        
        DefaultDataSorter sorter = new DefaultDataSorter();
        System.out.println("数据排序: " + sorter.sortData(data));
        
        DefaultSensorCalibrator calibrator = new DefaultSensorCalibrator();
        calibrator.calibrate(data, -0.5); // 模拟传感器标定偏差
        System.out.println("标定后重新计算均值: " + String.format("%.2f", statAnalyzer.calculateMean(data)));
    }
}

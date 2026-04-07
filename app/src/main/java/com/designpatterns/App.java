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
            System.out.print("输入温度数据: ");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
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
        subject.attach(new StorageObserver());

        TemperatureData data = new TemperatureData();
        for (double t : temps) {
            data.addTemp(t);
        }
        subject.setData(data);
    }
}

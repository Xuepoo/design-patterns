package com.designpatterns;

import com.designpatterns.datasource.DataSourceFactory;
import com.designpatterns.monitor.MonitorSystem;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("软件设计模式与重构 - 第五次作业：迪米特法则与门面模式\n");

        MonitorSystem system = new MonitorSystem();
        
        String inputData = "25.1 24.8 26.3 5.2 27.2 32.5 24.5";

        if (args.length > 0) {
            String type = switch (args[0]) {
                case "1" -> DataSourceFactory.TYPE_SENSOR;
                case "2" -> DataSourceFactory.TYPE_FILE;
                case "3" -> DataSourceFactory.TYPE_KEYBOARD;
                default -> DataSourceFactory.TYPE_SENSOR;
            };
            String input = args.length > 1 ? args[1] : (type.equals(DataSourceFactory.TYPE_KEYBOARD) ? inputData : "data.txt");
            system.setDataSource(type, input);
        } else {
            System.out.println("（直接按回车使用默认数据: " + inputData + "）");
            System.out.print("输入温度数据: ");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                line = inputData;
            }
            system.setDataSource(DataSourceFactory.TYPE_KEYBOARD, line);
        }

        system.run();
    }
}

package com.designpatterns.monitor;

import com.designpatterns.datasource.DataSource;
import com.designpatterns.datasource.DataSourceFactory;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.notification.EmailNotificationService;
import com.designpatterns.observer.*;
import com.designpatterns.processor.*;

import java.util.List;

public class MonitorSystem {
    private final DataCapturer capturer;
    private final DataSubject dataSubject;
    private DataSource currentDataSource;

    public MonitorSystem() {
        this.capturer = new DataCapturer();
        this.dataSubject = new DataSubject();

        this.dataSubject.attach(new DisplayObserver());
        this.dataSubject.attach(new AnalysisObserver());
        this.dataSubject.attach(new StorageObserver("temperature_log.txt"));
        this.dataSubject.attach(new RemoteNotificationObserver(
                new EmailNotificationService(), new DefaultAnomalyDetector()));
    }

    public void setDataSource(String type, String input) {
        this.currentDataSource = DataSourceFactory.createDataSource(type, input);
    }

    @Deprecated
    public void badRunMonitor() {
        System.out.println("--- 警告：正在运行违反迪米特法则的代码 ---");
        List<TemperatureSensor> sensors = capturer.getSensors();
        for (TemperatureSensor sensor : sensors) {
            double temp = sensor.getTemperature();
            System.out.println(sensor.getId() + " 温度: " + temp);
        }
    }

    private List<Double> captureData() {
        if (currentDataSource != null) {
            return currentDataSource.readData();
        } else {
            return capturer.captureAllTemperatures();
        }
    }

    public void run() {
        System.out.println("========== 温度监控系统已启动 (整合了作业1~5) ==========");
        
        List<Double> temps = captureData();
        if (temps.isEmpty()) {
            System.out.println("⚠️ 未采集到任何数据");
            return;
        }

        TemperatureData data = new TemperatureData();
        for (double t : temps) {
            data.addTemp(t);
        }
        
        dataSubject.setData(data);

        System.out.println("\n--- 独立数据处理演示 (体现 ISP 接口隔离) ---");
        DefaultStatisticalAnalyzer statAnalyzer = new DefaultStatisticalAnalyzer();
        System.out.println("系统平均温度=" + String.format("%.2f", statAnalyzer.calculateMean(data)) 
                + ", 温度极差=" + String.format("%.2f", statAnalyzer.findMax(data) - statAnalyzer.findMin(data)));
        
        DefaultDataSorter sorter = new DefaultDataSorter();
        System.out.println("全量数据排序=" + sorter.sortData(data));
        
        System.out.println("========================================================\n");
    }
}

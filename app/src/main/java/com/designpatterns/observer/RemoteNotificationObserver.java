package com.designpatterns.observer;

import com.designpatterns.model.TemperatureData;
import com.designpatterns.notification.NotificationService;
import com.designpatterns.processor.AnomalyDetector;
import java.util.List;

public class RemoteNotificationObserver implements DataObserver {
    private final NotificationService notificationService;
    private final AnomalyDetector anomalyDetector;

    public RemoteNotificationObserver(NotificationService notificationService, AnomalyDetector anomalyDetector) {
        this.notificationService = notificationService;
        this.anomalyDetector = anomalyDetector;
    }

    @Override
    public void update(TemperatureData data) {
        List<Double> anomalies = anomalyDetector.identifyAbnormal(data);
        if (!anomalies.isEmpty()) {
            notificationService.notify("检测到 " + anomalies.size() + " 个温度异常点！异常数据: " + anomalies);
        }
    }
}

package com.designpatterns.assignment_4;

import com.designpatterns.model.TemperatureData;
import com.designpatterns.notification.NotificationService;
import com.designpatterns.observer.RemoteNotificationObserver;
import com.designpatterns.processor.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Assignment4Test {
    private TemperatureData data;

    @BeforeEach
    void setUp() {
        data = new TemperatureData();
        data.addTemp(25.0);
        data.addTemp(10.0); // 异常 < 20
        data.addTemp(22.0);
        data.addTemp(35.0); // 异常 > 30
    }

    @Test
    void testAnomalyDetector() {
        AnomalyDetector detector = new DefaultAnomalyDetector();
        List<Double> abnormal = detector.identifyAbnormal(data);
        assertEquals(2, abnormal.size());
        assertTrue(abnormal.contains(10.0));
        assertTrue(abnormal.contains(35.0));
    }

    @Test
    void testStatisticalAnalyzer() {
        StatisticalAnalyzer analyzer = new DefaultStatisticalAnalyzer();
        assertEquals(35.0, analyzer.findMax(data));
        assertEquals(10.0, analyzer.findMin(data));
        assertEquals(23.0, analyzer.calculateMean(data), 0.01);
        assertTrue(analyzer.calculateVariance(data) > 0);
    }

    @Test
    void testDataSorter() {
        DataSorter sorter = new DefaultDataSorter();
        List<Double> sorted = sorter.sortData(data);
        assertEquals(10.0, sorted.get(0));
        assertEquals(22.0, sorted.get(1));
        assertEquals(25.0, sorted.get(2));
        assertEquals(35.0, sorted.get(3));
    }

    @Test
    void testSensorCalibrator() {
        SensorCalibrator calibrator = new DefaultSensorCalibrator();
        calibrator.calibrate(data, -5.0);
        
        List<Double> temps = data.getAllTemps();
        assertTrue(temps.contains(20.0)); // 25.0 - 5.0
        assertTrue(temps.contains(5.0));  // 10.0 - 5.0
        assertTrue(temps.contains(17.0)); // 22.0 - 5.0
        assertTrue(temps.contains(30.0)); // 35.0 - 5.0
    }

    @Test
    void testRemoteNotification() {
        List<String> messages = new ArrayList<>();
        NotificationService mockService = messages::add;

        RemoteNotificationObserver observer = new RemoteNotificationObserver(mockService, new DefaultAnomalyDetector());
        observer.update(data);

        assertEquals(1, messages.size());
        assertTrue(messages.get(0).contains("2 个温度异常点"));
    }
}

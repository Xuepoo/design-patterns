package com.designpatterns.assignment_5;

import com.designpatterns.monitor.DataCapturer;
import com.designpatterns.monitor.TemperatureSensor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Assignment5Test {

    @Test
    void testTemperatureSensor() {
        TemperatureSensor sensor = new TemperatureSensor("TEST");
        assertEquals("TEST", sensor.getId());
        double temp = sensor.getTemperature();
        assertTrue(temp >= 5.0 && temp <= 45.0, "Sensor temperature should be within expected bounds");
    }

    @Test
    void testDataCapturerLoD() {
        DataCapturer capturer = new DataCapturer();
        
        // 验证遵循迪米特法则的数据采集方法
        List<Double> temps = capturer.captureAllTemperatures();
        
        assertNotNull(temps);
        assertEquals(3, temps.size(), "Should capture temperatures from 3 sensors");
        
        // 验证其也实现了 DataSource 接口，从而可以与之前的模块整合
        List<Double> readTemps = capturer.readData();
        assertEquals(3, readTemps.size());
    }
}

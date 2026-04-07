package com.designpatterns.assignment_3;

import com.designpatterns.model.TemperatureData;
import com.designpatterns.observer.AnalysisObserver;
import com.designpatterns.observer.DataObserver;
import com.designpatterns.observer.DataSubject;
import com.designpatterns.observer.DisplayObserver;
import com.designpatterns.observer.StorageObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Assignment3Test {
    private DataSubject subject;
    private List<String> notifications;

    @BeforeEach
    void setUp() {
        subject = new DataSubject();
        notifications = new ArrayList<>();
    }

    @Test
    void testObserverAttachAndDetach() {
        DataObserver observer = new DisplayObserver();
        assertEquals(0, subject.getObserverCount());

        subject.attach(observer);
        assertEquals(1, subject.getObserverCount());

        subject.detach(observer);
        assertEquals(0, subject.getObserverCount());
    }

    @Test
    void testMultipleObservers() {
        subject.attach(new DisplayObserver());
        subject.attach(new AnalysisObserver());
        subject.attach(new StorageObserver());

        assertEquals(3, subject.getObserverCount());
    }

    @Test
    void testNotificationOnDataChange() {
        TestObserver observer = new TestObserver(notifications);
        subject.attach(observer);

        TemperatureData data = new TemperatureData();
        data.addTemp(25.0);
        data.addTemp(30.0);

        subject.setData(data);

        assertEquals(1, notifications.size());
    }

    @Test
    void testDisplayObserver() {
        DataObserver observer = new DisplayObserver();
        subject.attach(observer);

        TemperatureData data = new TemperatureData();
        data.addTemp(25.5);

        subject.setData(data);
        assertEquals(1, subject.getObserverCount());
    }

    @Test
    void testAnalysisObserver() {
        DataObserver observer = new AnalysisObserver();
        subject.attach(observer);

        TemperatureData data = new TemperatureData();
        data.addTemp(25.0);
        data.addTemp(10.0);
        data.addTemp(35.0);

        subject.setData(data);
        assertEquals(1, subject.getObserverCount());
    }

    @Test
    void testStorageObserver() {
        DataObserver observer = new StorageObserver("test_log.txt");
        subject.attach(observer);

        TemperatureData data = new TemperatureData();
        data.addTemp(25.0);
        data.addTemp(28.0);

        subject.setData(data);
        assertEquals(1, subject.getObserverCount());
    }

    @Test
    void testAbnormalDetectionInObserver() {
        TemperatureData data = new TemperatureData();
        data.addTemp(25.0);
        data.addTemp(10.0);
        data.addTemp(35.0);
        data.addTemp(22.0);

        AnalysisObserver observer = new AnalysisObserver();
        subject.attach(observer);
        subject.setData(data);

        assertNotNull(subject.getData());
    }

    @Test
    void testObserverPatternWithStrategyIntegration() {
        subject.attach(new AnalysisObserver());

        TemperatureData data = new TemperatureData();
        data.addTemp(25.1);
        data.addTemp(24.8);
        data.addTemp(5.2);
        data.addTemp(32.5);

        subject.setData(data);

        TemperatureData stored = subject.getData();
        assertNotNull(stored);
        assertEquals(4, stored.size());
    }

    private static class TestObserver implements DataObserver {
        private final List<String> notifications;

        public TestObserver(List<String> notifications) {
            this.notifications = notifications;
        }

        @Override
        public void update(TemperatureData data) {
            notifications.add("Notified with " + data.size() + " records");
        }
    }
}

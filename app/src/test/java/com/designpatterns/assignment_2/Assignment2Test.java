package com.designpatterns.assignment_2;

import com.designpatterns.context.TemperatureAnalyzer;
import com.designpatterns.datasource.DataSource;
import com.designpatterns.datasource.DataSourceFactory;
import com.designpatterns.datasource.SensorDataSource;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.strategy.AdvancedAnalysisStrategy;
import com.designpatterns.strategy.BasicAnalysisStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Assignment2Test {

    @Test
    void testSensorDataSource() {
        DataSource source = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_SENSOR);
        List<Double> temps = source.readData();
        assertNotNull(temps);
        assertFalse(temps.isEmpty());
    }

    @Test
    void testDataSourceFactory() {
        DataSource sensor = DataSourceFactory.createDataSource(DataSourceFactory.TYPE_SENSOR);
        assertTrue(sensor instanceof SensorDataSource);
    }

    @Test
    void testDataSourceFactoryWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            DataSourceFactory.createDataSource("invalid");
        });
    }

    @Test
    void testDataSourceAnalysis() {
        SensorDataSource source = new SensorDataSource();
        List<Double> temps = source.readData();

        TemperatureData data = new TemperatureData();
        for (double temp : temps) {
            data.addTemp(temp);
        }

        TemperatureAnalyzer analyzer = new TemperatureAnalyzer(new BasicAnalysisStrategy());
        double max = analyzer.findMax(data);
        double min = analyzer.findMin(data);

        assertFalse(Double.isNaN(max));
        assertFalse(Double.isNaN(min));
    }

    @Test
    void testFileDataSource() {
        assertThrows(IllegalArgumentException.class, () -> {
            DataSourceFactory.createDataSource(DataSourceFactory.TYPE_FILE);
        });
    }

    @Test
    void testStrategySwitching() {
        SensorDataSource source = new SensorDataSource();
        List<Double> temps = source.readData();

        TemperatureData data = new TemperatureData();
        for (double temp : temps) {
            data.addTemp(temp);
        }

        TemperatureAnalyzer analyzer = new TemperatureAnalyzer(new BasicAnalysisStrategy());
        double maxBasic = analyzer.findMax(data);

        analyzer.setStrategy(new AdvancedAnalysisStrategy());
        double maxAdvanced = analyzer.findMax(data);

        assertEquals(maxBasic, maxAdvanced);
    }

    @Test
    void testAbnormalTempsSorted() {
        TemperatureData data = new TemperatureData();
        data.addTemp(25.0);
        data.addTemp(10.0);
        data.addTemp(35.0);
        data.addTemp(15.0);

        TemperatureAnalyzer analyzer = new TemperatureAnalyzer(new AdvancedAnalysisStrategy());
        List<Double> abnormal = analyzer.getAbnormalTemps(data);

        assertEquals(3, abnormal.size());
        assertEquals(10.0, abnormal.get(0));
        assertEquals(15.0, abnormal.get(1));
        assertEquals(35.0, abnormal.get(2));
    }

    @Test
    void testAdvancedAnalysisWithSensorData() {
        SensorDataSource source = new SensorDataSource();
        List<Double> temps = source.readData();

        TemperatureData data = new TemperatureData();
        for (double temp : temps) {
            data.addTemp(temp);
        }

        AdvancedAnalysisStrategy advanced = new AdvancedAnalysisStrategy();
        double mean = advanced.calculateMean(data);
        double variance = advanced.calculateVariance(data);

        assertFalse(Double.isNaN(mean));
        assertFalse(Double.isNaN(variance));
        assertTrue(variance >= 0);
    }
}

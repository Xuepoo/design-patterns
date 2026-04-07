package com.designpatterns.assignment_1;

import com.designpatterns.context.TemperatureAnalyzer;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.strategy.AdvancedAnalysisStrategy;
import com.designpatterns.strategy.BasicAnalysisStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Assignment1Test {
    private TemperatureData data;
    private TemperatureAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        data = new TemperatureData();
        data.addTemp(25.1);
        data.addTemp(24.8);
        data.addTemp(26.3);
        data.addTemp(5.2);
        data.addTemp(27.2);
        data.addTemp(32.5);
        data.addTemp(24.5);
        analyzer = new TemperatureAnalyzer(new BasicAnalysisStrategy());
    }

    @Test
    void testFindMax() {
        double max = analyzer.findMax(data);
        assertEquals(32.5, max);
    }

    @Test
    void testFindMin() {
        double min = analyzer.findMin(data);
        assertEquals(5.2, min);
    }

    @Test
    void testGetAbnormalTemps() {
        List<Double> abnormal = analyzer.getAbnormalTemps(data);
        assertEquals(2, abnormal.size());
        assertTrue(abnormal.contains(5.2));
        assertTrue(abnormal.contains(32.5));
    }

    @Test
    void testCalculateMean() {
        AdvancedAnalysisStrategy advanced = new AdvancedAnalysisStrategy();
        double mean = advanced.calculateMean(data);
        assertEquals(23.66, mean, 0.01);
    }

    @Test
    void testCalculateVariance() {
        AdvancedAnalysisStrategy advanced = new AdvancedAnalysisStrategy();
        double variance = advanced.calculateVariance(data);
        assertTrue(variance > 0);
    }

    @Test
    void testSortAbnormalTemps() {
        analyzer.setStrategy(new AdvancedAnalysisStrategy());
        List<Double> abnormal = analyzer.getAbnormalTemps(data);
        assertEquals(5.2, abnormal.get(0));
        assertEquals(32.5, abnormal.get(1));
    }

    @Test
    void testSwitchStrategy() {
        assertEquals(32.5, analyzer.findMax(data));

        analyzer.setStrategy(new AdvancedAnalysisStrategy());
        assertEquals(32.5, analyzer.findMax(data));
    }

    @Test
    void testEmptyData() {
        TemperatureData emptyData = new TemperatureData();
        assertTrue(emptyData.isEmpty());
    }
}

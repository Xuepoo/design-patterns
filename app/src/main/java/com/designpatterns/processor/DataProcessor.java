package com.designpatterns.processor;

import com.designpatterns.model.TemperatureData;
import java.util.List;

/**
 * 这是一个违反接口隔离原则（ISP）的庞大接口设计示例。
 * 所有的处理职责（异常识别、统计、排序、标定）都被放在了一个接口中。
 * 在第四次作业中，此接口已被拆分为职责单一的多个小接口。
 */
@Deprecated
public interface DataProcessor {
    List<Double> identifyAbnormal(TemperatureData data);
    double[] calculateStatistics(TemperatureData data);
    List<Double> sortData(TemperatureData data);
    void calibrateSensor(TemperatureData data, double offset);
}

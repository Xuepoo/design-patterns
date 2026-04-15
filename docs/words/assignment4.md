# 软件设计模式与重构 - 第四次作业报告

**姓名**：[你的姓名]  
**班级**：[你的班级]  
**学号**：[你的学号]  
**日期**：2026-04-16

---

## 1. 课堂笔记 (10分)

### 接口隔离原则 (Interface Segregation Principle, ISP)
- **定义**：不应该强迫客户端依赖它不需要的方法。
- **核心思想**：将臃肿的接口（Fat Interface）拆分为多个职责单一、小的接口。
- **优点**：
    - 降低耦合：客户端只依赖于它们实际使用的接口。
    - 提高灵活性：更容易进行扩展和修改。
    - 清晰度：接口职责更加明确。

### 依赖倒置原则 (Dependency Inversion Principle, DIP)
- **定义**：高层模块不应该依赖于低层模块，二者都应该依赖于抽象；抽象不应该依赖于细节，细节应该依赖于抽象。
- **核心思想**：针对接口编程，而不是针对实现编程。

### 依赖注入 (Dependency Injection, DI)
- **定义**：将一个对象所依赖的对象（依赖项）传递给该对象，而不是让该对象自己创建依赖项。
- **常用方式**：构造器注入、Setter注入。

---

## 2. 练习题内容 (Assignment Requirements)

基于练习3（观察者模式）的温度采集系统进行扩展：
1.  **接口隔离原则 (ISP)**：系统需要对温度数据进行多种处理：异常识别、统计分析、数据排序、传感器标定。将原有的“大接口” `DataProcessor` 拆分为多个职责单一的小接口。
2.  **远程通知**：当检测到异常温度时，支持通过邮件、手机短信等方式进行远程通知。

---

## 3. 自己的方案 (My Solution)

### (1) 接口隔离与重构
- 废弃原本可能存在的臃肿接口 `DataProcessor`（在代码中以 `@Deprecated` 形式保留作为对比）。
- 定义了四个精细化的接口：
    - `AnomalyDetector`：仅负责异常识别。
    - `StatisticalAnalyzer`：负责均值、方差、最大/最小值的统计。
    - `DataSorter`：负责数据排序。
    - `SensorCalibrator`：负责传感器数据标定（加偏置值）。
- 每个具体的实现类（如 `DefaultAnomalyDetector`）仅实现相关的接口，符合单一职责原则（SRP）。

### (2) 远程通知设计 (DIP + DI)
- 定义 `NotificationService` 抽象接口。
- 实现 `EmailNotificationService` 和 `SMSNotificationService` 具体策略。
- 在 `DataObserver` 体系中新增 `RemoteNotificationObserver`，它通过 **构造器注入** 的方式依赖于 `NotificationService` 和 `AnomalyDetector`，实现了高层逻辑与具体发送方式及检测逻辑的解耦。

---

## 4. 类图 (60分)

> 下图展示了系统的整体设计结构。可以看到 `processor` 包下的接口完全隔离。

![Assignment 4 UML](figures/assignment_4.png)

---

## 5. 核心代码 (10分)

### ISP 接口定义示例
```java
// 拆分后的职责单一接口
public interface AnomalyDetector {
    List<Double> identifyAbnormal(TemperatureData data);
}

public interface StatisticalAnalyzer {
    double calculateMean(TemperatureData data);
    double findMax(TemperatureData data);
    // ... 其他统计方法
}
```

### 远程通知实现 (DI)
```java
public class RemoteNotificationObserver implements DataObserver {
    private final NotificationService notificationService;
    private final AnomalyDetector anomalyDetector;

    // 依赖注入：针对抽象编程
    public RemoteNotificationObserver(NotificationService ns, AnomalyDetector ad) {
        this.notificationService = ns;
        this.anomalyDetector = ad;
    }

    @Override
    public void update(TemperatureData data) {
        List<Double> anomalies = anomalyDetector.identifyAbnormal(data);
        if (!anomalies.isEmpty()) {
            notificationService.notify("检测到异常: " + anomalies);
        }
    }
}
```

---

## 6. 运行结果与分析 (10分)

### 运行输出
```text
温度数据分析系统

显示: [36.9, 24.6, 13.1, 15.1, 34.6, 21.6, 26.5, 22.7, 35.8]
分析: max=36.9, min=13.1, 异常=[13.1, 15.1, 34.6, 35.8, 36.9]
已保存到: temperature_log.txt
【Email 通知】发送邮件: 检测到 5 个温度异常点！异常数据: [36.9, 13.1, 15.1, 34.6, 35.8]

--- 接口隔离原则 (ISP) 处理演示 ---
统计分析: 均值=25.66, 方差=67.27
数据排序: [13.1, 15.1, 21.6, 22.7, 24.6, 26.5, 34.6, 35.8, 36.9]
数据已校准，偏差值: -0.5
标定后重新计算均值: 25.16
```

### 分析
1.  **自动触发**：当 `DataSubject` 的数据发生变化时，`RemoteNotificationObserver` 被自动触发。
2.  **异常识别**：通过 `DefaultAnomalyDetector` 识别出了超出 20-30℃ 范围的值。
3.  **多渠道通知**：控制台输出了“【Email 通知】”，证明远程通知模块工作正常。通过简单的配置，可以轻松更换为 `SMSNotificationService`。
4.  **接口隔离验证**：在最后的演示中，我们可以独立使用 `StatisticalAnalyzer` 和 `DataSorter` 而不需要耦合其他功能，证明了 ISP 的有效性。

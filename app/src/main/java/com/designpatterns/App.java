package com.designpatterns;

import com.designpatterns.context.TemperatureAnalyzer;
import com.designpatterns.model.TemperatureData;
import com.designpatterns.strategy.AdvancedAnalysisStrategy;
import com.designpatterns.strategy.BasicAnalysisStrategy;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入温度数据（空格分隔，例如：25.1 24.8 26.3 5.2 27.2 32.5 24.5）：");
        System.out.print("> ");

        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");

        TemperatureData data = new TemperatureData();
        for (String s : parts) {
            if (!s.isEmpty()) {
                data.addTemp(Double.parseDouble(s));
            }
        }

        System.out.println("\n========== 温度数据分析结果 ==========\n");

        TemperatureAnalyzer analyzer = new TemperatureAnalyzer(new BasicAnalysisStrategy());

        System.out.println("【基础分析】");
        System.out.println("最高温度: " + analyzer.findMax(data) + "℃");
        System.out.println("最低温度: " + analyzer.findMin(data) + "℃");
        System.out.println("异常温度: " + analyzer.getAbnormalTemps(data));

        analyzer.setStrategy(new AdvancedAnalysisStrategy());
        AdvancedAnalysisStrategy advanced = new AdvancedAnalysisStrategy();

        System.out.println("\n【高级分析】");
        System.out.println("均值: " + String.format("%.2f", advanced.calculateMean(data)) + "℃");
        System.out.println("方差: " + String.format("%.4f", advanced.calculateVariance(data)));
        System.out.println("异常温度(排序): " + analyzer.getAbnormalTemps(data));

        System.out.println("\n======================================");
        System.out.println("温度判定: 正常 20~30℃ | 异常低 <20℃ | 异常高 >30℃");
    }
}

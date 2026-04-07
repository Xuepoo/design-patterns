package com.designpatterns.datasource;

public class DataSourceFactory {
    public static final String TYPE_SENSOR = "sensor";
    public static final String TYPE_FILE = "file";
    public static final String TYPE_KEYBOARD = "keyboard";

    public static DataSource createDataSource(String type, String... args) {
        return switch (type.toLowerCase()) {
            case TYPE_SENSOR -> new SensorDataSource();
            case TYPE_FILE -> {
                if (args.length == 0) {
                    throw new IllegalArgumentException("FileDataSource 需要文件路径参数");
                }
                yield new FileDataSource(args[0]);
            }
            case TYPE_KEYBOARD -> {
                if (args.length > 0) {
                    yield new KeyboardDataSource(args[0]);
                }
                yield new KeyboardDataSource();
            }
            default -> throw new IllegalArgumentException("未知的数据源类型: " + type);
        };
    }
}

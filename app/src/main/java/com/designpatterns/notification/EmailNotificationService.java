package com.designpatterns.notification;

public class EmailNotificationService implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("【Email 通知】发送邮件: " + message);
    }
}

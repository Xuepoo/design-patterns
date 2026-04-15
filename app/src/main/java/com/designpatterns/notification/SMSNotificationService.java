package com.designpatterns.notification;

public class SMSNotificationService implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("【SMS 通知】发送短信: " + message);
    }
}

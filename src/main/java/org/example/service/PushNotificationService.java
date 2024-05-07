package org.example.service;

import org.example.entity.PushNotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    private final FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }


    public void sendNotification(PushNotificationRequest pushNotificationRequest) {
        try{
            fcmService.sendMessageToToken(pushNotificationRequest);
        }catch (Exception ex){

        }
    }
}

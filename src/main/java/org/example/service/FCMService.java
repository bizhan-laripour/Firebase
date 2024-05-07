package org.example.service;


import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.entity.PushNotificationRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FCMService {

    public void sendMessageToToken(PushNotificationRequest request) throws ExecutionException, InterruptedException {
        Message message = getPreConfiguredMessageToToken(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message);
    }

    private String sendAndGetResponse(Message message) throws ExecutionException, InterruptedException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder().setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder().setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message getPreConfiguredMessageToToken(PushNotificationRequest request) {
        return getPreConfiguredMessageBuilder(request).setToken(request.getToken()).build();
    }

    private Message getPreConfiguredMessageWithoutData(PushNotificationRequest request) {
        return getPreConfiguredMessageBuilder(request).setToken(request.getToken()).build();
    }

    private Message getPreConfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
        return getPreConfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken()).build();
    }

    private Message.Builder getPreConfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        Message.Builder builder = Message.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(Notification.builder().setTitle(request.getTitle()).setBody(request.getMessage()).build());
        return builder;
    }
}

package org.example.controller;

import org.example.entity.PushNotificationRequest;
import org.example.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireBaseController {

    private final PushNotificationService pushNotificationService;

    public FireBaseController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendTokenNotification(@RequestBody PushNotificationRequest pushNotificationRequest){
        pushNotificationService.sendNotification(pushNotificationRequest);
        return new ResponseEntity<>("ok" , HttpStatus.OK);
    }
}

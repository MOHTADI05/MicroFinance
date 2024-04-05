package tn.esprit.mfb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mfb.Services.SmsService;
import tn.esprit.mfb.config.SmsSendRequest;

@RestController
@RequestMapping("/api/sms")
@Slf4j
public class SmsController {
    @Autowired
    SmsService smsService;

    @PostMapping("/processSms")
    public String processSms(@RequestBody SmsSendRequest sendRequest) {
        log.info("processSms started sendRequest " + sendRequest.toString());
        return smsService.sendSms(sendRequest.getDestinationSmsNumber(), sendRequest.getSmsMessages());
    }
}
package tn.esprit.mfb.config;

import lombok.Data;

@Data
public class SmsSendRequest {
    private String destinationSmsNumber;
    private String smsMessages;
}

package tn.esprit.mfb.Services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.mfb.serviceInterface.ISmsService;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class SmsService implements ISmsService {
    @Value("${TWILIO_ACCOUNT_SID}")
    String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    String AUTH_TOKEN;
    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;

//    public SmsService(){
//        log.info("Creating class SmsService");
//        log.info("SID" + ACCOUNT_SID);
//
//    }
    @PostConstruct
    private void setup(){
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);

    }
    @Override
    public String sendSms(String smsNumber, String smsMsg){
        Message message  = Message.creator(
                new PhoneNumber(smsNumber),
                new PhoneNumber(OUTGOING_SMS_NUMBER),
                smsMsg).create();
        return message.getStatus().toString();

    }

}

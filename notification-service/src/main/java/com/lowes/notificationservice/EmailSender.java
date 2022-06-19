package com.lowes.notificationservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSender {

    public void sendEmail(String orderNumber) {
        System.out.println("Order Placed Successfully - Order Number is {}" +orderNumber);
        System.out.println("Email Sent For Order Id {}" + orderNumber);

    }
}
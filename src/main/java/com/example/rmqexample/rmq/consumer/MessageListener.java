package com.example.rmqexample.rmq.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageListener(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = "user-messages")
    public void processMessage(@Payload Message message) {
        // Обработка полученного сообщения и выполнение нужных действий
        // message содержит данные, которые были отправлены через RabbitMQ
        System.out.println(new String(message.getBody()));

        String userIdentity = message.getMessageProperties().getReceivedRoutingKey();
        simpMessagingTemplate.convertAndSend("/topic/messages/" + userIdentity, new String(message.getBody()));

    }
}
package com.example.rmqexample.controller;

import com.example.rmqexample.entity.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    public ChatController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @MessageMapping("/send")
    public void sendMessage(Message message) {
        // Логика обработки сообщения и определения получателя
        Long toUserId = message.getUserToId();
        rabbitTemplate.convertAndSend("messages-exchange", "user." + toUserId, message);
    }


}

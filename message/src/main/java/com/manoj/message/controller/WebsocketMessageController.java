package com.manoj.message.controller;

import com.manoj.message.dto.MessageRequest;
import com.manoj.message.model.Message;
import com.manoj.message.service.MessageService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketMessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebsocketMessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
     }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageRequest messageRequest,
                             @Header("X-User-Username") String senderUsername) {
        Message savedMessage = messageService.save(messageRequest);
        messagingTemplate.convertAndSend("/topic/chat/" + savedMessage.getChatId(), savedMessage);
    }
}

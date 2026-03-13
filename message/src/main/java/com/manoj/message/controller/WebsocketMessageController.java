package com.manoj.message.controller;

import com.manoj.message.dto.MessageRequest;
import com.manoj.message.dto.MessageResponse;
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
    public String sendMessage(@Payload MessageRequest messageRequest,
                            @Header("X-User-Username") String senderUsername){
        if(!senderUsername.equals(messageRequest.getSenderUsername())){
            throw new SecurityException("Sender Username does not match");
        }

        messageRequest.setSenderUsername(senderUsername);
        Message savedMessage = messageService.save(messageRequest);
        MessageResponse response = MessageResponse.builder()
                .senderUsername(savedMessage.getSenderUsername())
                .content(savedMessage.getContent())
                .timestamp(savedMessage.getTimestamp())
                .build();
        messagingTemplate.convertAndSend("/topic/chat/" + savedMessage.getChatId(), response);

        return "Message sent successfully";
    }
}

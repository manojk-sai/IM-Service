package com.manoj.message.controller;

import com.manoj.message.model.Message;
import com.manoj.message.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
     }

    @GetMapping("/{chatId}")
    public List<Message> getMessages(@PathVariable String chatId) {
        return messageService.getChatHistory(chatId);
    }
}

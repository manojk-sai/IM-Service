package com.manoj.chat.controller;

import com.manoj.chat.dto.AddMemberRequest;
import com.manoj.chat.dto.CreateChatRequest;
import com.manoj.chat.model.Chat;
import com.manoj.chat.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Chat createChat(
            @RequestBody CreateChatRequest request,
            @RequestHeader("X-User-Username") String username) {
        return chatService.createChat(request, username);
    }

    @GetMapping
    public List<Chat> getUserChats(
            @RequestHeader("X-User-Username") String username) {

        return chatService.getUserChats(username);
    }

    @GetMapping("/{chatId}")
    public Chat getChatById(@PathVariable String chatId) {
        return chatService.getChatById(chatId);
    }

    @PostMapping("/{chatId}/members")
    public Chat addMember(
            @PathVariable String chatId,
            @RequestBody AddMemberRequest request) {

        return chatService.addMember(chatId, request.getUsername());
    }

}

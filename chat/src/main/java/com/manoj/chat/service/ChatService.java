package com.manoj.chat.service;

import com.manoj.chat.dto.CreateChatRequest;
import com.manoj.chat.model.Chat;
import com.manoj.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(CreateChatRequest request, String createrUsername) {
        List<String> members = new ArrayList<>(request.getMembers());
        if (!members.contains(createrUsername)) {
            members.add(createrUsername);
        }
        Chat chat = Chat.builder()
                .name(request.getName())
                .type(request.getType())
                .members(members)
                .createdBy(createrUsername)
                .createdAt(new Date())
                .build();

        return chatRepository.save(chat);
    }

    public List<Chat> getUserChats(String userId) {

        return chatRepository.findByMembersContaining(userId);
    }

    public Chat addMember(String chatId, String username) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));
        if(chat.getMembers().contains(username)) {
            throw new RuntimeException("User is already a member of the chat");
        }
        chat.getMembers().add(username);
        return chatRepository.save(chat);
    }

}

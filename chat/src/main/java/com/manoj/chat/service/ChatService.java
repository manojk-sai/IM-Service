package com.manoj.chat.service;

import com.manoj.chat.dto.CreateChatRequest;
import com.manoj.chat.model.Chat;
import com.manoj.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(CreateChatRequest request, String creatorId) {

        Chat chat = Chat.builder()
                .name(request.getName())
                .type(request.getType())
                .members(request.getMembers())
                .createdBy(creatorId)
                .createdAt(new Date())
                .build();

        return chatRepository.save(chat);
    }

    public List<Chat> getUserChats(String userId) {

        return chatRepository.findByMembersContaining(userId);
    }

    public Chat addMember(String chatId, String userId) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        chat.getMembers().add(userId);

        return chatRepository.save(chat);
    }

}

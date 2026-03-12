package com.manoj.message.service;

import com.manoj.message.dto.MessageRequest;
import com.manoj.message.model.Message;
import com.manoj.message.repository.MessageRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public MessageService(MessageRepository messageRepository, RedisTemplate<String, Object> redisTemplate) {
        this.messageRepository = messageRepository;
        this.redisTemplate = redisTemplate;
    }

    public Message save(MessageRequest messageRequest) {
        Message message = Message.builder()
                .timestamp(new Date())
                .senderUsername(messageRequest.getSenderUsername())
                .chatId(messageRequest.getChatId())
                .content(messageRequest.getContent())
                .build();
        Message savedMessage = messageRepository.save(message);

        redisTemplate.opsForList().rightPush("Chat: " + savedMessage.getChatId(),savedMessage);

        return savedMessage;
    }

    public List<Message> getChatHistory(String chatId) {
        return messageRepository.findByChatIdOrderByTimestampAsc(chatId);
    }

}

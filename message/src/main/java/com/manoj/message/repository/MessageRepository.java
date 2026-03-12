package com.manoj.message.repository;

import com.manoj.message.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByChatIdOrderByTimestampAsc(String chatId);
}

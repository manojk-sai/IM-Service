package com.manoj.chat.repository;


import com.manoj.chat.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByMembersContaining(String username);
}

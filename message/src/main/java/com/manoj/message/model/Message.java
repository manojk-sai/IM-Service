package com.manoj.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String senderUsername;
    private String chatId;
    private String content;
    private Date timestamp;
}

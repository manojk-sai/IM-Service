package com.manoj.message.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String chatId;
    private String senderUsername;
    private String content;
}

package com.manoj.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageRequest {
    @NotBlank(message = "Chat ID is required")
    private String chatId;
    @NotBlank(message = "Sender username is required")
    private String senderUsername;
    @NotBlank(message = "Message content is required")
    @Size(max = 500, message = "Message content must be less than 500 characters")
    @Size(min = 1, message = "Message content must be at least 1 character")
    private String content;
}

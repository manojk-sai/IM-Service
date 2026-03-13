package com.manoj.message.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageResponse {
    private String senderUsername;
    private String content;
    private Date timestamp;
}

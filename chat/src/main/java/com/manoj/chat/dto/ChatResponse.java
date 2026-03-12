package com.manoj.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ChatResponse {

    private String id;

    private String name;

    private String type;

    private List<String> members;

    private String createdBy;

    private Date createdAt;

}
package com.iaeep.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author DELL
 * @version 1.0
 * @Classname chatMessage
 * @Description TODO
 * @CreateDate 2022/10/17 12:26
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/17 12:26
 */
@Data
public class ChatMessage {
    private Long userId;
    private String content;
    private Date time;
    private String contentType;
    private Long contactsId;
    private Long toUserId;
}

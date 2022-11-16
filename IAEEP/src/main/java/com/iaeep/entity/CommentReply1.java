package com.iaeep.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author DELL
 * @version 1.0
 * @Classname CommentReply1
 * @Description TODO
 * @CreateDate 2022/10/2 9:01
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:01
 */
@Data
public class CommentReply1 {
    private Long id;
    private Date replyTime;
    private String replyContent;
    private Integer replyLikeNum;
    private Long sendReplyUserId;
    private Long receiveReplyUserId;
    private Long commentId;
}

package com.iaeep.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author DELL
 * @version 1.0
 * @Classname CommentReply2
 * @Description TODO
 * @CreateDate 2022/10/2 9:01
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:01
 */
@Data
public class CommentReply2 {
    private Integer id;
    private Date replyTime;
    private String replyContent;
    private Integer replyLikeNum;
    private Integer sendReplyUserId;
    private Integer receiveReplyUserId;
    private Integer replyId;
}

package com.iaeep.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DELL
 * @version 1.0
 * @Classname CommentInformation
 * @Description TODO
 * @CreateDate 2022/10/2 9:00
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:00
 */
@Data
public class CommentInformation extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    //贴子id
    private Long postId;

    //评论人id
    private Long sendUserId;

    //被评论人id
    private Long receiveUserId;

    //评论内容
    private String commentContent;

    //点赞数
    private Integer commentLikeNum;

    //0表示评论 1表示回复
    private Integer status;

    //评论时间
    private Date time;

    //当为回复时表示该回复属于哪条评论下的，当为评论是为null;
    private Long rootId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

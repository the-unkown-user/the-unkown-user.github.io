package com.iaeep.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DELL
 * @version 1.0
 * @Classname ChatRecord
 * @Description TODO
 * @CreateDate 2022/10/2 8:59
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 8:59
 */
@Data
public class ChatRecord extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private String contentType;
    private String content;
    @JSONField(format = "yyyy/MM/dd E HH:mm:ss")
    private Date time;
    private Integer isRead;//已读标记
    private Long fromUserId;
    private Long toUserId;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

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
 * @Classname Posts
 * @Description TODO
 * @CreateDate 2022/10/2 9:04
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:04
 */
@Data
public class Posts extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private String postTitle;
    private String postContent;
    private String postKind; //不启用，保留属性
    private Integer likeNum;
    private Long userId;
    private Date time;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

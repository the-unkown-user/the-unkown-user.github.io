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
 * @Classname PostCollection
 * @Description TODO
 * @CreateDate 2022/10/2 9:04
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:04
 */
@Data
public class PostCollection extends Model {
    private Long userId;
    private Long postId;
    private Date time;
    private String date;
    private String postTitle;
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

package com.iaeep.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @author DELL
 * @version 1.0
 * @Classname HotPosts
 * @Description TODO
 * @CreateDate 2022/10/2 9:03
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:03
 */
@Data
public class HotPosts extends Model {
    @TableId(value = "post_id",type = IdType.INPUT)
    private Long postId;
    @Override
    protected Serializable pkVal() {
        return this.postId;
    }
}

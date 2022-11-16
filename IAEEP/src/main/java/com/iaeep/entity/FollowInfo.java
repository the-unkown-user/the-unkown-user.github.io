package com.iaeep.entity;

/**
 * @version 1.0
 * @Classname FollowInfo
 * @Description TODO
 * @CreateDate 2022/10/15 20:28
 * @Created by DELL
 * @Update DELL
 * @author DELL
 * @UpdateDate 2022/10/15 20:28
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class FollowInfo extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Long userId1;
    private Long userId2;
    private String name1;
    private String name2;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

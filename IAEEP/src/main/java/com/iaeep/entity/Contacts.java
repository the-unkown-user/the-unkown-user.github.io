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
 * @Classname Contacts
 * @Description TODO
 * @CreateDate 2022/10/2 9:01
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:01
 */
@Data
public class Contacts extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private String  name;
    //是否有未读信息 1代表有未读， 0代表没有未读信息
    private Integer unRead;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

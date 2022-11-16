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
 * @Classname LogInfo
 * @Description TODO
 * @CreateDate 2022/10/2 9:03
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:03
 */
@Data
public class LogInfo extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Date time;
    private String logger;
    private String level;
    private String message;
    private String method;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

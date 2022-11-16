package com.iaeep.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @author DELL
 * @version 1.0
 * @Classname TeamInformation
 * @Description TODO
 * @CreateDate 2022/10/2 9:05
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:05
 */
@Data
public class TeamInformation extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Long projectId;
    private Long userId;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

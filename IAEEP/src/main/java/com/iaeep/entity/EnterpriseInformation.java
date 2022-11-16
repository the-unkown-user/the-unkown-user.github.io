package com.iaeep.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @author DELL
 * @version 1.0
 * @Classname EnterpriseInformation
 * @Description TODO
 * @CreateDate 2022/10/2 9:02
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:02
 */
@Data
public class EnterpriseInformation extends Model {
    @TableId(value = "user_id",type = IdType.INPUT)
    private Long userId;
    private String name;
    private String address;
    private String introduction;
    private String phone;
    private String email;
    private String avatar;
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}

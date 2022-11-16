package com.iaeep.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @author DELL
 * @version 1.0
 * @Classname PersonalInformation
 * @Description TODO
 * @CreateDate 2022/10/2 9:03
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:03
 */
@Data
public class PersonalInformation extends Model {
    private String name;
    @TableId(value = "user_id",type = IdType.INPUT)
    private Long userId;
    private String phone;
    private String email;
    private Integer age;
    private String sex;
    private String graduateSchool;
    private String avatar;
    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}

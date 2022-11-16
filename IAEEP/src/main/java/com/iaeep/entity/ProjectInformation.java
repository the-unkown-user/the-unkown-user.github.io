package com.iaeep.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author DELL
 * @version 1.0
 * @Classname ProjectInformation
 * @Description TODO
 * @CreateDate 2022/10/2 9:04
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:04
 */
@Data
public class ProjectInformation extends Model {
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Long chargeUserId;
    private String phone;
    private String email;
    private String projectIntroduce;
    private Integer projectPersonNum;
    private String projectStatus;
    private String projectType;
    private String projectLabel;
    private String projectName;
    private String chargeUsername;

    @TableField(fill = FieldFill.INSERT)
    private Date time;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

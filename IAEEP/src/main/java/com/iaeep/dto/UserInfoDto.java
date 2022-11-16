package com.iaeep.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author DELL
 * @version 1.0
 * @Classname UserInfoDto
 * @Description TODO
 * @CreateDate 2022/10/30 21:11
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/30 21:11
 */
@Data
public class UserInfoDto {
    private String name;
    private Long userId;
    private String phone;
    private String email;
    private Integer age;
    private String sex;
    private String graduateSchool;
    private String avatar;
    private String address;
    private String introduction;
    private Integer follow;
    private Integer like;
    private Integer collection;
    private Integer posts;
}

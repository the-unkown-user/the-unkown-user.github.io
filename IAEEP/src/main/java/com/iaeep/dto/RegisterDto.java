package com.iaeep.dto;

import lombok.Data;

/**
 * @author DELL
 * @version 1.0
 * @Classname registerDto
 * @Description TODO
 * @CreateDate 2022/10/27 8:33
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/27 8:33
 */
@Data
public class RegisterDto {
    private String username;
    private String password;
    private String phone;
    private String userType;
    private String code;
}

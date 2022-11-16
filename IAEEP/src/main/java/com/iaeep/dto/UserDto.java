package com.iaeep.dto;

import com.iaeep.entity.EnterpriseInformation;
import com.iaeep.entity.PersonalInformation;
import com.iaeep.entity.User;
import lombok.Data;

/**
 * @author DELL
 * @version 1.0
 * @Classname UserDto
 * @Description TODO
 * @CreateDate 2022/10/7 22:00
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/7 22:00
 */
@Data
public class UserDto extends User {
    private PersonalInformation personalInformation;
    private EnterpriseInformation enterpriseInformation;
    private Boolean unReadeInfo;
    private String account;
}

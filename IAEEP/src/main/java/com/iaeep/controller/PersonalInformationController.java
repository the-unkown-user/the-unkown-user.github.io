package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iaeep.common.R;
import com.iaeep.entity.PersonalInformation;
import com.iaeep.service.PersonalInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DELL
 * @version 1.0
 * @Classname PersonalInformationController
 * @Description TODO
 * @CreateDate 2022/10/2 9:46
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:46
 */
@RestController
@Slf4j
@RequestMapping("/personal")
public class PersonalInformationController {
    @Autowired
    private PersonalInformationService personalInformationService;

    /**
     *
     * @param: personalInformation
     * @return : {@link R< String>}
     * @author : liujiahui
     * @description: 〈保存用户信息〉
     * @date : 2022/10/27 17:45
     */
    @PostMapping("/savePersonalInfo")
    public R<String> savePersonalInfo(@RequestBody  PersonalInformation personalInformation) {
        if(personalInformationService.updateById(personalInformation)){
            return R.success("用户信息保存成功");
        }else{
            return R.error("用户信息保存失败");
        }
    }

}

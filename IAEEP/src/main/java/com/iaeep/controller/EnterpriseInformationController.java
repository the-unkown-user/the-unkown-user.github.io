package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iaeep.common.R;
import com.iaeep.entity.EnterpriseInformation;
import com.iaeep.service.EnterpriseInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author DELL
 * @version 1.0
 * @Classname EnterpriseInformationController
 * @Description TODO
 * @CreateDate 2022/10/2 9:46
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:46
 */
@RestController
@Slf4j
@RequestMapping("/enterprise")
public class EnterpriseInformationController {
    @Autowired
    private EnterpriseInformationService enterpriseInformationService;

    /**
     *
     * @param: enterpriseInformation
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈保存用户信息〉
     * @date : 2022/10/27 17:45
     */
    @PostMapping("/saveEnterpriseInfo")
    public R<String> saveEnterpriseInfo(@RequestBody EnterpriseInformation enterpriseInformation) {
        if(enterpriseInformationService.updateById(enterpriseInformation)){
            return R.success("用户信息修改成功");
        }else {
            return R.error("用户信息保存失败");
        }
    }
}

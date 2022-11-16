package com.iaeep.controller;

import com.iaeep.common.R;
import com.iaeep.service.LogInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DELL
 * @version 1.0
 * @Classname LogInfoController
 * @Description TODO
 * @CreateDate 2022/10/2 9:46
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:46
 */
@RestController
@Slf4j
@RequestMapping("/logInfo")
public class LogInfoController {
    @Autowired
    private LogInfoService logInfoService;

}

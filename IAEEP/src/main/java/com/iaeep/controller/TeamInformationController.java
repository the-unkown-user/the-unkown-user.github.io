package com.iaeep.controller;

import com.iaeep.service.TeamInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DELL
 * @version 1.0
 * @Classname TeamInformationController
 * @Description TODO
 * @CreateDate 2022/10/2 9:47
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:47
 */
@RestController
@Slf4j
@RequestMapping("/team")
public class TeamInformationController {
    @Autowired
    private TeamInformationService teamInformationService;

}

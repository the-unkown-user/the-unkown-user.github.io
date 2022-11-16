package com.iaeep.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DELL
 * @version 1.0
 * @Classname WelcomePage
 * @Description TODO
 * @CreateDate 2022/10/17 20:13
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/17 20:13
 */
@RestController
@RequestMapping(value = "/iaeep")
public class WelcomePage {
    @RequestMapping("/")
    public String WelcomePage(){
        return "forward:page/index.html";
    }
}

package com.iaeep.controller;

import com.iaeep.service.CommentReply1Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DELL
 * @version 1.0
 * @Classname CommentReply1Controller
 * @Description TODO
 * @CreateDate 2022/10/2 9:45
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:45
 */
@RestController
@Slf4j
@RequestMapping("/commentReply1")
public class CommentReply1Controller {
    @Autowired
    private CommentReply1Service commentReply1Service;
}

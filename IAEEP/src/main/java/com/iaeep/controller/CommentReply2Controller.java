package com.iaeep.controller;

import com.iaeep.service.CommentReply2Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DELL
 * @version 1.0
 * @Classname CommentReply2Controller
 * @Description TODO
 * @CreateDate 2022/10/2 9:45
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:45
 */
@RestController
@Slf4j
@RequestMapping("/commentReply2")
public class CommentReply2Controller {
    @Autowired
    private CommentReply2Service commentReply2Service;

}

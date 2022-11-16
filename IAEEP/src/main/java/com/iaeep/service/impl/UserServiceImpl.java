package com.iaeep.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaeep.entity.User;
import com.iaeep.mapper.UserMapper;
import com.iaeep.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @version 1.0
 * @Classname UserService
 * @Description TODO
 * @CreateDate 2022/10/2 9:24
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}

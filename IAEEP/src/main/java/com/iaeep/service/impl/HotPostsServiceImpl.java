package com.iaeep.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaeep.entity.HotPosts;
import com.iaeep.mapper.HotPostsMapper;
import com.iaeep.service.HotPostsService;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @version 1.0
 * @Classname HotPostsService
 * @Description TODO
 * @CreateDate 2022/10/2 9:20
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:20
 */
@Service
public class HotPostsServiceImpl extends ServiceImpl<HotPostsMapper,HotPosts> implements HotPostsService {
}

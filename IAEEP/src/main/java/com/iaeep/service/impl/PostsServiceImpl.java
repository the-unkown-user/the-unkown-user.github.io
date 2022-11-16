package com.iaeep.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaeep.entity.Posts;
import com.iaeep.mapper.PostsMapper;
import com.iaeep.service.PostsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DELL
 * @version 1.0
 * @Classname PostsService
 * @Description TODO
 * @CreateDate 2022/10/2 9:22
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:22
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper,Posts> implements PostsService {
    @Override
    public List<Integer> postsIds (Integer num){
        return this.baseMapper.getPostIdsByNum(num);
    }

    @Override
    public Integer getLikeNum(Long userId) {
        return this.baseMapper.getLikeNum(userId);
    }
}

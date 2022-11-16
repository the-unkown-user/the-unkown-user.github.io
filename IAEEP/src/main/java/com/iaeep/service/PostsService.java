package com.iaeep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iaeep.entity.Posts;

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
public interface PostsService extends IService<Posts> {
    public List<Integer> postsIds (Integer num);

    Integer getLikeNum(Long userId);
}

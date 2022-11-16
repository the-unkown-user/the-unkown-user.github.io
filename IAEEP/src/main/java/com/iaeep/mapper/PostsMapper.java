package com.iaeep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaeep.entity.Posts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author DELL
 * @version 1.0
 * @Classname PostsMapper
 * @Description TODO
 * @CreateDate 2022/10/2 9:14
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:14
 */
@Mapper
public interface PostsMapper extends BaseMapper<Posts> {

    /**
     *
     * @param: num
     * @return : {@link List<Integer>}
     * @author : liujiahui
     * @description: 〈查找收藏数最多的贴子编号集合〉
     * @date : 2022/10/29 15:39
     */
    @Select("select post_id from (select post_id,count(*) cnt from post_collection group by post_id) as temp ORDER BY cnt desc limit #{num}")
    public List<Integer> getPostIdsByNum(@Param("num") Integer num);

    @Select("SELECT sum(like_num) as likeNum FROM posts where user_id = #{userId}")
    public Integer getLikeNum(@Param("userId") Long userId);

}

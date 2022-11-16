package com.iaeep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iaeep.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author DELL
 * @version 1.0
 * @Classname UserMapper
 * @Description TODO
 * @CreateDate 2022/10/2 9:15
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

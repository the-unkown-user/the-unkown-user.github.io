package com.iaeep.dto;

import com.iaeep.entity.Posts;
import lombok.Data;

/**
 * @author DELL
 * @version 1.0
 * @Classname PostDto
 * @Description TODO
 * @CreateDate 2022/10/21 22:08
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/21 22:08
 */
@Data
public class PostDto extends Posts {
    private String username;
    private String date;
    private Integer collectionNum; //收藏数
    private Integer commentNum; //评论数
}

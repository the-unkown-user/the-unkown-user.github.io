package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iaeep.common.R;
import com.iaeep.dto.PostDto;
import com.iaeep.entity.PostCollection;
import com.iaeep.entity.User;
import com.iaeep.service.PostCollectionService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author DELL
 * @version 1.0
 * @Classname PostCollectionController
 * @Description TODO
 * @CreateDate 2022/10/2 9:46
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:46
 */
@RestController
@Slf4j
@RequestMapping("/collection")
public class PostCollectionController {

    @Autowired
    private PostCollectionService postCollectionService;


    @GetMapping("/cancelCollection/{id}")
    public R<String> cancelCollection(@PathVariable String id) {
        postCollectionService.removeById(id);
        return R.success("取消收藏成功");
    }

    /**
     *
     * @param: postDto
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈贴子收藏〉
     * @date : 2022/10/29 18:01
     */
    @PostMapping("/updateCollectionInfo")
    public R<String> addCollectionInfo(@RequestBody PostDto postDto,String type, HttpServletRequest request) {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        if (type != null && type.equals("1")){
            LambdaQueryWrapper<PostCollection> query = new LambdaQueryWrapper<>();
            query.eq(PostCollection::getPostId,postDto.getId());
            query.eq(PostCollection::getUserId,((User)request.getSession().getAttribute("user")).getId());
            postCollectionService.remove(query);
            return R.success("已取消收藏");
        }
        PostCollection postCollection = new PostCollection();
        postCollection.setPostId(postDto.getId());
        postCollection.setTime(new Date());
        postCollection.setDate(format2.format(postCollection.getTime()));
        postCollection.setUserId(((User)request.getSession().getAttribute("user")).getId());
        postCollection.setPostTitle(postDto.getPostTitle());
        postCollectionService.save(postCollection);
        return R.success("已收藏");
    }


    @SneakyThrows
    @GetMapping("/getCollection/{userId}")
    public R<List<PostCollection>> getCollection(@PathVariable("userId") String userId) {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        List<PostCollection> postCollection = new ArrayList<>();
        LambdaQueryWrapper<PostCollection> query = new LambdaQueryWrapper<>();
        query.eq(PostCollection::getUserId, userId);
        postCollection = postCollectionService.list(query);
        return R.success(postCollection);
    }

    @PostMapping("/isCollection")
    public R<Boolean> isCollection(@RequestBody HashMap<String, Object> data){
        Long userId = Long.parseLong((String) data.get("userId"));
        LambdaQueryWrapper<PostCollection> query = new LambdaQueryWrapper<>();
        query.eq(PostCollection::getUserId, userId);
        query.eq(PostCollection::getPostId, data.get("postId"));
        int cnt = postCollectionService.count(query);
        if (cnt > 0) {
            return R.success(true);
        }else {
            return R.success(false);
        }
    }

}

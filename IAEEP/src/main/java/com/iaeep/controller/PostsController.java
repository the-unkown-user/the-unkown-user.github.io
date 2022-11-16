package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.iaeep.common.R;
import com.iaeep.dto.PostDto;
import com.iaeep.entity.*;
import com.iaeep.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author DELL
 * @version 1.0
 * @Classname PostsController
 * @Description TODO
 * @CreateDate 2022/10/2 9:47
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:47
 */
@RestController
@Slf4j
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;
    @Autowired
    private PersonalInformationService personalInformationService;
    @Autowired
    private CommentInformationService commentInformationService;
    @Autowired
    private PostCollectionService   postCollectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseInformationService enterpriseInformationService;

    @RequestMapping("/save")
    public R<String> addArticle(Posts posts) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        //补全posts属性
        posts.setLikeNum(0);
        posts.setTime(new Date());
        posts.setUserId(((User) session.getAttribute("user")).getId());
        postsService.save(posts);
        return R.success("添加成功",null);
    }

    @GetMapping("/getPost/{id}")
    public R<PostDto> getPost(@PathVariable("id") Integer id){
        Posts post = postsService.getById(id);
        PostDto postDto = new PostDto();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        postDto.setDate(sdf.format(post.getTime()));
        BeanUtils.copyProperties(post,postDto);

        User user = userService.getById(post.getUserId());
        if (user.getUserType().equals("个人")){
            LambdaQueryWrapper<PersonalInformation> query = new LambdaQueryWrapper<>();
            query.eq(PersonalInformation::getUserId,post.getUserId());
            PersonalInformation personalInformation = personalInformationService.getOne(query);
            postDto.setUsername(personalInformation.getName());
        }else {
            LambdaQueryWrapper<EnterpriseInformation> query = new LambdaQueryWrapper<>();
            query.eq(EnterpriseInformation::getUserId,post.getUserId());
            EnterpriseInformation enterpriseInformation = enterpriseInformationService.getOne(query);
            postDto.setUsername(enterpriseInformation.getName());
        }

        //获取评论数
        LambdaQueryWrapper<CommentInformation> queryComment = new LambdaQueryWrapper<>();
        queryComment.eq(CommentInformation::getPostId,post.getId());
        postDto.setCommentNum(commentInformationService.count(queryComment));

        //获取收藏数
        LambdaQueryWrapper<PostCollection> queryPostCollection = new LambdaQueryWrapper<>();
        queryPostCollection.eq(PostCollection::getPostId,post.getId());
        postDto.setCollectionNum(postCollectionService.count(queryPostCollection));

        return R.success(postDto);
    }

    /**
     *
     * @param: input
     * @return : {@link R< List< Posts>>}
     * @author : liujiahui
     * @description: 〈按输入查询贴子〉
     * @date : 2022/10/29 11:03
     */
    @PostMapping("/getSearchPostList")
    public R<List<Posts>> getSearchPostList(@RequestBody HashMap<String,Object> data) {
        String input = String.valueOf(data.get("input"));
        QueryWrapper<Posts> query = new QueryWrapper<>();
        query.like(StringUtils.isNotEmpty(input),"post_title",input);
        return R.success(postsService.list(query));
    }

    @GetMapping("/getPostList/{id}")
    public R<List<PostDto>> getPostList(@PathVariable Integer id) {
        List<PostDto> postDtoList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Posts> postsList = new ArrayList<>();
        if(id ==1){
            //最新
            LambdaQueryWrapper<Posts> query = new LambdaQueryWrapper<>();
            //降序排序
            query.orderByDesc(Posts::getTime);
            query.last("limit 10");
            postsList = postsService.list(query);
        }else if(id == 2){
            //最热，点赞
            LambdaQueryWrapper<Posts> query = new LambdaQueryWrapper<>();
            query.orderByDesc(Posts::getLikeNum);
            query.last("limit 10");
           postsList = postsService.list(query);
        }else {
            //推荐，收藏
            List<Integer> postIds = postsService.postsIds(10);
            for (Integer ids : postIds) {
                postsList.add(postsService.getById(ids));
            }
        }
        for (Posts post : postsList) {
            PostDto postDto = new PostDto();
            //格式化时间
            postDto.setDate(sdf.format(post.getTime()));
            //获取作者
            BeanUtils.copyProperties(post,postDto);

            User user = userService.getById(post.getUserId());
            if (user.getUserType().equals("个人")){
                LambdaQueryWrapper<PersonalInformation> query = new LambdaQueryWrapper<>();
                query.eq(PersonalInformation::getUserId,post.getUserId());
                PersonalInformation personalInformation = personalInformationService.getOne(query);
                postDto.setUsername(personalInformation.getName());
            }else {
                LambdaQueryWrapper<EnterpriseInformation> query = new LambdaQueryWrapper<>();
                query.eq(EnterpriseInformation::getUserId,post.getUserId());
                EnterpriseInformation enterpriseInformation = enterpriseInformationService.getOne(query);
                postDto.setUsername(enterpriseInformation.getName());
            }
            //添加到dto
            postDtoList.add(postDto);
        }
        return R.success(postDtoList);
    }

    /**
     *
     * @param: postDto
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈贴子点赞〉
     * @date : 2022/10/29 18:01
     */
    @PostMapping("/updateLikeNum")
    public R<String> updateLikeNum(@RequestBody PostDto postDto) {
        Posts posts =new Posts();
        BeanUtils.copyProperties(postDto,posts);
        postsService.updateById(posts);
        return R.success("点赞成功");
    }
}

package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iaeep.common.R;
import com.iaeep.dto.CommentDto;
import com.iaeep.dto.PostDto;
import com.iaeep.entity.CommentInformation;
import com.iaeep.entity.EnterpriseInformation;
import com.iaeep.entity.PersonalInformation;
import com.iaeep.entity.User;
import com.iaeep.service.CommentInformationService;
import com.iaeep.service.EnterpriseInformationService;
import com.iaeep.service.PersonalInformationService;
import com.iaeep.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author DELL
 * @version 1.0
 * @Classname CommentInformationController
 * @Description TODO
 * @CreateDate 2022/10/2 9:45
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:45
 */
@RestController
@Slf4j
@RequestMapping("/commentInformation")
public class CommentInformationController {
    @Autowired
    private CommentInformationService commentInformationService;
    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseInformationService enterpriseInformationService;
    @Autowired
    private PersonalInformationService personalInformationService;

    /**
     *
     * @param: commentDto
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈评论或回复点赞〉
     * @date : 2022/10/29 16:25
     */
    @PostMapping("/like")
    public R<String> addLikeNum(@RequestBody CommentDto commentDto){
        CommentInformation commentInformation = new CommentInformation();
        BeanUtils.copyProperties(commentDto, commentInformation);
        commentInformationService.updateById(commentInformation);
        return R.success("点赞成功");
    }

    /**
     *
     * @param: postDto
     * @return : {@link R< List< CommentDto>>}
     * @author : liujiahui
     * @description: 〈查询评论〉
     * @date : 2022/10/29 17:43
     */
    @PostMapping("/get")
    public R<List<CommentDto>> getCommentList(@RequestBody PostDto postDto){
        String pat = "yyyy-MM-dd";
        SimpleDateFormat format2 = new SimpleDateFormat(pat);
        List<CommentDto> comments = new ArrayList<CommentDto>();
        LambdaQueryWrapper<CommentInformation> query = new LambdaQueryWrapper<>();
        query.eq(CommentInformation::getPostId,postDto.getId());
        //0表示评论，1 表示回复
        query.eq(CommentInformation::getStatus,0);
        //查询评论
        List<CommentInformation> commentInformationList = commentInformationService.list(query);

        //查询回复,并组装评论和回复
        for(int i=0;i<commentInformationList.size();i++) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(commentInformationList.get(i), commentDto);
            commentDto.setDate(format2.format(commentInformationList.get(i).getTime()));
            //查询头像，用户名
            //评论发送者：
            User user = userService.getById(commentInformationList.get(i).getSendUserId());
            if (user.getUserType().equals("个人")){
                PersonalInformation personalInformation = personalInformationService.getById(commentInformationList.get(i).getSendUserId());
                commentDto.setAvatar("../"+personalInformation.getAvatar());
                commentDto.setUsername(personalInformation.getName());
            }else {
                EnterpriseInformation enterpriseInformation = enterpriseInformationService.getById(commentInformationList.get(i).getSendUserId());
                commentDto.setAvatar("../"+enterpriseInformation.getAvatar());
                commentDto.setUsername(enterpriseInformation.getName());
            }
            //查询回复
            LambdaQueryWrapper<CommentInformation> query1 = new LambdaQueryWrapper<>();
            query1.eq(CommentInformation::getRootId,commentInformationList.get(i).getId());
            query1.eq(CommentInformation::getStatus,1);
            List<CommentInformation> replies = commentInformationService.list(query1);
            List<CommentDto> comments1 = new ArrayList<CommentDto>();

            //查询回复的用户头像，用户名
            for (int j = 0; j < replies.size(); j++) {
                CommentDto commentDto1 = new CommentDto();
                BeanUtils.copyProperties(replies.get(j),commentDto1);
                //回复发送者：
                User user1 = userService.getById(commentDto1.getSendUserId());
                if (user1.getUserType().equals("个人")){
                    PersonalInformation personalInformation = personalInformationService.getById(commentDto1.getSendUserId());
                    commentDto1.setAvatar("../"+personalInformation.getAvatar());
                    commentDto1.setUsername(personalInformation.getName());
                }else {
                    EnterpriseInformation enterpriseInformation = enterpriseInformationService.getById(commentDto1.getSendUserId());
                    commentDto1.setAvatar("../"+enterpriseInformation.getAvatar());
                    commentDto1.setUsername(enterpriseInformation.getName());
                }

                //回复接收者名称:
                User user2 = userService.getById(commentDto1.getReceiveUserId());
                if (user2.getUserType().equals("个人")){
                    PersonalInformation personalInformation = personalInformationService.getById(commentDto1.getReceiveUserId());
                    commentDto1.setToUsername(personalInformation.getName());
                }else {
                    EnterpriseInformation enterpriseInformation = enterpriseInformationService.getById(commentDto1.getReceiveUserId());
                    commentDto1.setToUsername(enterpriseInformation.getName());
                }

                comments1.add(commentDto1);
            }

            //组装评论和回复
            if (comments1.size() > 0){
                commentDto.setCommentList1(comments1);
            }else {
                commentDto.setCommentList1(null);
            }
            comments.add(commentDto);
        }
        return R.success(comments);
    }


    @SneakyThrows
    @PostMapping("/send")
    public R<CommentDto> addCommend(@RequestBody CommentDto commentDto_,HttpServletRequest request){
        CommentDto commentDto = new CommentDto();
        //删除文本域中的回复锁定
        if (commentDto_.getStatus() == 1){
            String content = "";
            int indexHead = commentDto_.getCommentContent().indexOf('@');
            int indexEnd = commentDto_.getCommentContent().indexOf(':',indexHead);
            if(indexHead >=1){
                content = commentDto_.getCommentContent().substring(0,indexHead-1);
            }
            if(indexEnd <commentDto_.getCommentContent().length()-1){
                content += commentDto_.getCommentContent().substring(indexEnd+1);
            }
            commentDto_.setCommentContent(content);
            System.out.println("..........................................."+content+"...................................");
        }
        //保存评论、回复
        CommentInformation commentInformation = new CommentInformation();
        BeanUtils.copyProperties(commentDto_,commentInformation);
        commentInformation.setSendUserId(((User)request.getSession().getAttribute("user")).getId());
        commentInformation.setTime(new Date());
        commentInformationService.save(commentInformation);
        //查询评论、回复
        String pat = "yyyy-MM-dd";
        SimpleDateFormat format2 = new SimpleDateFormat(pat);
        String date = format2.format(commentInformation.getTime());
        LambdaQueryWrapper<CommentInformation> query = new LambdaQueryWrapper<>();
        query.eq(CommentInformation::getTime,format2.parse(date));
        query.eq(CommentInformation::getCommentContent,commentInformation.getCommentContent());
        CommentInformation comment = new CommentInformation();
        comment = commentInformationService.getOne(query);
        if (comment != null) {
            BeanUtils.copyProperties(comment,commentDto);
        }
        commentDto.setAvatar("../"+commentDto_.getAvatar());
        commentDto.setUsername(commentDto_.getUsername());
        commentDto.setToUsername(commentDto_.getToUsername());
        commentDto.setDate(format2.format(commentDto.getTime()));
        //返回结果
        return R.success(commentDto);
    }

}

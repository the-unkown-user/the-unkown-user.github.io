package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iaeep.common.R;
import com.iaeep.dto.FollowDto;
import com.iaeep.entity.EnterpriseInformation;
import com.iaeep.entity.FollowInfo;
import com.iaeep.entity.PersonalInformation;
import com.iaeep.entity.User;
import com.iaeep.service.EnterpriseInformationService;
import com.iaeep.service.FollowInfoService;
import com.iaeep.service.PersonalInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DELL
 * @version 1.0
 * @Classname FollowInfoController
 * @Description TODO
 * @CreateDate 2022/10/15 20:34
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/15 20:34
 */
@RestController
@RequestMapping("/follow")
public class FollowInfoController {
    @Autowired
    private FollowInfoService followInfoService;
    @Autowired
    private PersonalInformationService personalInformationService;
    @Autowired
    private EnterpriseInformationService enterpriseInformationService;

    /**
     *
     * @param: userId
     * @param: type
     * @return : {@link R<List<FollowInfo>>}
     * @author : liujiahui
     * @description: 〈查询关注信息〉
     * @date : 2022/10/29 10:04
     */
    @PostMapping("/getFollowList")
    public R<List<FollowInfo>> getFollowList(@RequestBody FollowDto followDto) {
        Long userId = followDto.getUserId();
        String type = followDto.getType();
        List<FollowInfo> list = new ArrayList<>();
        String check = "关注我的";
        //查找关注集合
        if(type.equals(check)){
            LambdaQueryWrapper<FollowInfo> query = new LambdaQueryWrapper<>();
            query.eq(FollowInfo::getUserId2,userId);
            list = followInfoService.list(query);
        }else {
            LambdaQueryWrapper<FollowInfo> query = new LambdaQueryWrapper<>();
            query.eq(FollowInfo::getUserId1,userId);
            list = followInfoService.list(query);
        }
        return R.success(list);
    }


    @GetMapping("/cancel")
    public R<String> cancelFollow(@PathVariable("id") Integer id) {
        followInfoService.removeById(id);
        return R.success("已取消关注");
    }

    @GetMapping("/addUser/{username}")
    public R<String> addUser(@PathVariable("username") String username, HttpServletRequest request){
        FollowInfo info = new FollowInfo();
        //完善信息
        info.setUserId1(((User)request.getSession().getAttribute("user")).getId());
        //个人中查询
        LambdaQueryWrapper<PersonalInformation> query = new LambdaQueryWrapper<>();
        query.eq(PersonalInformation::getName,username);
        PersonalInformation personalInformation = personalInformationService.getOne(query);
        if(personalInformation !=null ){
            info.setUserId2(personalInformation.getUserId());
            info.setName2(personalInformation.getName());
            followInfoService.save(info);
            return R.success("关注成功");
        }
        //企业中查询
        LambdaQueryWrapper<EnterpriseInformation> query1 = new LambdaQueryWrapper<>();
        query1.eq(EnterpriseInformation::getName,username);
        EnterpriseInformation enterpriseInformation = enterpriseInformationService.getOne(query1);
        info.setUserId2(enterpriseInformation.getUserId());
        info.setName2(enterpriseInformation.getName());
        followInfoService.save(info);
        return R.success("关注成功");
    }

}

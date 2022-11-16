package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iaeep.common.R;
import com.iaeep.entity.ProjectInformation;
import com.iaeep.entity.User;
import com.iaeep.service.ProjectInformationService;
import com.sun.javafx.collections.MappingChange;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DELL
 * @version 1.0
 * @Classname ProjectInformationController
 * @Description TODO
 * @CreateDate 2022/10/2 9:47
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:47
 */
@RestController
@Slf4j
@RequestMapping("/project")
public class ProjectInformationController {
    @Autowired
    private ProjectInformationService projectInformationService;


    /**
     *
     * @param: projectInformation
     * @return : {@link R<ProjectInformation>}
     * @author : liujiahui
     * @description: 〈添加项目〉
     * @date : 2022/10/29 10:40
     */
    @PostMapping("/addProjectInfo")
    public R<ProjectInformation> addProjectInfo(@RequestBody ProjectInformation projectInformation, HttpServletRequest request){
        ProjectInformation projectInformation1 =new ProjectInformation();
        projectInformation.setChargeUserId(((User) request.getSession().getAttribute("user")).getId());
        projectInformationService.save(projectInformation);
        LambdaQueryWrapper<ProjectInformation> query = new LambdaQueryWrapper<>();
        query.eq(ProjectInformation::getProjectIntroduce,projectInformation.getProjectIntroduce());
        query.eq(ProjectInformation::getProjectName,projectInformation.getProjectName());
        projectInformation1= projectInformationService.getOne(query);
        return R.success(projectInformation1);
    }


    /**
     *
     * @param: projectInformation
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈修改项目信息〉
     * @date : 2022/10/29 10:46
     */
    @PostMapping("/updateProjectInfo")
    public R<String> updateProjectInfo(@RequestBody ProjectInformation projectInformation){
        projectInformationService.updateById(projectInformation);
        return R.success("项目信息修改成功");
    }

    /**
     *
     * @param: input
     * @return : {@link R<List<ProjectInformation>>}
     * @author : liujiahui
     * @description: 〈根据输入条件查询项目信息〉
     * @date : 2022/10/29 10:54
     */
    @PostMapping("/getSearchProjectList")
    public R<List<ProjectInformation>> getSearchProjectList(@RequestBody HashMap<String,Object> data){
        String input = String.valueOf(data.get("input"));
        System.out.println("input: "+input);
        List<ProjectInformation> results = new ArrayList<>();
        QueryWrapper<ProjectInformation> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(input),"project_name",input);
        results = projectInformationService.list(queryWrapper);
        return R.success(results);
    }


    @GetMapping("/list/{id}")
    public R<List<ProjectInformation>> list(@PathVariable("id") Integer id) {
        List<ProjectInformation> results = new ArrayList<>();
        if(id == 1){
            //all
            return R.success(projectInformationService.list());
        }else {
            //last
            LambdaQueryWrapper<ProjectInformation> query = new LambdaQueryWrapper<>();
            query.orderByDesc(ProjectInformation::getTime);
            query.last("limit 10");
            return R.success(projectInformationService.list(query));
        }
    }

    /**
     *
     * @param: userId
     * @return : {@link R<List<ProjectInformation>>}
     * @author : liujiahui
     * @description: 〈当前用户的项目〉
     * @date : 2022/10/30 19:30
     */
    @GetMapping("/getProject/{userId}")
    public R<List<ProjectInformation>> getProject(@PathVariable("userId") String userId) {
        List<ProjectInformation> results = new ArrayList<>();
        LambdaQueryWrapper<ProjectInformation> query = new LambdaQueryWrapper<>();
        query.eq(ProjectInformation::getChargeUserId, userId);
        results = projectInformationService.list(query);
        return R.success(results);
    }
}

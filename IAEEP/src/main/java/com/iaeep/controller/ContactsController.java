package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iaeep.common.R;
import com.iaeep.dto.FindContactsDto;
import com.iaeep.entity.*;
import com.iaeep.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DELL
 * @version 1.0
 * @Classname ContactsController
 * @Description TODO
 * @CreateDate 2022/10/2 9:45
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:45
 */
@RestController
@Slf4j
@RequestMapping("/contact")
public class ContactsController {
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseInformationService enterpriseInformationService;
    @Autowired
    private PersonalInformationService personalInformationService;
    @Autowired
    private ChatRecordService chatRecordService;


    //修改聊天记录状态，将当前私信关系的未读信息修改为已读
    /**
     *
     * @param: data =>{toUserId:"",fromUserId:""}
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈〉
     * @date : 2022/11/13 11:43
     */
    @RequestMapping("/changeToRead")
    private R<String> changeToReade(@RequestBody Contacts contacts){
        Long toUserId = contacts.getToUserId();
        Long fromUserId = contacts.getFromUserId();
        LambdaUpdateWrapper<ChatRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ChatRecord::getIsRead, 1);
        updateWrapper.eq(ChatRecord::getToUserId,fromUserId);
        updateWrapper.eq(ChatRecord::getFromUserId, toUserId);
        //修改数据库
        chatRecordService.update(updateWrapper);
        return R.success("success",null);
    }

    /**
     *
     * @param: null
     * @return : {@link null}
     * @author : liujiahui
     * @description: 〈修改临时聊天记录〉
     * @date : 2022/11/16 20:32
     */
    @PostMapping("/toRead")
    private R<String> toRead(@RequestBody ChatRecord chatRecord){
        List<ChatRecord> chatRecordList = ChatRecordController.getChatRecordList();
        for(int i = 0; i < chatRecordList.size(); i++){
            if(chatRecordList.get(i).getFromUserId().equals(chatRecord.getFromUserId())
                    && chatRecordList.get(i).getToUserId().equals(chatRecord.getToUserId())){
                chatRecordList.get(i).setIsRead(1);
            }
        }
        return R.success("success",null);
    }

    /**
     *
     * @param: userId
     * @return : {@link R<List<Contacts>>}
     * @author : liujiahui
     * @description: 〈获取全部联系人〉
     * @date : 2022/10/27 17:50
     */
    @GetMapping("/getAllContacts/{userId}")
    public R<List<Contacts>> getAllContacts(@PathVariable("userId") String userId){
        LambdaUpdateWrapper<Contacts> query = new LambdaUpdateWrapper<>();
        query.eq(Contacts::getFromUserId, userId);
        List<Contacts> contactsList = contactsService.list(query);
        for(int i= 0;i<contactsList.size();i++) {
            contactsList.get(i).setUnRead(0);
            //判断该联系人是否存在未读信息
            LambdaQueryWrapper<ChatRecord> queryChat = new LambdaQueryWrapper<>();
            queryChat.eq(ChatRecord::getFromUserId,contactsList.get(i).getToUserId());
            //0代表该条信息未读
            queryChat.eq(ChatRecord::getIsRead, 0);
            int cnt = chatRecordService.count(queryChat);
            if (cnt > 0){
                //存在未读信息,1表示存在未读信息
                contactsList.get(i).setUnRead(1);
                continue;
            }
            //判断当前为插入数据库的聊天记录中是否存在未读记录：
            List<ChatRecord> chatRecordList = ChatRecordController.getChatRecordList();
            for (int k=0;k < chatRecordList.size();k++) {
                if (chatRecordList.get(k).getFromUserId().equals(contactsList.get(i).getToUserId()) && chatRecordList.get(k).getIsRead() == 0){
                    //存在未读信息
                    contactsList.get(i).setUnRead(1);
                    break;
                }
            }
        }
        return R.success(contactsList);
    }

    /**
     *
     * @param: username
     * @return : {@link R<List<FindContactsDto>>}
     * @author : liujiahui
     * @description: 〈根据用户名查询用户〉
     * @date : 2022/10/29 8:59
     */
    @GetMapping("/findContactList")
    public R<List<FindContactsDto>> findContactList(@RequestParam("username") String username){
        List<FindContactsDto> findContactsDtoList = new ArrayList<>();
        //查询企业用户条件
        List<EnterpriseInformation> enterpriseInformationList = new ArrayList<>();
        QueryWrapper<EnterpriseInformation> enterpriseQuery = new QueryWrapper<>();
        enterpriseQuery.select("user_id","name").like("name",username);
        //查询个人用户条件
        List<PersonalInformation> personalInformationList = new ArrayList<>();
        QueryWrapper<PersonalInformation> personalQuery = new QueryWrapper<>();
        personalQuery.select("user_id","name").like("name",username);
        //数据库查询
        enterpriseInformationList = enterpriseInformationService.list(enterpriseQuery);
        personalInformationList = personalInformationService.list(personalQuery);
        //封装结果
        for(int i = 0; i <personalInformationList.size(); i++) {
            FindContactsDto contact = new FindContactsDto();
            contact.setUserId(personalInformationList.get(i).getUserId());
            contact.setName(personalInformationList.get(i).getName());
            findContactsDtoList.add(contact);
        }
        for(int i = 0; i <enterpriseInformationList.size(); i++) {
            FindContactsDto contact = new FindContactsDto();
            contact.setUserId(enterpriseInformationList.get(i).getUserId());
            contact.setName(enterpriseInformationList.get(i).getName());
            findContactsDtoList.add(contact);
        }
        //返回结果
        return R.success(findContactsDtoList);
    }


    /**
     *
     * @param: findContactsDto
     * @param: request
     * @return : {@link R<Contacts>}
     * @author : liujiahui
     * @description: 〈添加联系人〉
     * @date : 2022/10/29 9:41
     */
    @PostMapping("/addContacts")
    public R<Contacts> addContacts(@RequestBody  FindContactsDto findContactsDto, HttpServletRequest request){
        Contacts contacts = new Contacts();
        //封装联系人信息
        contacts.setFromUserId(((User)request.getSession().getAttribute("user")).getId());
        contacts.setName(findContactsDto.getName());
        //默认没有未读信息
        contacts.setUnRead(0);
        if (findContactsDto.getUserId() != null){
            contacts.setToUserId(findContactsDto.getUserId());
        }else {
            //个人寻找
            LambdaQueryWrapper<PersonalInformation> query = new LambdaQueryWrapper<>();
            query.eq(PersonalInformation::getName,findContactsDto.getName());
            PersonalInformation personalInformation = personalInformationService.getOne(query);
            if(personalInformation !=null ){
                contacts.setToUserId(personalInformation.getUserId());
                //保存数据库
                contacts.setUnRead(0);
                contactsService.save(contacts);
                return R.success("添加联系人成功",contacts);
            }
            //企业中查询
            LambdaQueryWrapper<EnterpriseInformation> query1 = new LambdaQueryWrapper<>();
            query1.eq(EnterpriseInformation::getName,findContactsDto.getName());
            EnterpriseInformation enterpriseInformation = enterpriseInformationService.getOne(query1);
            contacts.setToUserId(enterpriseInformation.getUserId());
        }
        //保存数据库
        contacts.setUnRead(0);
        contactsService.save(contacts);
        return R.success("添加联系人成功",contacts);
    }


    /**
     *
     * @param: id
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈删除联系人〉
     * @date : 2022/10/29 9:46
     */
    @GetMapping("/deleteContact/{id}")
    public R<String> deleteContacts(@PathVariable Long id){
        contactsService.removeById(id);
        return R.success("删除联系人成功");
    }


}

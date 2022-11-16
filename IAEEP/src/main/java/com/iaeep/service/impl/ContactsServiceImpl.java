package com.iaeep.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaeep.entity.Contacts;
import com.iaeep.mapper.ContactsMapper;
import com.iaeep.service.ContactsService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @version 1.0
 * @Classname ContactsService
 * @Description TODO
 * @CreateDate 2022/10/2 9:19
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:19
 */
@Service
public class ContactsServiceImpl extends ServiceImpl<ContactsMapper,Contacts> implements ContactsService {
}

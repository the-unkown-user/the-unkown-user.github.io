package com.iaeep.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaeep.entity.ChatRecord;
import com.iaeep.mapper.ChatRecordMapper;
import com.iaeep.service.ChatRecordService;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @version 1.0
 * @Classname ChatRecordService
 * @Description TODO
 * @CreateDate 2022/10/2 9:17
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:17
 */
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper,ChatRecord> implements ChatRecordService {
}

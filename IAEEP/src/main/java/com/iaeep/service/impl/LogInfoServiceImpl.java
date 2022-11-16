package com.iaeep.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaeep.entity.LogInfo;
import com.iaeep.mapper.LogInfoMapper;
import com.iaeep.service.LogInfoService;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @version 1.0
 * @Classname LogInfoService
 * @Description TODO
 * @CreateDate 2022/10/2 9:21
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:21
 */
@Service
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper,LogInfo> implements LogInfoService {
}

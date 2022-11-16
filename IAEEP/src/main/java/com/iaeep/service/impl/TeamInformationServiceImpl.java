package com.iaeep.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iaeep.entity.TeamInformation;
import com.iaeep.mapper.TeamInformationMapper;
import com.iaeep.service.TeamInformationService;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 * @version 1.0
 * @Classname TeamInformationService
 * @Description TODO
 * @CreateDate 2022/10/2 9:24
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:24
 */
@Service
public class TeamInformationServiceImpl extends ServiceImpl<TeamInformationMapper,TeamInformation> implements TeamInformationService {
}

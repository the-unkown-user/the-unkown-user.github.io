package com.iaeep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.iaeep.common.Md5Utils;
import com.iaeep.common.R;
import com.iaeep.common.SmsUtils;
import com.iaeep.dto.CodeDto;
import com.iaeep.dto.RegisterDto;
import com.iaeep.dto.UserDto;
import com.iaeep.dto.UserInfoDto;
import com.iaeep.entity.*;
import com.iaeep.service.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;

/**
 * @author DELL
 * @version 1.0
 * @Classname UserController
 * @Description TODO
 * @CreateDate 2022/10/2 9:47
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/2 9:47
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EnterpriseInformationService enterpriseInformationService;
    @Autowired
    private PersonalInformationService personalInformationService;
    @Autowired
    private ChatRecordService chatRecordService;
    @Autowired
    private FollowInfoService followInfoService;
    @Autowired
    private PostCollectionService postCollectionService;
    @Autowired
    private PostsService postsService;


    /**
     *
     * @param: registerDto
     * @return : {@link R< String>}
     * @author : liujiahui
     * @description: 〈注册账户〉
     * @date : 2022/10/27 16:57
     */
    @PostMapping("/register")
    public R<String> register(@RequestBody RegisterDto registerDto){
        String codeName = "code";
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        if(session.getAttribute("code") == null){
            return R.error("请先获取验证码");
        }
        if(registerDto.getCode() == null){
            return R.error("验证码为空");
        }
        if(registerDto.getPassword() == null){
            return R.error("密码为空");
        }
        if(registerDto.getUserType() == null){
            return R.error("用户类型为空");
        }
        if(!registerDto.getCode().equals((String) session.getAttribute(codeName))){
           return R.error("验证码错误");
        }
        System.out.println(registerDto);
        User user = new User();
        user.setPassword(Md5Utils.getEncryptionPassword(registerDto.getPassword()));
        user.setAccount(registerDto.getPhone());
        user.setUserType(registerDto.getUserType());
        user.setLastLoginTime(new Date());
        userService.save(user);
        LambdaQueryWrapper<User> q = new LambdaQueryWrapper<>();
        q.eq(User::getAccount,user.getAccount());
        user = userService.getOne(q);
        //添加个人、企业基本信息
        if (user.getUserType().equals("个人")){
            PersonalInformation personalInformation = new PersonalInformation();
            personalInformation.setAvatar("../image/avatar/6.jpg");
            personalInformation.setName(user.getAccount());
            personalInformation.setPhone(user.getAccount());
            personalInformation.setUserId(user.getId());
            personalInformationService.save(personalInformation);
        }else {
            EnterpriseInformation enterpriseInformation = new EnterpriseInformation();
            enterpriseInformation.setAvatar("../image/avatar/6.jpg");
            enterpriseInformation.setName(user.getAccount());
            enterpriseInformation.setPhone(user.getAccount());
            enterpriseInformation.setUserId(user.getId());
            enterpriseInformationService.save(enterpriseInformation);
        }
        return R.success("注册成功");
    }

    /**
     *
     * @param: phone
     * @param: request
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈发送手机验证码〉
     * @date : 2022/10/27 16:57
     */
    @PostMapping("/sendCode")
    public R<String> sendCode(@RequestBody CodeDto codeDto, HttpServletRequest request){
        try {
            System.out.println("phone:"+codeDto.getPhone());
            String code = SmsUtils.sentSms(codeDto.getPhone());
            if(code.equals("false")){
                return R.error("验证码发送失败");
            }
            request.getSession().setAttribute("code", code);
            System.out.println("code: " + code);
            return R.success("验证码发送成功");
        }catch (Exception e) {
            return R.error("验证码发送失败");
        }
    }

    /**
     *
     * @param: username
     * @param: password
     * @param: request
     * @return : {@link R< UserDto>}
     * @author : liujiahui
     * @description: 〈登录功能〉
     * @date : 2022/10/27 16:57
     */
    @PostMapping("/login")
    public R<UserDto> login(@RequestBody RegisterDto registerDto,HttpServletRequest request){
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        if(username == null || password == null){
            return R.error("Please enter a username and password");
        }
        UserDto userDto = new UserDto();
        //获取用户账户信息
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getAccount,username);
        User user = userService.getOne(query);
        //判断用户是否有未读信息
        LambdaQueryWrapper<ChatRecord> chatQuery = new LambdaQueryWrapper<>();
        //chatQuery.gt(ChatRecord::getTime , user.getLastLoginTime());
        //发送给当前用户的信息
        chatQuery.eq(ChatRecord::getToUserId,user.getId());
        //未读的信息
        chatQuery.eq(ChatRecord::getIsRead,0);
        int cnt = chatRecordService.count(chatQuery);
        if (cnt > 0){
            //存在未读信息
            userDto.setUnReadeInfo(true);
        }else{
            //不存在未读信息
            userDto.setUnReadeInfo(false);
        }
        //获取用户基本信息
        if(user != null) {
            if(Md5Utils.getEncryptionPassword(password).equals(user.getPassword())){
                BeanUtils.copyProperties(user,userDto);
                if(user.getUserType().equals("个人")){
                    PersonalInformation personalInformation = personalInformationService.getById(user.getId());
                    userDto.setPersonalInformation(personalInformation);
                    request.getSession().setAttribute("user", user);
                    return R.success(userDto);
                }else if(user.getUserType().equals("企业")){
                    EnterpriseInformation enterpriseInformation = enterpriseInformationService.getById(user.getId());
                    userDto.setEnterpriseInformation(enterpriseInformation);
                    request.getSession().setAttribute("user", user);
                    return R.success(userDto);
                }else{
                    return R.error("未知的账户类型");
                }
            }else{
                return R.error("用户名或密码错误");
            }
        }else{
            return R.error("用户名或密码错误");
        }
    }


    /**
     *
     * @param: registerDto
     * @return : {@link R<String>}
     * @author : liujiahui
     * @description: 〈重置密码〉
     * @date : 2022/10/27 17:14
     */
    @PostMapping("/resetPassword")
    public R<String> resetPassword(RegisterDto registerDto){
        LambdaUpdateWrapper<User> query = new LambdaUpdateWrapper<>();
        query.set(User::getPassword,Md5Utils.getEncryptionPassword(registerDto.getPassword()))
                .eq(User::getAccount,registerDto.getUsername());
        if (userService.update(query)){
            return R.success("密码重置成功");
        }else{
            return R.error("密码重置失败");
        }
    }

    /**
     *
     * @param: request
     * @return :
     * @author : liujiahui
     * @description: 〈用户退出〉
     * @date : 2022/10/27 17:17
     */
    @PostMapping("/loginOut")
    public R<String> loginOut(HttpServletRequest request){
        request.getSession().invalidate();//销毁session，用户下线
        return R.success("用户下线");
    }


    /**
     *
     * @param: null
     * @return : {@link null}
     * @author : liujiahui
     * @description: 〈查询用户信息〉
     * @date : 2022/10/29 14:15
     */
    @GetMapping("/userInfo/{userId}")
    public R<UserInfoDto> userInfo(@PathVariable("userId") Long userId){
        UserInfoDto userInfoDto = new UserInfoDto();
        //获取用户类型
        User user = userService.getById(userId);
        //获取用户信息

        if(user.getUserType().equals("个人")){
            LambdaQueryWrapper<PersonalInformation> query = new LambdaQueryWrapper<>();
            query.eq(PersonalInformation::getUserId,userId);
            PersonalInformation personalInformation = personalInformationService.getOne(query);
            BeanUtils.copyProperties(personalInformation,userInfoDto);
        }else {
            LambdaQueryWrapper<EnterpriseInformation> query = new LambdaQueryWrapper<>();
            query.eq(EnterpriseInformation::getUserId,userId);
            EnterpriseInformation enterpriseInformation = enterpriseInformationService.getOne(query);
            BeanUtils.copyProperties(enterpriseInformation,userInfoDto);
        }

        //查询 粉丝数 获赞数 收藏数 贴子数
        //粉丝数 关注我的
        LambdaQueryWrapper<FollowInfo> query = new LambdaQueryWrapper<>();
        query.eq(FollowInfo::getUserId2,user.getId());
        Integer followNum =followInfoService.count(query);

        //获赞数
        Integer likeNum = postsService.getLikeNum(user.getId());

        //收藏数
        LambdaQueryWrapper<PostCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PostCollection::getUserId, user.getId());
        Integer collection = postCollectionService.count(queryWrapper);

        //贴子数
        LambdaQueryWrapper<Posts> queryWrapper1= new LambdaQueryWrapper<>();
        queryWrapper1.eq(Posts::getUserId, user.getId());
        Integer posts = postsService.count(queryWrapper1);

        //封装
        userInfoDto.setFollow(followNum);
        userInfoDto.setLike(likeNum);
        userInfoDto.setCollection(collection);
        userInfoDto.setPosts(posts);
        return R.success(userInfoDto);
    }

    @SneakyThrows
    @PostMapping("/modifyAvatar")
    public R<String> modifyAvatar(MultipartFile file){
        System.out.println("项目绝对路径"+System.getProperty("user.dir"));
        if (file == null) {
            return R.error("File cannot be null");
        }else{
            String fileName = file.getName();
            String path = System.getProperty("user.dir")+"\\src\\main\\resources\\iaeep\\image\\avatar\\"+fileName;
            //判断该文件是否存在
            File f = new File(path+".jpg");
            int cnt =0;
            String path2 = "";
            while(f.exists()){
                cnt++;
                path2 = path + String.valueOf(cnt);
                f =new File(path2+".jpg");
            }
            //保存图片
            file.transferTo(f);
            String url ="";
            if (cnt ==0){
                url = "../image/avatar/"+fileName+".jpg";
            }else {
                url = "../image/avatar/"+fileName+String.valueOf(cnt)+".jpg";
            }

            System.out.println("url :: "+url);
            System.out.println("path ::"+ path2);
            //路径存入数据库

            return R.success(url);
        }
    }

}

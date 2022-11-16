package com.iaeep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author DELL
 * @version 1.0
 * @Classname ReggieApplication
 * @Description TODO springBoot 启动类
 * @CreateDate 2022/9/26 16:54
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/9/26 16:54
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement //开启事务管理
/**
 *@ServletComponentScan 的作用:
 * 在SpringBoot项目中, 在引导类/配置类上加了该注解后, 会自动扫描项目中(当前包及
 * 其子包下)的@WebServlet , @WebFilter , @WebListener 注解, 自动注册Servlet的
 * 相关组件 ;
 * @author DELL
 * @version 1.0
 */
@EnableScheduling
@ServletComponentScan
public class IaeepApplication {
    public static void main(String[] args) {
        //启动springBoot 项目
        /**
         *run方法表示启动
         * 第一个参数 填写 启动类全类名 当前启动类ReggieApplication 所以其全类名为ReggieApplication.class
         * 第二个参数是携带的数据 我们调用的是主方法， 主方法传入什么参数 我们就直接传入进去
         */
        SpringApplication.run(IaeepApplication.class, args);
        //日志文件输出打印到控制台
        log.info("SpringBootApplication started successfully on ");

    }
}


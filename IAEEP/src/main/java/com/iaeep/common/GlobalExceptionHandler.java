package com.iaeep.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


/**
 * @author DELL
 * @version 1.0
 * @Classname GlobaExceptionHandler
 * @Description TODO 全局异常处理{
 *     注解说明:
 * 上述的全局异常处理器上使用了的两个注解 @ControllerAdvice , @ResponseBody
 * , 他们的作用分别为:
 * @ControllerAdvice : 指定拦截那些类型的控制器;
 * @ResponseBody: 将方法的返回值 R 对象转换为json格式的数据, 响应给页面;
 * 上述使用的两个注解, 也可以合并成为一个注解 @RestControllerAdvice
 * }
 * @CreateDate 2022/9/27 9:56
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/9/27 9:56
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     *
     * @param: null
     * @return : {@link null}
     * @author : liujiahui
     * @description: 〈异常处理方法〉
     * @date : 2022/9/27 9:57
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String [] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
    /**
     * 异常处理方法,自定义异常
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }

}

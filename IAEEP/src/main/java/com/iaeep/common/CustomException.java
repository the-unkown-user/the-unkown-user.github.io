package com.iaeep.common;

/**
 * @author DELL
 * @version 1.0
 * @Classname CustomException
 * @Description TODO 自定义业务异常类
 * @CreateDate 2022/9/27 17:32
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/9/27 17:32
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}

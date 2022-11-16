package com.iaeep.common;

/**
 * @author DELL
 * @version 1.0
 * @Classname BaseContext
 * @Description TODO 基于ThreadLocal 封装工具类，用户保存和获取当前登录用户id
 * @CreateDate 2022/9/27 16:18
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/9/27 16:18
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

package com.iaeep.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author DELL
 * @version 1.0
 * @Classname SpringUtils
 * @Description TODO
 * @CreateDate 2022/10/30 9:48
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/10/30 9:48
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    //spring应用的上下文环境
    private static ApplicationContext applicationContext=null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //实现ApplicationContextAware接口的回调方法，设置上下文环境
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    //byType获取对象
    public static <T> T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }
    //byName+byType获取对象
    public static <T> T getBean(String name,Class<T> requiredType){
        return applicationContext.getBean(name,requiredType);
    }
    //byName获取对象
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
    //byName判读对象是否存在
    public static  boolean contains(String name){
        return applicationContext.containsBean(name);
    }
    //判断对象是否是单例
    public static boolean isSignleton(String name){
        return applicationContext.isSingleton(name);
    }
    //判断注册对象的类型
    public static Class<?> getType(String name){
        return applicationContext.getType(name);
    }
    //返回bean的别名
    public static String[] getAliases(String name){
        return applicationContext.getAliases(name);
    }

    //根据类型获取bean的名字
    public String[] getBeanNamesForType(Class<?> clazz){
        return applicationContext.getBeanNamesForType(clazz);
    }

}

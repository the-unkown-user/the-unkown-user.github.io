package com.iaeep.filter;

import com.alibaba.fastjson.JSON;
import com.iaeep.common.BaseContext;
import com.iaeep.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author DELL
 * @version 1.0
 * @Classname LoginCheckFilter
 * @Description TODO 检查用户是否已经完成登录
 * @CreateDate 2022/9/27 9:16
 * @Created by DELL
 * @Update DELL
 * @UpdateDate 2022/9/27 9:16
 */

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) servletRequest;

        filterChain.doFilter(request, response);

        /*
        //1.获取本次请求url
        String requestURL = request.getRequestURI();

        log.info("Request URL: " + requestURL);

        //定义不需要处理的请求路径
        String [] urls = new String[]{
                "/user/login",
                "/iaeep/**",
                "/favicon.ico",
        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURL);

        //3.如果不需要处理，则直接放行

        if(check){
            log.info("本次请求{}不许要处理",requestURL);
            filterChain.doFilter(request, response);
            return;
        }

        //4.判断管理员登录状态，如果已经登录，则直接放行
        if(request.getSession().getAttribute("admin") != null ){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("admin"));

            //将当前登录用户id存入ThreadLocal中待用
            Long empId = (Long)request.getSession().getAttribute("admin");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        //判断用户登录状态
        if(request.getSession().getAttribute("user")!= null ){
            log.info("用户已登录，用户名为：{}",request.getSession().getAttribute("user"));
            //将当前登录用户id存入ThreadLocal中待用
            Long userId =(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");

        //5.如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

        return;
         */
    }
    /**
     *
     * @return :
     * @author : liujiahui
     * @description: 〈路径匹配〉
     * @date : 2022/9/27 9:36
     */
    public boolean check(String [] urls,String requestURL){
        for(String url : urls){
            if(PATH_MATCHER.match(url, requestURL)){
                return true;
            }
        }
        return false;
    }



    @Override
    public void destroy() {

    }
}

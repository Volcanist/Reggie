package com.tc.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.tc.reggie.common.BaseContext;
import com.tc.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查是否登录
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //获取本次请求的URI
        String requestURI=request.getRequestURI();

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        //判断路径是否需要处理
        boolean check=check(urls,requestURI);

        //不需要处理则放行
        if(check){
            filterChain.doFilter(request,response);
            return;
        }

        //判断员工登陆状态，如果已登录，则直接放行,顺便存一下用户id
        if(request.getSession().getAttribute("employee")!=null){

            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            Long id=Thread.currentThread().getId();
            log.info("线程id为{}" ,id);

            filterChain.doFilter(request,response);
            return;
        }

        // 判断用户登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已登录，用户 id 为: {}", request.getSession().getAttribute("user"));

            Long userId = (Long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            long id = Thread.currentThread().getId();
            log.info("线程 id 为: {}", id);

            filterChain.doFilter(request, response);
            return;
        }

        //如果未登录，则返回登陆结果，通过输出流向客户端页面响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


//        log.info("拦截成功：{}",request.getRequestURI());
//        filterChain.doFilter(request,response);
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI) {
        for(String url:urls){
            boolean match=PATH_MATCHER.match(url,requestURI);
            if (match) return true;
        }
        return false;
    }
}

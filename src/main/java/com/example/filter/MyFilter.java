package com.example.filter;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author user01
 * @create 2019/10/29
 */
//@WebFilter(urlPatterns = "/*",filterName = "MyFilter",
//        initParams = {@WebInitParam(name = "exclude_pages",value = "*.html;js/*.js;imgs/*")})
public class MyFilter implements Filter {

    private String excludedPaths;
    private String [] excludedPathArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedPaths = filterConfig.getInitParameter("exclude_pages");
        System.out.println(excludedPaths);
        if (!StringUtils.isEmpty(excludedPaths)){
            excludedPathArray = excludedPaths.split(";");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                                     FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter.doFilter(被执行了)");
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=utf-8");

        if (!isFilterExcludeRequest((HttpServletRequest)servletRequest)) {
              //过滤逻辑
//            HttpSession session = ((HttpServletRequest) servletRequest).getSession();
//            if (session.getAttribute("user")==null){
//                ((HttpServletResponse)servletResponse).sendRedirect("login.html");
//            }
        }
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("MyFilter.doFilter(被执行了2)");
    }

    @Override
    public void destroy() {

    }

    private boolean isFilterExcludeRequest(HttpServletRequest request) {
        if(null != excludedPathArray && excludedPathArray.length > 0) {
            String url = request.getRequestURI();
            for (String excludedUrl : excludedPathArray) {
                if (excludedUrl.startsWith("*.")) {
                    // 如果配置的是后缀匹配, 则把前面的*号干掉，然后用endWith来判断
                    if(url.endsWith(excludedUrl.substring(1))){
                        return true;
                    }
                } else if (excludedUrl.endsWith("/*")) {
                    if(!excludedUrl.startsWith("/")) {
                        // 前缀匹配，必须要是/开头
                        excludedUrl = "/" + excludedUrl;
                    }
                    // 如果配置是前缀匹配, 则把最后的*号干掉，然后startWith来判断
                    String prefixStr = request.getContextPath() + excludedUrl.substring(0, excludedUrl.length() - 1);
                    if(url.startsWith(prefixStr)) {
                        return true;
                    }
                } else {
                    // 如果不是前缀匹配也不是后缀匹配,那就是全路径匹配
                    if(!excludedUrl.startsWith("/")) {
                        // 全路径匹配，也必须要是/开头
                        excludedUrl = "/" + excludedUrl;
                    }
                    String targetUrl = request.getContextPath() + excludedUrl;
                    if(url.equals(targetUrl)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


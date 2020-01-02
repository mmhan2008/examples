package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

/**
 * @author user01
 * @create 2019/10/29
 */
// @WebListener
public class MyListener implements ServletRequestListener,ServletRequestAttributeListener,
        HttpSessionListener,HttpSessionAttributeListener,ServletContextListener,
        ServletContextAttributeListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("request对象被销毁了");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("request对象被创建了");
    }


    //监听request作用域数据的添加
    @Override
    public void attributeAdded(ServletRequestAttributeEvent event) {
        System.out.println("request中添加一条数据"+event.getName()+"--"+event.getValue());
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent event) {

    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent event) {

    }
    //监听session的创建和销毁
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        System.out.println("session被创建了");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session被销毁了");
    }
    //监听session中数据的变化
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println("session中增加了一条数据" + se.getName() +"--" + se.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {

    }
    //application对象监听
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("application 对象初始化了");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("application 销毁了");
    }


    //application对象属性监听
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("application中增加了一条数据" + event.getName() +"--" + event.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {

    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {

    }
}


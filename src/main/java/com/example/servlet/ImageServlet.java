package com.example.servlet;

import org.springframework.util.ResourceUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author user01
 * @create 2019/10/28
 */

@WebServlet("/index")
public class ImageServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //输出文字流
    //        PrintWriter out = response.getWriter();
        //获取响应流
        ServletOutputStream os = response.getOutputStream();
        InputStream ins = new FileInputStream(ResourceUtils.getFile("classpath:static/imgs/b.jpg"));
        int index = -1;
        while ((index = ins.read()) != -1){
            os.print(index);
        }
        os.flush();
    }
    public static void main(String[] args) throws IOException {
        ImageServlet servlet = new ImageServlet();
        //获取类加载的根路径
        String classPath = servlet.getClass().getResource("/").getPath();
        System.out.println(classPath);
        //获取当前类的所在工程路径
        String classPath1 = servlet.getClass().getResource("").getPath();
        System.out.println(classPath1);
        //获取项目路径
        String path = new File("").getCanonicalPath();
        System.out.println(path);
        System.out.println(System.getProperty("user.dir"));

        //获取所有的类路径 包括jar包的路径
        System.out.println(System.getProperty("java.class.path"));
    }
}


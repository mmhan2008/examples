package com.example.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author user01
 * @create 2019/10/29
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        req.setAttribute("message","request be monitored");
        HttpSession session = req.getSession();
        session.setAttribute("message","session be monitored");
        session.invalidate();
        ServletContext sc = this.getServletContext();
        sc.setAttribute("message","application be monitored");
        resp.getWriter().write("this is listener study");
    }
}


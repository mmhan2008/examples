package com.example.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author user01
 * @create 2019/10/28
 */
@WebServlet("/add")
public class ValidCodeServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedImage image = new BufferedImage(150,50,BufferedImage.TYPE_INT_RGB);
        //创建画布
        Graphics2D graphics = image.createGraphics();
        //填充画布颜色
        graphics.setColor(Color.WHITE);
        //从哪个坐标开始填充
        graphics.fillRect(0,0,150,50);
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            list.add(random.nextInt(10));
        }
        //设置字体
        graphics.setFont(new Font("宋体",Font.ITALIC|Font.BOLD,20));
        Color[] colors = {Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.PINK,Color.GRAY};
        for (int i = 0; i < list.size(); i++) {
            graphics.setColor(colors[random.nextInt(colors.length)]);
            graphics.drawString(list.get(i)+"",i*40,25+(random.nextInt(21)-10));
        }
        //画横线
        for (int i = 0; i < 4; i++) {
            graphics.setColor(colors[random.nextInt(colors.length)]);
            graphics.drawLine(10,random.nextInt(51),140,random.nextInt(51));
        }
        ServletOutputStream outputStream = resp.getOutputStream();
        ImageIO.write(image,"jpg",outputStream);
    }
}

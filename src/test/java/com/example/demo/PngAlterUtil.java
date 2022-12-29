package com.example.demo;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2022-12-26 21:36
 */
public class PngAlterUtil {

    private Font font = new Font("宋体", Font.PLAIN, 12); // 添加字体的属性设置
    private Graphics2D g = null;
    private int fontsize = 0;
    private int x = 0;
    private int y = 0;

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.RED);//设置背景颜色
            g.setColor(Color.BLUE);//设置字体颜色
            if (this.font != null)
                g.setFont(this.font);
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (content != null) {
                g.drawString(content.toString(), this.x, this.y);
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    public static void main(String[] args) {

        PngAlterUtil tt = new PngAlterUtil();

        BufferedImage d = tt.loadImageLocal("/Users/world/Downloads/WechatIMG418.png");

        //往图片上编辑内容
        tt.writeImageLocal("/Users/world/Downloads/WechatIMG4128.png", tt.modifyImage(d, "这是文本内容啦啦啦啦啦", 0, 200));

        System.out.println("success");
    }


}

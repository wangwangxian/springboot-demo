package com.jw.demo.utils;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MergePicturesUtil {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //源图片路径
        String source = "http://img1.qdingnet.com/4c2d8d17e27f81f1db6da90f258d5689.jpeg";
        //覆盖图片路径
        String icon = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGD8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyXzNrVVpoY2lhQVUxMDAwMDAwM0MAAgTVCc9bAwQAAAAA";
        //水印旋转角度-45，null表示不旋转
        Integer degree = null;
        String result2 = null;
        //给图片添加图片水印
        try {
            result2 = MergePicturesUtil.markImageBySingleIcon(icon, source, degree,570,1170,0.3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result2);
    }

    /**
     * 给图片添加单个图片水印、可设置水印图片旋转角度
     * @param icon 水印图片路径（如：F:/images/icon.png）
     * @param source 没有加水印的图片路径（如：F:/images/6.jpg）
     * @param degree 水印图片旋转角度，为null表示不旋转
     * @param x
     * @param y
     * @param scale
     */
    public static String markImageBySingleIcon(String icon,String source,Integer degree,int x,int y,double scale) throws Exception{
        String result = "添加图片水印出错";
        try {
            InputStream file = getImageStream(source);
            InputStream ficon = getImageStream(icon);
            //将icon加载到内存中
            Image ic = zoomImage(ficon,scale);
            //将源图片读到内存中
            Image img = ImageIO.read(file);
            //背景图宽
            int width = img.getWidth(null);
            //背景图高
            int height = img.getHeight(null);
            BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            //呈现一个图像，在绘制前进行从图像空间到用户空间的转换
            g.drawImage(img.getScaledInstance(width,height,Image.SCALE_SMOOTH),0,0,null);
            if (null != degree) {
                //设置水印旋转
                g.rotate(Math.toRadians(degree),(double) bi.getWidth() / 2, (double) bi.getHeight() / 2);
            }
            //透明度，最小值为0，最大值为1
            float clarity = 1f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,clarity));
            //表示水印图片的坐标位置(x,y)
            g.drawImage(ic, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            byte[] bytes = baos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            String png_base64 =  encoder.encodeBuffer(bytes).trim();
            //删除 \r\n
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
            return png_base64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取网络图片流
     *
     * @param url
     * @return
     */
    public static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }

    public static Image zoomImage(InputStream src,double scale) throws Exception {

        double wr=0,hr=0;
        //获取缩放比例
        wr=scale;
        hr=scale;
        //读取图片
        BufferedImage bufImg = ImageIO.read(src);
        int w = (new Double(bufImg.getWidth() * wr)).intValue();
        int h = (new Double(bufImg.getHeight() * hr)).intValue();
        //设置缩放目标图片模板
        Image itemp = bufImg.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);


        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        itemp = ato.filter(bufImg, null);
        return itemp;
    }
}

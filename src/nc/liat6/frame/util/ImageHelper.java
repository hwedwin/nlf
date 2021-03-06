package nc.liat6.frame.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图像处理工具类
 * 
 * @author 6tail
 * 
 */
public class ImageHelper{
  /** 默认透明度 */
  public static final int DEFAULT_ALPHA = 255;
  /** 默认背景色 */
  public static final int DEFAULT_BG = -1;
  /** 默认前景色 */
  public static final int DEFAULT_FG = -16777216;
  /** 图片保持原来大小，居中 */
  public static final int SCALE_TYPE_CENTER = 0;
  /** 非等比例缩放至充满 */
  public static final int SCALE_TYPE_FIT_XY = 1;
  /** 大图缩小至完全可见居中，小图放大至接触边缘居中 */
  public static final int SCALE_TYPE_FIT_CENTER = 2;
  /** 大图缩小至完全可见居右或下，小图放大至接触边缘居右或下 */
  public static final int SCALE_TYPE_FIT_END = 3;
  /** 大图缩小至完全可见居左或上，小图放大至接触边缘居左或上 */
  public static final int SCALE_TYPE_FIT_START = 4;
  /** 将图片等比例缩放，不留空白，缩放后截取中间部分 */
  public static final int SCALE_TYPE_CENTER_CROP = 5;
  /** 大图缩小至完全可见居中，小图保持原大小居中 */
  public static final int SCALE_TYPE_CENTER_INSIDE = 6;
  /** 默认缩放模式 */
  public static final int DEFAULT_SCALE_TYPE = SCALE_TYPE_CENTER_CROP;
  /** 默认缩放背景色 */
  public static final Color DEFAULT_SCALE_BG = Color.WHITE;

  private ImageHelper(){}

  /**
   * 由r,g,b值构成默认透明度的像点
   * 
   * @param r R
   * @param g G
   * @param b B
   * @return 像点
   */
  public static int toInt(int r,int g,int b){
    return toInt(DEFAULT_ALPHA,r,g,b);
  }

  /**
   * 由透明度，r,g,b值构成像点
   * 
   * @param alpha 透明度
   * @param r R
   * @param g G
   * @param b B
   * @return 像点
   */
  public static int toInt(int alpha,int r,int g,int b){
    return alpha<<24|r<<16|g<<8|b;
  }

  /**
   * 获取像点的透明度
   * 
   * @param pixel 像点
   * @return 透明度
   */
  public static int alpha(int pixel){
    return (pixel&0xff000000)>>24;
  }

  /**
   * 获取像点的r,g,b值
   * 
   * @param pixel 像点
   * @return r,g,b值
   */
  public static int[] rgb(int pixel){
    int r = (pixel&0xff0000)>>16;
    int g = (pixel&0xff00)>>8;
    int b = (pixel&0xff);
    int[] m = {r,g,b};
    return m;
  }

  /**
   * 从流读取图像
   * 
   * @param stream 输入流
   * @return 图像
   */
  public static BufferedImage image(InputStream stream){
    try{
      return ImageIO.read(stream);
    }catch(IOException e){
      throw new RuntimeException("stream转换失败"+e);
    }
  }

  /**
   * 字节数组转图像
   * 
   * @param bytes 字节数组
   * @return 图像
   */
  public static BufferedImage image(byte[] bytes){
    InputStream is = new ByteArrayInputStream(bytes);
    return image(is);
  }

  /**
   * 像点数组转图像
   * 
   * @param pixels 像点数组
   * @param width 宽度
   * @param height 高度
   * @return 图像
   */
  public static BufferedImage image(int[] pixels,int width,int height){
    Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width,height,pixels,0,width));
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
    Graphics2D g = bufferedImage.createGraphics();
    g.drawImage(image,0,0,null);
    return bufferedImage;
  }

  /**
   * 图像转字节数组
   * 
   * @param image 原图像
   * @return 字节数组
   * @throws IOException
   * @throws ImageFormatException
   */
  public static byte[] bytes(BufferedImage image) throws ImageFormatException,IOException{
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
    encoder.encode(image);
    return os.toByteArray();
  }

  /**
   * 图像转像点数组
   * 
   * @param image 原图像
   * @return 像点数组
   */
  public static int[] pixels(BufferedImage image){
    int iw = image.getWidth();
    int ih = image.getHeight();
    int[] pixels = new int[iw*ih];
    PixelGrabber pg = new PixelGrabber(image.getSource(),0,0,iw,ih,pixels,0,iw);
    try{
      pg.grabPixels();
    }catch(InterruptedException e){
      throw new RuntimeException("pixels转换失败"+e);
    }
    return pixels;
  }

  public static BufferedImage resize(BufferedImage o,int width,int height,int type,Color bg){
    switch(type){
      case SCALE_TYPE_CENTER:
        return resizeCenter(o,width,height,bg);
      case SCALE_TYPE_CENTER_CROP:
        return resizeCenterCrop(o,width,height,bg);
      case SCALE_TYPE_CENTER_INSIDE:
        return resizeCenterInside(o,width,height,bg);
      case SCALE_TYPE_FIT_CENTER:
        return resizeFitCenter(o,width,height,bg);
      case SCALE_TYPE_FIT_START:
        return resizeFitStart(o,width,height,bg);
      case SCALE_TYPE_FIT_END:
        return resizeFitEnd(o,width,height,bg);
      case SCALE_TYPE_FIT_XY:
        return resizeFitXY(o,width,height,bg);
    }
    return o;
  }

  public static BufferedImage resize(BufferedImage o,int width,int height,int type){
    return resize(o,width,height,type,DEFAULT_SCALE_BG);
  }

  public static BufferedImage resize(BufferedImage o,int width,int height){
    return resize(o,width,height,DEFAULT_SCALE_TYPE,DEFAULT_SCALE_BG);
  }

  public static BufferedImage resizeFitXY(BufferedImage o,int width,int height,Color bg){
    BufferedImage t = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics g = t.createGraphics();
    g.setColor(bg);
    g.fillRect(0,0,width,height);
    g.drawImage(o.getScaledInstance(width,height,Image.SCALE_SMOOTH),0,0,width,height,null);
    g.dispose();
    return t;
  }
  
  public static BufferedImage resizeFitXY(BufferedImage o,int width,int height){
    return resizeFitXY(o,width,height,DEFAULT_SCALE_BG);
  }

  public static BufferedImage resizeCenter(BufferedImage o,int width,int height,Color bg){
    BufferedImage t = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics g = t.createGraphics();
    g.setColor(bg);
    g.fillRect(0,0,width,height);
    int ow = o.getWidth();
    int oh = o.getHeight();
    int x = (width-ow)/2;
    int y = (height-oh)/2;
    g.drawImage(o,x,y,null);
    g.dispose();
    return t;
  }

  public static BufferedImage resizeCenter(BufferedImage o,int width,int height){
    return resizeCenter(o,width,height,DEFAULT_SCALE_BG);
  }

  public static BufferedImage resizeCenterCrop(BufferedImage o,int width,int height,Color bg){
    BufferedImage t = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics g = t.createGraphics();
    g.setColor(bg);
    g.fillRect(0,0,width,height);
    int x = 0;
    int y = 0;
    int w = width;
    int h = height;
    double r0 = o.getWidth()*1D/width;
    double r1 = o.getHeight()*1D/height;
    if(r0<r1){
      x = 0;
      w = width;
      h = (int)(o.getHeight()*1D/r0);
      y = (int)((height-h)/2);
    }else{
      y = 0;
      h = height;
      w = (int)(o.getWidth()*1D/r1);
      x = (int)((width-w)/2);
    }
    g.drawImage(o.getScaledInstance(w,h,Image.SCALE_SMOOTH),x,y,w,h,null);
    g.dispose();
    return t;
  }

  public static BufferedImage resizeCenterCrop(BufferedImage o,int width,int height){
    return resizeCenterCrop(o,width,height,DEFAULT_SCALE_BG);
  }

  public static BufferedImage resizeFitCenter(BufferedImage o,int width,int height,Color bg){
    BufferedImage t = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics g = t.createGraphics();
    g.setColor(bg);
    g.fillRect(0,0,width,height);
    int x = 0;
    int y = 0;
    int w = width;
    int h = height;
    double r0 = o.getWidth()*1D/width;
    double r1 = o.getHeight()*1D/height;
    if(r0<r1){
      y = 0;
      h = height;
      w = (int)(o.getWidth()*1D/r1);
      x = (int)((width-w)/2);
    }else{
      x = 0;
      w = width;
      h = (int)(o.getHeight()*1D/r0);
      y = (int)((height-h)/2);
    }
    g.drawImage(o.getScaledInstance(w,h,Image.SCALE_SMOOTH),x,y,w,h,null);
    g.dispose();
    return t;
  }

  public static BufferedImage resizeFitCenter(BufferedImage o,int width,int height){
    return resizeFitCenter(o,width,height,DEFAULT_SCALE_BG);
  }

  public static BufferedImage resizeFitStart(BufferedImage o,int width,int height,Color bg){
    BufferedImage t = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics g = t.createGraphics();
    g.setColor(bg);
    g.fillRect(0,0,width,height);
    int x = 0;
    int y = 0;
    int w = width;
    int h = height;
    double r0 = o.getWidth()*1D/width;
    double r1 = o.getHeight()*1D/height;
    if(r0<r1){
      h = height;
      w = (int)(o.getWidth()*1D/r1);
    }else{
      w = width;
      h = (int)(o.getHeight()*1D/r0);
    }
    g.drawImage(o.getScaledInstance(w,h,Image.SCALE_SMOOTH),x,y,w,h,null);
    g.dispose();
    return t;
  }

  public static BufferedImage resizeFitStart(BufferedImage o,int width,int height){
    return resizeFitStart(o,width,height,DEFAULT_SCALE_BG);
  }

  public static BufferedImage resizeFitEnd(BufferedImage o,int width,int height,Color bg){
    BufferedImage t = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics g = t.createGraphics();
    g.setColor(bg);
    g.fillRect(0,0,width,height);
    int w = width;
    int h = height;
    double r0 = o.getWidth()*1D/width;
    double r1 = o.getHeight()*1D/height;
    if(r0<r1){
      h = height;
      w = (int)(o.getWidth()*1D/r1);
    }else{
      w = width;
      h = (int)(o.getHeight()*1D/r0);
    }
    int x = width-w;
    int y = height-h;
    g.drawImage(o.getScaledInstance(w,h,Image.SCALE_SMOOTH),x,y,w,h,null);
    g.dispose();
    return t;
  }

  public static BufferedImage resizeFitEnd(BufferedImage o,int width,int height){
    return resizeFitEnd(o,width,height,DEFAULT_SCALE_BG);
  }

  /**
   * 缩放图像
   * 
   * @param o 原图像
   * @param width 缩放后宽度
   * @param height 缩放后高度
   * @param border 是否带边框
   * @return 缩放后图像
   */
  public static BufferedImage resizeCenterInside(BufferedImage o,int width,int height,Color bg){
    BufferedImage t = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    Graphics g = t.createGraphics();
    g.setColor(bg);
    g.fillRect(0,0,width,height);
    int x = 0;
    int y = 0;
    int w = width;
    int h = height;
    if(o.getWidth()<=width&&o.getHeight()<=height){
      x = (int)((width-o.getWidth())/2);
      y = (int)((height-o.getHeight())/2);
      w = o.getWidth();
      h = o.getHeight();
    }else if(o.getWidth()<=width&&o.getHeight()>height){
      y = 0;
      h = height;
      w = (int)(o.getWidth()*1D*height/o.getHeight());
      x = (int)((width-w)/2);
    }else if(o.getWidth()>width&&o.getHeight()<=height){
      x = 0;
      w = width;
      h = (int)(o.getHeight()*1D*width/o.getWidth());
      y = (int)((height-h)/2);
    }else{
      double r0 = o.getWidth()*1D/width;
      double r1 = o.getHeight()*1D/height;
      if(r0<r1){
        y = 0;
        h = height;
        w = (int)(o.getWidth()*1D/r1);
        x = (int)((width-w)/2);
      }else{
        x = 0;
        w = width;
        h = (int)(o.getHeight()*1D/r0);
        y = (int)((height-h)/2);
      }
    }
    g.drawImage(o.getScaledInstance(w,h,Image.SCALE_SMOOTH),x,y,w,h,null);
    g.dispose();
    return t;
  }

  /**
   * 图像缩放
   * 
   * @param o 原图像
   * @param width 缩放后宽度
   * @param height 缩放后高度
   * @return 缩放后图像
   */
  public static BufferedImage resizeCenterInside(BufferedImage o,int width,int height){
    return resizeCenterInside(o,width,height,DEFAULT_SCALE_BG);
  }

  /**
   * 输出为JPG文件
   * 
   * @param o 图像
   * @param output 输出文件
   * @throws IOException
   */
  public static void writeJPG(BufferedImage o,File output) throws IOException{
    FileOutputStream out = new FileOutputStream(output);
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(o);
    param.setQuality(1f,false);
    encoder.setJPEGEncodeParam(param);
    encoder.encode(o);
    out.close();
  }
}
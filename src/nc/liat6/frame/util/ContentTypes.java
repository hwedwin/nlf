package nc.liat6.frame.util;

import java.util.HashMap;
import java.util.Map;

/**
 * MIME类型
 * 
 * @author 6tail
 * 
 */
public class ContentTypes{

  private static final String DEFAULT_TYPE = "application/octet-stream";
  private static final Map<String,String> TYPES = new HashMap<String,String>();
  static{
    TYPES.put("jfif","image/jpeg");
    TYPES.put("jpe","image/jpeg");
    TYPES.put("jpg","image/jpeg");
    TYPES.put("jpeg","image/jpeg");
    TYPES.put("gif","image/gif");
    TYPES.put("png","image/png");
    TYPES.put("bmp","image/bmp");
    TYPES.put("tif","image/tiff");
    TYPES.put("tiff","image/tiff");
    TYPES.put("ico","image/x-icon");
    TYPES.put("doc","application/msword");
    TYPES.put("docx","application/msword");
    TYPES.put("xls","application/vnd.ms-excel");
    TYPES.put("xlsx","application/vnd.ms-excel");
    TYPES.put("pdf","application/pdf");
    TYPES.put("js","text/javascript");
    TYPES.put("csv","text/csv");
    TYPES.put("txt","text/plain");
    TYPES.put("htm","text/html");
    TYPES.put("html","text/html");
    TYPES.put("swf","application/x-shockwave-flash");
    TYPES.put("zip","application/zip");
    TYPES.put("rar","application/rar");
    TYPES.put("exe","application/x-msdownload");
  }

  private ContentTypes(){}

  /**
   * 根据文件名后缀获取MIME类型
   * 
   * @param suffix 后缀名，不包括点号
   * @return MIME类型名
   */
  public static String getContenType(String suffix){
    String type = TYPES.get(suffix);
    return null==type?DEFAULT_TYPE:type;
  }
}

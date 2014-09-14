package nc.liat6.frame.locale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.request.ILocaleFetcher;
import nc.liat6.frame.util.Stringer;

public class L{

  private static final Map<String,Map<String,String>> res = new HashMap<String,Map<String,String>>();
  static{
    init();
  }

  public static void loadResource(File f) throws IOException{
    String name = f.getName();
    if(!name.endsWith(LocaleFactory.FILE_SUFFIX)){
      return;
    }
    if(!name.contains("_")){
      return;
    }
    System.out.println("load locale:"+f.getAbsolutePath());
    String locale = name.substring(0,name.indexOf("."));
    res.put(locale,gen(new FileInputStream(f)));
  }

  private static Map<String,String> gen(InputStream in) throws IOException{
    BufferedReader bi = new BufferedReader(new InputStreamReader(in,"utf-8"));
    String line = null;
    Map<String,String> map = new HashMap<String,String>();
    while(null!=(line = bi.readLine())){
      line = line.trim();
      if(line.startsWith("#")){
        continue;
      }
      if(!line.contains("=")){
        continue;
      }
      String k = Stringer.cut(line,"","=").trim();
      String v = Stringer.cut(line,"=").trim();
      map.put(k,v);
    }
    bi.close();
    return map;
  }

  public static void loadResource(LocaleResource o){
    try{
      if(o.isInJar()){
        ZipFile zip = new ZipFile(o.getHome());
        ZipEntry en = zip.getEntry(o.getFileName());
        InputStream in = zip.getInputStream(en);
        System.out.println("load locale:"+o.getHome());
        res.put(o.getLocale(),gen(in));
      }else{
        loadResource(new File(o.getHome(),o.getFileName()));
      }
    }catch(IOException e){
      throw new NlfException(o.getFileName(),e);
    }
  }

  private static void init(){
    for(LocaleResource o:LocaleFactory.localeResources){
      loadResource(o);
    }
  }

  public static String get(String key){
    Request r = Context.get(Statics.REQUEST);
    if(null==r){
      return get(LocaleFactory.locale,key);
    }
    ILocaleFetcher lf = r.find(Statics.TAG_LOCALE_FETCHER);
    if(null==lf){
      return get(LocaleFactory.locale,key);
    }
    return get(lf.getLocale(),key);
  }

  public static String get(Locale locale,String key){
    return get(locale.toString(),key);
  }

  public static String get(String locale,String key){
    if(!res.containsKey(locale)){
      if(res.containsKey(LocaleFactory.locale)){
        return res.get(LocaleFactory.locale).get(key);
      }else{
        return null;
      }
    }
    return res.get(locale).get(key);
  }
}

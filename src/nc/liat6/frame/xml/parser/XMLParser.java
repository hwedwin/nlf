package nc.liat6.frame.xml.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.xml.XmlFormatException;
import nc.liat6.frame.xml.element.IXmlElement;
import nc.liat6.frame.xml.element.XmlList;
import nc.liat6.frame.xml.element.XmlMap;
import nc.liat6.frame.xml.element.XmlString;
import nc.liat6.frame.xml.element.XmlType;

/**
 * XML解析器
 * <p>
 * 不遵循以下约定的XML可能会解析失败：
 * <ul>
 * <li>子节点为属性的，父节点建议设置type="bean"属性。</li>
 * <li>子节点为数组的，父节点必须设置type="list"属性。</li>
 * <li>字符串中包含&lt;和&gt;必须使用&lt;![CDATA[和]]&gt;包装。</li>
 * </ul>
 * </p>
 * 
 * @author 6tail
 * 
 */
public class XMLParser{

  /** CDATA起始符 */
  public static final String CDATA_PREFIX = "![CDATA[";
  /** CDATA结束符 */
  public static final String CDATA_SUFFIX = "]]";
  /** 注释起始符 */
  public static final String ANNO_PREFIX = "!--";
  /** 注释结束符 */
  public static final String ANNO_SUFFIX = "--";
  /** BEAN节点类型 */
  public static final int TYPE_BEAN = 1;
  /** LIST节点类型 */
  public static final int TYPE_LIST = 2;
  /** 其他节点类型（字符串） */
  public static final int TYPE_OTHER = 0;
  /** 节点缓存栈 */
  private List<IXmlElement> stack = new ArrayList<IXmlElement>();
  /** 当前字符 */
  private int c;
  /** 待解析的字符串 */
  private String orgs;
  /** 字符读取器 */
  private Reader reader;
  /** 注释 */
  private String note;

  /**
   * 将XML字符串转换为对象
   * 
   * @param s XML字符串
   * @return 对象
   */
  public IXmlElement parse(String s){
    orgs = s;
    if(null==s){
      return null;
    }
    s = s.trim();
    s = s.substring(s.indexOf("<"));
    reader = new StringReader(s);
    while(-1!=c){
      parseElement();
    }
    return stack.get(0);
  }

  private void parseElement(){
    skip();
    switch(c){
      case '<':// 对象开始
        parseNode();
        break;
      default:
        String s = readUntil('<');
        IXmlElement p = stack.get(stack.size()-1);
        try{
          p.toXmlString().set(s.replace("&lt;","<").replace("&gt;",">"));
        }catch(Exception e){
          throw new XmlFormatException(s);
        }
        break;
    }
  }

  private void parseNode(){
    next();
    switch(c){
      case '?':
        readUntil('>');
        next();
        break;
      case '!':
        String s = readUntil('>').trim();
        String us = s.toUpperCase();
        if(us.startsWith(CDATA_PREFIX)){// 处理CDATA
          while(!us.endsWith(CDATA_SUFFIX)){
            next();
            if(-1==c){
              throw new XmlFormatException(L.get("xml.cdata_not_end")+"\r\n"+orgs);
            }
            us = readUntil('>');
            s += ">"+us;
            us = us.toUpperCase();
          }
          s = s.substring(CDATA_PREFIX.length());
          s = s.substring(0,s.length()-CDATA_SUFFIX.length());
          if(stack.size()>0){
            IXmlElement p = stack.get(stack.size()-1);
            p.toXmlString().set(s);
          }
        }else if(us.startsWith(ANNO_PREFIX)){// 处理注释
          StringBuffer n = new StringBuffer();// 注释
          n.append(s);
          while(!us.endsWith(ANNO_SUFFIX)){
            next();
            if(-1==c){
              throw new XmlFormatException(L.get("xml.note_not_end")+"\r\n"+orgs);
            }
            us = readUntil('>');
            n.append(us);
            us = us.toUpperCase();
          }
          note = n.toString();
          note = note.substring(ANNO_PREFIX.length());
          note = note.substring(0,note.length()-ANNO_SUFFIX.length());
        }
        next();
        break;
      case '/':
        next();
        String tag = readUntil('>');
        next();
        if(stack.size()<2){
          break;
        }
        // 最后一个节点
        IXmlElement el = stack.remove(stack.size()-1);
        IXmlElement p = stack.get(stack.size()-1);
        switch(p.type()){
          case LIST:
            p.toXmlList().add(el);
            break;
          case MAP:
            IXmlElement xe = p.toXmlMap().get(tag);
            if(null!=xe){
              String tagName = p.getName();
              String n = p.getNote();
              XmlList xl = new XmlList();
              Map<String,String> attrs = p.getAttributes();
              xl.setName(tagName);
              xl.setNote(n);
              xl.setAttributes(attrs);
              xl.add(xe);
              xl.add(el);
              stack.set(stack.size()-1,xl);
            }
            p.toXmlMap().set(tag,el);
            break;
          default:
            if(XmlType.STRING.equals(p.type())){
              String tagName = p.getName();
              String n = p.getNote();
              Map<String,String> attrs = p.getAttributes();
              p = new XmlMap();
              p.setName(tagName);
              p.setNote(n);
              p.setAttributes(attrs);
              stack.set(stack.size()-1,p);
            }
            p.toXmlMap().set(tag,el);
        }
        break;
      default:
        String startTag = readUntil('>');
        next();
        startTag = startTag.trim();
        if(startTag.endsWith("/")){
          break;
        }
        Map<String,String> attrs = new HashMap<String,String>();
        if(startTag.contains(" ")){
          String nodeName = Stringer.cut(startTag,""," ");
          String attr = Stringer.cut(startTag," ");
          int type = TYPE_OTHER;
          while(attr.contains("=\"")){
            String attrName = Stringer.cut(attr,"","=\"").trim();
            String attrValue = Stringer.cut(attr,"=\"","\"").trim();
            attrs.put(attrName,attrValue);
            attr = Stringer.cut(attr,"=\"");
            attr = Stringer.cut(attr,"\"");
            if("type".equals(attrName)){
              if("bean".equalsIgnoreCase(attrValue)){
                type = TYPE_BEAN;
              }else if("list".equalsIgnoreCase(attrValue)){
                type = TYPE_LIST;
              }
            }
          }
          switch(type){
            case TYPE_BEAN:
              XmlMap xm = new XmlMap();
              if(null!=note){
                xm.setNote(note);
                note = null;
              }
              xm.setName(nodeName);
              xm.setAttributes(attrs);
              stack.add(xm);
              break;
            case TYPE_LIST:
              XmlList xl = new XmlList();
              if(null!=note){
                xl.setNote(note);
                note = null;
              }
              xl.setName(nodeName);
              xl.setAttributes(attrs);
              stack.add(xl);
              break;
            case TYPE_OTHER:
              XmlString xs = new XmlString();
              if(null!=note){
                xs.setNote(note);
                note = null;
              }
              xs.setName(nodeName);
              xs.setAttributes(attrs);
              stack.add(xs);
              break;
          }
        }else{
          XmlString xs = new XmlString();
          if(null!=note){
            xs.setNote(note);
            note = null;
          }
          xs.setName(startTag);
          xs.setAttributes(attrs);
          stack.add(xs);
        }
    }
  }

  /**
   * 一直读取，直到遇到指定字符，不包括指定字符
   * 
   * @param endTag 指定字符
   * @return 读取到的字符串
   */
  private String readUntil(int endTag){
    StringBuilder s = new StringBuilder();
    while(-1!=c&&endTag!=c){
      s.append((char)c);
      next();
    }
    return s.toString();
  }

  /**
   * 读取下一个字符
   */
  private void next(){
    try{
      c = reader.read();
    }catch(IOException e){
      throw new NlfException(e);
    }
  }

  /**
   * 跳过无意义字符和注释
   */
  private void skip(){
    if(-1==c)
      return;
    if(0<=c&&32>=c){ // 忽略0到32之间的
      next();
      skip();
    }
    if(127==c||'\r'==c||'\n'==c){ // 忽略DEL及回车换行
      next();
      skip();
    }
  }
}
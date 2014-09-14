package nc.liat6.frame.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.util.Pair;

/**
 * 分页当前页数据封装
 * 
 * @author 6tail
 * 
 */
public class PageData implements Serializable{

  private static final long serialVersionUID = -5088644434609874737L;
  /** 每页记录数 */
  private int pageSize;
  /** 总记录数 */
  private int recordCount;
  /** 第几页 */
  private int pageNumber;
  /** 排序字段，值为ASC或DESC，大写，如NAME=ASC */
  private List<Pair> sorts = new ArrayList<Pair>();
  /** 该页数据 */
  private List<?> data;

  public PageData(){}

  public PageData(List<?> data,int pageSize,int pageNumber,int recordCount){
    setData(data);
    setPageSize(pageSize);
    setPageNumber(pageNumber);
    setRecordCount(recordCount);
  }

  /**
   * 获取每页记录数
   * 
   * @return 每页记录数
   */
  public int getPageSize(){
    return pageSize;
  }

  /**
   * 设置每页记录数
   * 
   * @param pageSize 每页记录数
   */
  public void setPageSize(int pageSize){
    this.pageSize = pageSize<1?1:pageSize;
  }

  /**
   * 获取总记录数
   * 
   * @return 总记录数
   */
  public int getRecordCount(){
    return recordCount;
  }

  /**
   * 设置总记录数
   * 
   * @param recordCount 总记录数
   */
  public void setRecordCount(int recordCount){
    this.recordCount = recordCount<0?0:recordCount;
  }

  /**
   * 获取总页数
   * 
   * @return 总页数
   */
  public int getPageCount(){
    return (recordCount<1||pageSize<1)?1:(int)Math.ceil(recordCount*1D/pageSize);
  }

  /**
   * 获取前一页页码
   * 
   * @return 前一页页码
   */
  public int getPreviousPageNumber(){
    return pageNumber-1<1?1:pageNumber-1;
  }

  /**
   * 获取后一页页码
   * 
   * @return 页码
   */
  public int getNextPageNumber(){
    return pageNumber+1>getPageCount()?getPageCount():pageNumber+1;
  }

  /**
   * 获取第一页页码
   * 
   * @return 页码
   */
  public int getFirstPageNumber(){
    return 1;
  }

  /**
   * 获取最后页页码
   * 
   * @return 页码
   */
  public int getLastPageNumber(){
    return getPageCount();
  }

  /**
   * 获取相邻的页码
   * 
   * @param size 页码个数
   * @return 相邻的页码数组
   */
  public int[] getNearPageNumbers(int count){
    int start = pageNumber-count;
    int end = pageNumber+count;
    start = start<1?1:start;
    end = end>getPageCount()?getPageCount():end;
    int[] m = new int[end+1-start];
    for(int i = 0;i<m.length;i++){
      m[i] = start+i;
    }
    return m;
  }

  /**
   * 获取该页数据条数
   * 
   * @return 该页数据条数
   */
  public int getSize(){
    return null==data?0:data.size();
  }

  /**
   * 获取该页页码
   * 
   * @return 页码
   */
  public int getPageNumber(){
    return pageNumber;
  }

  /**
   * 设置该页页码
   * 
   * @param pageNumber 页码
   */
  public void setPageNumber(int pageNumber){
    this.pageNumber = pageNumber<1?1:pageNumber;
  }

  /**
   * 获取该页数据
   * 
   * @return 该页数据
   */
  public List<?> getData(){
    return data;
  }

  /**
   * 设置该页数据
   * 
   * @param data 该页数据
   */
  public void setData(List<?> data){
    this.data = data;
  }

  /**
   * 获取指定Bean
   * 
   * @param index 索引
   * @return Bean
   */
  public Bean getBean(int index){
    return (Bean)data.get(index);
  }

  /**
   * 获取排序键值对列表
   * 
   * @return 排序键值对列表
   */
  public List<Pair> getSorts(){
    return sorts;
  }

  /**
   * 设置排序键值对列表
   * 
   * @param sorts 排序键值对列表
   */
  public void setSorts(List<Pair> sorts){
    this.sorts = sorts;
  }

  /**
   * 获取排序中某列的值
   * 
   * @param column 列键名
   * @return 值
   */
  public String getSort(String column){
    for(Pair p:sorts){
      if(null!=p&&null!=p.getKey()&&p.getKey().equals(column)){
        return p.getValue();
      }
    }
    return null;
  }

  /**
   * 添加排序键值对
   * 
   * @param key 键
   * @param value 值
   */
  public void addSort(String key,String value){
    if(null!=key){
      sorts.add(new Pair(key,value));
    }
  }

  /**
   * 清空排序键值对
   */
  public void clearSorts(){
    sorts.clear();
  }

  /**
   * 获取键值对的字符串表示
   * 
   * @return 键值对的字符串表示，多键之间以分号间隔，键值之间以冒号间隔
   */
  public String getSortsAsString(){
    StringBuilder s = new StringBuilder();
    int n = 0;
    for(Pair p:sorts){
      if(n>0){
        s.append(";");
      }
      s.append(p.getKey());
      s.append(":");
      s.append(p.getValue());
      n++;
    }
    return s.toString();
  }
}
package nc.liat6.frame.context;

/**
 * 上下文
 * 
 * @author liat6
 * 
 */
public class Context{

  /** 上下文变量 */
  private final static ThreadLocal<Var> VAR = new ThreadLocal<Var>(){

    public Var initialValue(){
      return new Var();
    }
  };

  protected Context(){}

  /**
   * 设置变量
   * 
   * @param k 键
   * @param v 值
   */
  public synchronized static void set(Object k,Object v){
    VAR.get().set(k,v);
  }

  /**
   * 获取变量
   * 
   * @param k 键
   * @return 值
   */
  @SuppressWarnings("unchecked")
  public synchronized static <T>T get(Object k){
    return (T)VAR.get().get(k);
  }

  /**
   * 移除变量
   * 
   * @param k 键
   */
  public synchronized static void remove(Object k){
    VAR.get().remove(k);
  }

  /**
   * 是否存在变量
   * 
   * @param k 键
   * @return 值
   */
  public static boolean contains(Object k){
    return VAR.get().contains(k);
  }

  /**
   * 清空上下文变量
   */
  public synchronized static void clear(){
    VAR.get().clear();
  }
}
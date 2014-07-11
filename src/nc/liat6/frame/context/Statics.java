package nc.liat6.frame.context;

/**
 * 常量
 * 
 * @author 6tail
 * 
 */
public class Statics{

  private Statics(){}
  /** 字符编码 */
  public final static String ENCODE = "UTF-8";
  /** 响应封装 */
  public final static String RESPONSE = "NLF_RESPONSE";
  /** 请求封装 */
  public final static String REQUEST = "NLF_REQUEST";
  /** 物理路径标识 */
  public final static String REAL_PATH_TAG = "REAL_PATH";
  /** 默认应用根路径标识 */
  public final static String DEFAULT_APP_ROOT_TAG = "PATH";
  /** 默认连接别名 */
  public final static String DEFAULT_CONNECTION_ALIAS = "NLF_DEFAULT_CONNECTION_ALIAS";
  /** 连接 */
  public final static String CONNECTIONS = "NLF_CONNECTIONS";
  /** 用于获取原始Request的标识符 */
  public static final String TAG_ORG_REQUEST = "request";
  /** 用于获取IP获取器的标识符 */
  public static final String TAG_IP_FETCHER = "ipfetcher";
  /** 用于获取locale获取器的标识符 */
  public static final String TAG_LOCALE_FETCHER = "localefetcher";
}

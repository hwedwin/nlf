package nc.liat6.frame.rmi.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import nc.liat6.frame.Factory;
import nc.liat6.frame.Version;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * 远程调用服务
 * <p>
 * 启动服务端调用：NlfServer.getInstance().start();
 * </p>
 * 
 * @author 6tail
 * 
 */
public class NlfServer implements Runnable{

  /** 是否开启服务日志 */
  static boolean enableLog = true;
  /** 请求标识 */
  static final String REQ_TAG = "nlfreq";
  /** 响应标识 */
  static final String RSP_TAG = "nlfrsp";
  /** 配置文件名称 */
  public static final String SETTING_FILE_NAME = "server";
  /** 默认端口 */
  public static final int DEFAULT_PORT = 9999;
  /** 默认线程池大小 */
  public static final int DEFAULT_POOL_SIZE = 100;
  /** 默认缓冲区大小 */
  public static final int DEFAULT_BUFFER_SIZE = 40960;
  /** 端口 */
  public static int PORT = DEFAULT_PORT;
  /** 线程池大小 */
  public static int POOL_SIZE = DEFAULT_POOL_SIZE;
  /** 缓冲区大小 */
  public static int BUFFER_SIZE = DEFAULT_BUFFER_SIZE;
  /** 实例 */
  private static NlfServer instance = null;
  /** 日志 */
  private static ILog log = Logger.getLog();
  /** 线程池 */
  private ExecutorService pool;
  /** 监听socket */
  private ServerSocket serverSocket;
  /** 监听线程 */
  private Thread t = null;
  /** 允许访问的路径 */
  public static final List<String> allow = new ArrayList<String>();
  /** 禁止访问的路径 */
  public static final List<String> forbid = new ArrayList<String>();

  private NlfServer(){
    try{
      serverSocket = new ServerSocket(PORT);
      serverSocket.setSoTimeout(0);
      pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
    }catch(IOException e){
      throw new NlfException(e);
    }
  }

  private static void init(){
    File f = new File(Factory.APP_PATH,SETTING_FILE_NAME);
    if(!f.exists()||f.isDirectory()){
      return;
    }
    Bean o;
    try{
      o = JSON.toBean(Stringer.readFromFile(f,"utf-8"));
    }catch(IOException e){
      throw new NlfException(e);
    }
    PORT = o.getInt("port",DEFAULT_PORT);
    POOL_SIZE = o.getInt("pool_size",DEFAULT_POOL_SIZE);
    BUFFER_SIZE = o.getInt("buffer_size",DEFAULT_BUFFER_SIZE);
    List<String> lallow = o.get("allow");
    if(null!=lallow){
      allow.addAll(lallow);
    }
    List<String> lforbid = o.get("forbid");
    if(null!=lforbid){
      forbid.addAll(lforbid);
    }
    forbid.add(Version.PACKAGE);
  }
  static{
    init();
  }

  public synchronized static NlfServer getInstance(){
    if(null==instance){
      instance = new NlfServer();
    }
    return instance;
  }

  public void start(){
    if(null==t){
      t = new Thread(this);
      t.start();
    }
    log.debug(L.get(LocaleFactory.locale,"rmi.app_start")+"\r\n\t"+L.get(LocaleFactory.locale,"rmi.port")+PORT+"\r\n\t"+L.get("rmi.threads")+POOL_SIZE+"\r\n\t"+L.get(LocaleFactory.locale,"rmi.buffer")+BUFFER_SIZE);
  }

  public void run(){
    while(null!=t&&t.isAlive()){
      Socket socket = null;
      try{
        socket = serverSocket.accept();
        if(enableLog){
          log.debug(Stringer.print("??:?",L.get(LocaleFactory.locale,"rmi.new_client"),socket.getInetAddress(),socket.getPort()));
        }
        pool.execute(new Handler(socket));
      }catch(Exception e){
        if(null!=socket){
          try{
            socket.close();
          }catch(IOException ex){}
        }
      }
      try{
        Thread.sleep(10);
      }catch(InterruptedException e){}
    }
  }

  public void nolog(){
    enableLog = false;
  }
}

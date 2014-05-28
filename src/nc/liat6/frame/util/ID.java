package nc.liat6.frame.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 记录ID生成器
 * 
 * <p>
 * Created on 2010-04-01
 * </p>
 * 
 * @author 六特尔
 * @version 1.0
 */
public class ID{

	/** 每秒自增序列 */
    private static int serial = 0;

    /** 当前时间字符串 */
    private static String time = "";

    /** 自增序列位数 */
    private static final int DIGIT = 3;
    
    private ID(){}

    /**
     * 获取一个新的不重复的ID
     * 
     * @return 长整型数字
     */
    public synchronized static BigDecimal next(){
        String s = "";
        String t = new SimpleDateFormat("yyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        if(!t.equals(time)){
            time = t;
            serial = (int)Math.round(Math.random()*5);
        }
        int max = (int) Math.pow(10, DIGIT);
        if(++serial >= max){
        	try{
        		Thread.sleep(1000L);
        	}catch(InterruptedException e){}
        	return next();
        }
        s += serial;
        while(s.length() < DIGIT){
            s = "0" + s;
        }
        long a =Long.parseLong(time + s);
        return  BigDecimal.valueOf(a);
    }
}
package nc.liat6.frame.execute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.exception.BadException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.paging.PagingParam;
import nc.liat6.frame.util.Pair;
import nc.liat6.frame.util.Stringer;

/**
 * 请求
 * 
 * @author 6tail
 * 
 */
public class Request{

	/** 分页页码标识 */
	public static final String PAGE_NUMBER_VAR = "nlfPagingNumber";

	/** 分页每页大小标识 */
	public static final String PAGE_SIZE_VAR = "nlfPagingSize";

	/** 分页参数标识 */
	public static final String PAGE_PARAM_VAR = "nlfPagingParam";

	/** 排序参数标识 */
	public static final String PAGE_SORT_VAR = "nlfSort";

	/** 默认分页大小 */
	public static final int DEFAULT_PAGE_SIZE = 20;

	/** 请求的参数 */
	private Map<String,String> params = new HashMap<String,String>();

	/** 绑定变量 */
	private Map<String,Object> bundle = new HashMap<String,Object>();

	/** 分页参数 */
	private PagingParam pagingParam;

	/** 客户端类型：电脑 */
	public static final int CLIENT_TYPE_COMPUTER = 0;

	/** 客户端类型：移动设备 */
	public static final int CLIENT_TYPE_MOBILE = 1;

	/** 客户端类型 */
	private int clientType = CLIENT_TYPE_COMPUTER;
	
	/**
	 * 获取客户端类型
	 * @return 客户端类型
	 */
	public int getClientType(){
		return clientType;
	}

	/**
	 * 设置客户端类型
	 * @param clientType 客户端类型
	 */
	public void setClientType(int clientType){
		this.clientType = clientType;
	}

	/**
	 * 设置参数
	 * 
	 * @param params 参数
	 */
	public void setParams(Map<String,String> params){
		this.params = params;
	}

	/**
	 * 设置参数
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void setParam(String key,String value){
		params.put(key,value);
	}

	/**
	 * 绑定变量
	 * 
	 * @param key 键
	 * @param value 值
	 */
	public void bind(String key,Object value){
		bundle.put(key,value);
	}

	/**
	 * 获取绑定变量
	 * 
	 * @param key 键
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public <T>T find(String key){
		return (T)bundle.get(key);
	}

	/**
	 * 参数转换为Bean，参数全部去两端空格
	 * 
	 * @return Bean
	 */
	public Bean toBean(){
		return toBean(true);
	}

	/**
	 * 参数转换为Bean
	 * 
	 * @param trim 参数是否去两端空格
	 * @return Bean
	 */
	public Bean toBean(boolean trim){
		Bean o = new Bean();
		for(String key:params.keySet()){
			o.set(key,get(key,trim));
		}
		o.remove("_timestamp");
		return o;
	}

	/**
	 * 参数转换为对象，参数全部去两端空格
	 * 
	 * @param klass 类
	 * @return 对象
	 */
	public <T>T toObject(Class<?> klass){
		return toObject(klass,true);
	}

	/**
	 * 参数转换为对象
	 * 
	 * @param klass 类
	 * @param trim 参数是否全部去两端空格
	 * @return 对象
	 */
	public <T>T toObject(Class<?> klass,boolean trim){
		return toBean(trim).toObject(klass);
	}

	/**
	 * 获取去掉两端空格后的参数值
	 * 
	 * @param key 键
	 * @return 值
	 */
	public String get(String key){
		return get(key,true);
	}

	/**
	 * 获取参数值
	 * 
	 * @param key 键
	 * @param trim 值
	 * @return 是否去掉两端空格
	 */
	public String get(String key,boolean trim){
		String value = params.get(key);
		value = null == value?"":value;
		value = trim?value.trim():value;
		return value;
	}

	/**
	 * 获取整数参数值
	 * 
	 * @param key 参数名
	 * @return 参数值
	 */
	public int getInt(String key){
		String v = get(key);
		try{
			return Integer.parseInt(v);
		}catch(Exception e){
			throw new BadException(Stringer.print("??",L.get("valiadtor.integer"),key));
		}
	}

	/**
	 * 获取长整型参数值
	 * 
	 * @param key 参数名
	 * @return 参数值
	 */
	public long getLong(String key){
		String v = get(key);
		try{
			return Long.parseLong(v);
		}catch(Exception e){
			throw new BadException(Stringer.print("??",L.get("valiadtor.long_integer"),key));
		}
	}

	/**
	 * 获取数值型参数值
	 * 
	 * @param key 参数名
	 * @return 参数值
	 */
	public double getDouble(String key){
		String v = get(key);
		try{
			return Double.parseDouble(v);
		}catch(Exception e){
			throw new BadException(Stringer.print("??",L.get("valiadtor.double"),key));
		}
	}

	/**
	 * 获取浮点型参数值
	 * 
	 * @param key 参数名
	 * @return 参数值
	 */
	public float getFloat(String key){
		String v = get(key);
		try{
			return Float.parseFloat(v);
		}catch(Exception e){
			throw new BadException(Stringer.print("??",L.get("valiadtor.float"),key));
		}
	}

	/**
	 * 获取布尔型参数值
	 * 
	 * @param key 参数名
	 * @return 参数值
	 */
	public boolean getBoolean(String key){
		String v = get(key);
		try{
			return Boolean.parseBoolean(v);
		}catch(Exception e){
			throw new BadException(Stringer.print("??",L.get("valiadtor.bool"),key));
		}
	}

	/**
	 * 获取所有参数
	 * 
	 * @return 所有参数
	 */
	public Map<String,String> getParams(){
		return getParams(true);
	}

	/**
	 * 获取所有参数
	 * 
	 * @param trim 是否去掉两端空格
	 * @return 所有参数
	 */
	public Map<String,String> getParams(boolean trim){
		Map<String,String> ps = new HashMap<String,String>();
		for(String key:params.keySet()){
			ps.put(key,get(key,trim));
		}
		return ps;
	}

	/**
	 * 获取当前页页码
	 * 
	 * @return 当前页页码
	 */
	public int getPageNumber(){
		try{
			return Integer.parseInt(pagingParam.getParam(PAGE_NUMBER_VAR));
		}catch(Exception e){
			return 1;
		}
	}

	/**
	 * 获取分页大小
	 * 
	 * @return 分页大小
	 */
	public int getPageSize(){
		try{
			return Integer.parseInt(pagingParam.getParam(PAGE_SIZE_VAR));
		}catch(Exception e){
			return DEFAULT_PAGE_SIZE;
		}
	}

	/**
	 * 获取表头排序项列表
	 * 
	 * @return 列表，即使为空也不会返回null
	 */
	public List<Pair> getSorts(){
		List<Pair> l = new ArrayList<Pair>();
		String s = pagingParam.getParam(PAGE_SORT_VAR);
		if(null == s)
			return l;
		s = s.trim();
		if("".equals(s))
			return l;
		if(!s.contains(":"))
			return l;
		if(s.contains(";")){
			String[] ps = s.split(";",-1);
			for(String psit:ps){
				String[] pt = psit.split(":",-1);
				l.add(new Pair(pt[0],pt[1]));
			}
		}else{
			String[] pt = s.split(":",-1);
			l.add(new Pair(pt[0],pt[1]));
		}
		return l;
	}

	/**
	 * 获取分页参数
	 * 
	 * @return 分页参数
	 */
	public PagingParam getPagingParam(){
		return pagingParam;
	}

	/**
	 * 设置分页参数
	 * 
	 * @param pagingParam 分页参数
	 */
	public void setPagingParam(PagingParam pagingParam){
		this.pagingParam = pagingParam;
	}

}

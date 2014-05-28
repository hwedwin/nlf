package nc.liat6.frame.execute.impl;

import java.util.Map;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.IExecute;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.Response;

/**
 * 执行抽象
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractExecute implements IExecute{

	/** 用于获取上下文中存放的返回值的标识符 */
	public static final String EXECUTE_RETURN = "NLF_EXECUTE_RETURN";
	
	/** 用于获取上下文中存放的参数的标识符 */
	public static final String EXECUTE_ARGS = "NLF_EXECUTE_ARGS";

	public void begin(){
		Request req = new Request();
		Response res = new Response();
		Map<String,String> args = Context.get(EXECUTE_ARGS);
		req.setParams(args);
		Context.set(Statics.REQUEST,req);
		Context.set(Statics.RESPONSE,res);
		request();
	}

	public void end(){
		response();
		Context.remove(EXECUTE_ARGS);
		Context.remove(EXECUTE_RETURN);
	}

	/**
	 * 请求
	 */
	public abstract void request();

	/**
	 * 响应
	 */
	public abstract void response();

}

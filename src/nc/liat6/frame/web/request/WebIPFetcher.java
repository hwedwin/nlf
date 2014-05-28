package nc.liat6.frame.web.request;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.request.IIPFetcher;
import nc.liat6.frame.web.WebExecute;

/**
 * WEB应用IP获取器，在确实需要用到IP的时候才解析
 * @author 6tail
 *
 */
public class WebIPFetcher implements IIPFetcher{
	
	/** 当前请求 */
	private Request request;
	/** 缓存IP */
	private String ip;
	/** 当前请求是否请求过IP */
	private boolean ipFetched = false;
	
	/** 代理标识 */
	public static final String[] IP_HEADER = {
		"X-Real-IP",
		"X-Forwarded-For",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP",
		"HTTP_CLIENT_IP",
		"HTTP_X_FORWARDED_FOR"
	};
	
	public WebIPFetcher(Request request){
		this.request = request;
	}
	
	public String getIP(){
		if(ipFetched){
			return ip;
		}
		HttpServletRequest oreq = request.find(WebExecute.TAG_REQUEST);
		String r = oreq.getRemoteAddr();
		Enumeration<String> em = oreq.getHeaderNames();
		out:while(em.hasMoreElements()){
			String k = em.nextElement();
			for(String s:IP_HEADER){
				if(s.equalsIgnoreCase(k)){
					String p = oreq.getHeader(k);
					if(null!=p&&p.length()>0&&!"unknown".equalsIgnoreCase(p)){
						r = p;
						break out;
					}
				}
			}
		}
		if(null!=r){
			if(r.indexOf(",")>-1){
				String[] rs = r.split(",");
				for(String s:rs){
					if(s.length()>0&&!"unknown".equalsIgnoreCase(s)){
						r = s;
						break;
					}
				}
			}
			if("0:0:0:0:0:0:0:1".equals(r)){
				r = "127.0.0.1";
			}
		}
		ip = r;
		ipFetched = true;
		return r;
	}

}

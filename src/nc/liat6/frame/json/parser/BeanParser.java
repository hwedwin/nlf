package nc.liat6.frame.json.parser;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.element.IJsonElement;
import nc.liat6.frame.json.element.JsonList;
import nc.liat6.frame.json.element.JsonMap;

/**
 * 对象解析器，用于将IJsonElement转换为Bean
 * @author 6tail
 *
 */
public class BeanParser{
	
	/**
	 * 解析Map类型
	 * @param jm
	 * @return
	 */
	private Bean genMap(JsonMap jm){
		Bean bean = new Bean();
		for(String key:jm.keySet()){
			IJsonElement ve = jm.get(key);
			if(null==ve){
				bean.set(key,null);
				continue;
			}
			switch(ve.type()){
				case STRING:
					bean.set(key,ve.toJsonString().toString(),ve.getNote());
					break;
				case NUMBER:
					bean.set(key,ve.toJsonNumber().value(),ve.getNote());
					break;
				case BOOL:
					bean.set(key,ve.toJsonBool().value(),ve.getNote());
					break;
				case MAP:
					bean.set(key,genMap(ve.toJsonMap()),ve.getNote());
					break;
				case LIST:
					bean.set(key,genList(ve.toJsonList()),ve.getNote());
					break;
			}
		}
		return bean;
	}

	/**
	 * 解析List类型
	 * @param jm
	 * @return
	 */
	private List<Object> genList(JsonList jm){
		List<Object> l = new ArrayList<Object>();
		for(int i = 0;i < jm.size();i++){
			IJsonElement ve = jm.get(i);
			if(null==ve){
				l.add(null);
			}
			switch(ve.type()){
				case STRING:
					l.add(ve.toJsonString().toString());
					break;
				case NUMBER:
					l.add(ve.toJsonNumber().value());
					break;
				case BOOL:
					l.add(ve.toJsonBool().value());
					break;
				case MAP:
					l.add(genMap(ve.toJsonMap()));
					break;
				case LIST:
					l.add(genList(ve.toJsonList()));
					break;
			}
		}
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parse(IJsonElement ve){
		if(null==ve){
			return null;
		}
		switch(ve.type()){
			case STRING:
				return (T)(ve.toJsonString().toString());
			case NUMBER:
				 return (T)(ve.toJsonNumber().value());
			case BOOL:
				return (T)(new Boolean(ve.toJsonBool().value()));
			case MAP:
				return (T)(genMap(ve.toJsonMap()));
			case LIST:
				return (T)(genList(ve.toJsonList()));
		}
		return null;
	}

}

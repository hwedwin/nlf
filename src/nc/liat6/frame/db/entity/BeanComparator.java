package nc.liat6.frame.db.entity;

import java.util.Comparator;
import java.util.List;

import nc.liat6.frame.util.Stringer;

/**
 * BEAN比较器
 * 
 * @author 6tail
 * 
 */
public class BeanComparator implements Comparator<Bean>{

	/** 默认排序 */
	public static final int TYPE_DEFAULT = 0;

	/** ASC排序 */
	public static final int TYPE_ASC = 1;

	/** DESC排序 */
	public static final int TYPE_DESC = 2;

	/** keys指定排序，例如col0:asc,col1:desc */
	public static final int TYPE_MANU = 3;

	/** 排序方式 */
	private int type = TYPE_DEFAULT;

	private List<String> keys;

	public BeanComparator(int type,List<String> keys){
		this.type = type;
		this.keys = keys;
	}

	public int compare(Bean o1,Bean o2){
		if(null == keys || keys.size() < 1){
			return 0;
		}
		switch(type){
			case TYPE_ASC:
				for(String k:keys){
					int n = o1.getString(k,"").compareTo(o2.getString(k,""));
					if(0 == n){
						continue;
					}else{
						return n;
					}
				}
				break;
			case TYPE_DESC:
				for(String k:keys){
					int n = o2.getString(k,"").compareTo(o1.getString(k,""));
					if(0 == n){
						continue;
					}else{
						return n;
					}
				}
				break;
			case TYPE_MANU:
				for(String k:keys){
					if(k.indexOf(":")<0){
						continue;
					}
					String key = Stringer.cut(k,"",":");
					String type = Stringer.cut(k,":");
					if("ASC".equalsIgnoreCase(type)){
						int n = o1.getString(key,"").compareTo(o2.getString(key,""));
						if(0 == n){
							continue;
						}else{
							return n;
						}
					}else if("DESC".equalsIgnoreCase(type)){
						int n = o2.getString(key,"").compareTo(o1.getString(key,""));
						if(0 == n){
							continue;
						}else{
							return n;
						}
					}
				}
				break;
		}
		return 0;
	}

}

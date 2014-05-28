package nc.liat6.frame.db.entity;

import java.util.Comparator;
import java.util.List;

/**
 * BEAN±È½ÏÆ÷
 * @author 6tail
 *
 */
public class BeanComparator implements Comparator<Bean>{
	
	public static final int TYPE_DEFAULT = 0;
	
	public static final int TYPE_ASC = 1;
	
	public static final int TYPE_DESC = 2;
	
	private int type = TYPE_DEFAULT;
	
	private List<String> keys;
	
	public BeanComparator(int type,List<String> keys){
		this.type = type;
		this.keys = keys;
	}

	public int compare(Bean o1,Bean o2){
		if(null==keys||keys.size()<1){
			return 0;
		}
		switch(type){
			case TYPE_ASC:
				for(String k:keys){
					int n = o1.getString(k).compareTo(o2.getString(k));
					if(0==n){
						continue;
					}else{
						return n;
					}
				}
				break;
			case TYPE_DESC:
				for(String k:keys){
					int n = o2.getString(k).compareTo(o1.getString(k));
					if(0==n){
						continue;
					}else{
						return n;
					}
				}
				break;
		}
		return 0;
	}

}

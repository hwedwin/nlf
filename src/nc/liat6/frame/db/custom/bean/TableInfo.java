package nc.liat6.frame.db.custom.bean;

public class TableInfo{

	private String name;
	private long lastModified = 0;

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name.toUpperCase();
	}

	public long getLastModified(){
		return lastModified;
	}

	public void setLastModified(long lastModified){
		this.lastModified = lastModified;
	}

}

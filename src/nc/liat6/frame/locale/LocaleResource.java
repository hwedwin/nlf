package nc.liat6.frame.locale;

public class LocaleResource{

	private boolean inJar;
	private String home;
	private String fileName;
	private String locale;

	public String getLocale(){
		return locale;
	}

	public void setLocale(String locale){
		this.locale = locale;
	}

	public boolean isInJar(){
		return inJar;
	}

	public void setInJar(boolean inJar){
		this.inJar = inJar;
	}

	public String getHome(){
		return home;
	}

	public void setHome(String home){
		this.home = home;
	}

	public String getFileName(){
		return fileName;
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

}

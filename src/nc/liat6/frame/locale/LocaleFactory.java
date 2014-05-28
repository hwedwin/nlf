package nc.liat6.frame.locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nc.liat6.frame.Factory;

public class LocaleFactory{

	public static final String FILE_SUFFIX = ".locale";

	public static Locale locale = Locale.getDefault();

	public static final List<LocaleResource> localeResources = new ArrayList<LocaleResource>();

	public static void addResource(LocaleResource res){
		localeResources.add(res);
	}

	static{
		Factory.doNothing();
	}

	public static Locale getLocale(){
		return locale;
	}

	public static void setLocale(Locale locale){
		LocaleFactory.locale = locale;
	}

}

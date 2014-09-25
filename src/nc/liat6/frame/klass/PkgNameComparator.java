package nc.liat6.frame.klass;

import java.util.Comparator;

/**
 * 包名比较器，按包名比较，越短的越小的越前
 *
 * @author 6tail
 *
 */
public class PkgNameComparator implements Comparator<String>{

  public int compare(String o1,String o2){
    int n = o1.length()-o2.length();
     return 0==n?o1.compareTo(o2):n;
  }
}

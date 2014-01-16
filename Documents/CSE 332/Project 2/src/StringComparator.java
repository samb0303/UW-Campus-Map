
public class StringComparator implements Comparator<String> {

	
	public int compare(String e1, String e2) {
		int i = 0;
		while (i < e1.length() && i < e2.length()) {
			if (e1.charAt(i) < e2.charAt(i)) {
				return -1;
			} else if (e1.charAt(i) > e2.charAt(i)) {
				return 1;
			}
			i++;
		}
		return e1.length() - e2.length();
	}



}


public class StringHasher implements Hasher<String>{
		
	public int hash(String str){
		int hashVal = 0;
		
		for(int i = 0; i < str.length(); i++){
			hashVal += str.charAt(i);
		} 
		return hashVal;
	}
}

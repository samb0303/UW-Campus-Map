import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class WordCountTest {

	Comparator<Integer> c;
	
	@Before
	public void setUp() throws Exception {
		c = new Comparator<Integer>(){
			public int compare(Integer e1, Integer e2){
				return e1 - e2;
			}
		};
	}
	
	@Test
	public void testifSorted() {
		Integer[] array = {
				1, 3, 30, 33, 5, 8, 4, 2
		};
	
		WordCount.mergesort(array, c);
		Integer[] temp = {
				1, 2, 3, 4, 5, 8, 30, 33
		};
		for(int i = 0; i < temp.length; i++){
			assertSame(temp[i], array[i]);
		}
	}
}

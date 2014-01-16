import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class StringComparatorTest {
	
	StringComparator s;
	
	@Before
	public void setUp() throws Exception {
		s = new StringComparator();
	}
	
	@Test
	public void testCompareAtoB() {
		int i = s.compare("a", "b");
		assertEquals(-1, i);
	}
	
	@Test
	public void testCompareSimilarStrings(){
		int i = s.compare("thing", "things");
		assertEquals(-1, i);
	}
	
	@Test
	public void testSameString(){
		int i = s.compare("same", "same");
		assertEquals(0, i);
	}
	
	@Test
	public void testEmptyString(){
		int i = s.compare("", "");
		assertEquals(0, i);
	}
	
	@Test
	public void testCompareBtoA(){
		int i = s.compare("b", "a");
		assertEquals(1, i);
	}
	
	@Test
	public void testBigStrings(){
		int i = s.compare("zzzakskfjeigadhfaskdkjriefieosdlenosoejgh", "aaaaaaammmshhiehgiheihadflsdfsdfasndfksadf");
		assertEquals(1, i);
	}
	
	@Test
	public void testEmptyToString(){
		int i = s.compare("", "a");
		assertEquals(-1, i);
	}
	
}

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class HashtableTest {

	Hashtable<String> hashtable;
	
	@Before
	public void setUp() throws Exception {
		hashtable = new Hashtable<String>(new StringComparator(), new StringHasher());
	}
	@Test
	public void tableHasSize0WhenConstructed() {
		assertEquals("Construct a hashtable and check its size", hashtable.getSize(), 0);
	}
	
	private void addAndTestSize(String[] strs, int unique){
		for(int i = 0; i < strs.length; i++)
			hashtable.incCount(strs[i]);
		assertEquals("Added list of strings " + strs + " to table", 
				hashtable.getSize(), unique);
	}
	
	@Test
	public void treeHasSize1AfterAddingA(){
		addAndTestSize(new String[]{"a"}, 1);
	}
	
	@Test
	public void treeHasSize1AfterAdding2A(){
		addAndTestSize(new String[]{"a", "a"}, 1);
	}
	
	@Test
	public void treeHasSize1AfterAdding2AandB(){
		addAndTestSize(new String[]{"a", "a", "b"}, 2);
	}
	
	@Test
	public void treeHasSize5AfterAdding5(){
		addAndTestSize(new String[]{"a","b","s","e","w"}, 5);
	}
	@Test
	public void treeHasSize5AfterAddingLongStrings(){
		addAndTestSize(new String[]{"this","is","a","longgggggggggg","stringggggggggggggggg"}, 5);
	}
	@Test
	public void treeHasSize5AfterAddingSequence(){
		addAndTestSize(new String[]{"a","b","s","e","w","a","b","s","e","w"}, 5);
	}
	
	private void addAndGetCount(String[] str, String getThis, int expected){
		for(int i = 0; i < str.length; i++)
			hashtable.incCount(str[i]);
		int actual = hashtable.getCount(getThis);
		assertEquals("Added " + str + " and got count of " + getThis, 
				expected, actual);
	}
	@Test
	public void treeHas7OfA(){
		addAndGetCount(new String[]{"a", "a", "a", "a", "a", "a", "a"}, "a", 7);
	}
	
	@Test
	public void treeHas7OfAafterInsertingMore(){
		addAndGetCount(new String[]{"a", "a", "b", "w", "coool", "a", "a", "a", "a", "a"}, "a", 7);
	}
	
	@Test
	public void testGetIterator(){
		hashtable.incCount("a");
		hashtable.incCount("b");
		hashtable.incCount("c");
		SimpleIterator<DataCount<String>> iterTest = hashtable.getIterator();
		DataCount<String> count = iterTest.next();
		assertSame("a", count.data);
		assertTrue(iterTest.hasNext());
		count = iterTest.next();
		count = iterTest.next();
		assertFalse(iterTest.hasNext());
	}
}

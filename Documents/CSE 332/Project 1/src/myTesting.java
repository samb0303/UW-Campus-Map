import java.util.*;
public class myTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		ArrayStack as = new ArrayStack();
//		
//		//test for EmptyStackException
//		//as.peek();
//		//as.pop();
//		
//		as.push(1.0);
//		as.push(2.0);
//		as.push(3.0);
//		
//		boolean empty  = as.isEmpty();
//		
//		double n = as.peek();
//		
//		System.out.println(empty + " , " + n );
//		
//		
//		ListStack ls = new ListStack();
//		
//		//ls.pop();
//		//ls.peek();
//		
//		boolean emptyLs = ls.isEmpty();
//		
//		
//		ls.push(1.0);
//		ls.push(2.0);
//		
//		emptyLs = ls.isEmpty();
//		System.out.println(emptyLs);
//		 
//		double y = ls.peek();
//		 double x = ls.pop();
//		
//		 System.out.println("peek= " + y + "," + x);
//	
		ListStack ls = new ListStack();
		ls.push(1.0);
		ls.push(2.0);
		ls.push(3.0);
		double i = ls.pop();
		System.out.println(i);
		ls.push(4.0);
		ls.push(5.0);
		i = ls.pop();
		i = ls.pop();
		boolean empty = ls.isEmpty();
		System.out.println(i);
		
	}

}

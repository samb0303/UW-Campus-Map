//Samantha Brender
//CSE 332 Project 1
//ListStack
//1/16/13

//ListStack provides an implementation of a Stack for doubles using a linked list.		
import java.util.EmptyStackException;


public class ListStack implements DStack{

	private ListNode front;
	private int size;//size of linked list
	
	//constructs a new ListStack with size 0 
	public ListStack(){
		front = new ListNode();
		size = 0;
	}
	
	//isEmpty returns true if the stack is empty and false otherwise
	public boolean isEmpty(){
		return size==0;
	  }
	  
	//push adds the given value to the top of the stack
    public void push(double d){
    	if(front == null){
    		front = new ListNode(d);
    	}
    	else{
    		ListNode newNode = new ListNode(d, front);
    		front = newNode;
    	}
    	size++;
    }

	//pop removes the current value at the top of the stack and returns that value
    //if the stack is empty, it throws a EmptyStackException
    public double pop(){
    	if(isEmpty())
    		throw new EmptyStackException();
    	else{
    		double data = front.val;
    		front = front.next;
    		size--; 
    		return data;
    	}
	}

	//peek returns the current value at the top of the stack
    //if the stack is empty it throws an EmptyStackException
    public double peek(){
    	if(isEmpty())
    		throw new EmptyStackException();	
    	
    	else{
    		return front.val;
    	}
    	
	}
    
    //defines a ListNode. ListNode has a double data value and a link to the next node in the list
  	private class ListNode{
  		
  		private double val;
  		private ListNode next;
  		
  		private ListNode(){
  			val = 0;
  			next = null;
  		}
  		
  		private ListNode(double d){
  			val = d;
  			next = null;
  		}
  		
  		private ListNode(double d, ListNode node){
  			val = d;
  			next = node;
  		}
  		  
  	}  
}

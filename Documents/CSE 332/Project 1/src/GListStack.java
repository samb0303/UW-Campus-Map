//Samantha Brender
//CSE 332 Project 1
//GListStack
//1/22/13

//GListStack provides an implementation of a Generic Stack using a linked list.		
import java.util.EmptyStackException;


public class GListStack<T> implements GStack<T>{

	private ListNode<T> front;
	private int size;//size of linked list
	
	//constructs a new ListStack with size 0 
	public GListStack(){
		front = new ListNode<T>();
		size = 0;
	}
	
	//isEmpty returns true if the stack is empty and false otherwise
	public boolean isEmpty(){
		return size==0;
	  }
	  
	//push adds the given value to the top of the stack
    public void push(T d){
    	if(front == null){
    		front = new ListNode<T>(d);
    	}
    	else{
    		ListNode<T> newNode = new ListNode<T>(d, front);
    		front = newNode;
    	}
    	size++;
    }

	//pop removes the current value at the top of the stack and returns that value
    //if the stack is empty, it throws a EmptyStackException
    public T pop(){
    	if(isEmpty())
    		throw new EmptyStackException();
    	else{
    		T data = front.val;
    		front = front.next;
    		size--; 
    		return data;
    	}
	}

	//peek returns the current value at the top of the stack
    //if the stack is empty it throws an EmptyStackException
    public T peek(){
    	if(isEmpty())
    		throw new EmptyStackException();	
    	
    	else{
    		return front.val;
    	}
    	
	}
    
    //defines a ListNode. ListNode has a generic data value and a link to the next node in the list
  	@SuppressWarnings("hiding")
	private class ListNode<T>{
  		
  		private T val;
  		private ListNode<T> next;
  		
  		private ListNode(){
  			val = null;
  			next = null;
  		}
  		
  		private ListNode(T d){
  			val = d;
  			next = null;
  		}
  		
  		private ListNode(T d, ListNode<T> node){
  			val = d;
  			next = node;
  		}
  		  
  	}  
}

//Samantha Brender
//CSE 332 Project 1
//GArrayStack
//1/22/13

//GArrayStack provides an implementation of a Genenric Stack using an array.		
import java.util.EmptyStackException;


public class GArrayStack<T> implements GStack<T>{
	
		private T[] myArray;
		private int size;//number of elements in myArray 
		
		//constructs an new array of length 10 and size 0
		@SuppressWarnings("unchecked")
		public GArrayStack(){
			myArray = (T[]) new Object[10];
			size = 0;
		}
		
		//isEmpty returns true if the stack is empty and false otherwise
		public boolean isEmpty(){
			return size==0;
		}

	    //push takes the given value and adds it to the top of the stack
	    @SuppressWarnings("unchecked")
		public void push(T d){
	    	
	    	//if the array is full, resizes the array to twice as large
	    	if(size == myArray.length){
	    		T[] newArray = (T[]) new Object[myArray.length * 2];
	    		for(int i = 0; i < myArray.length; i++){
	    			newArray[i] = myArray[i];
	    		}
	    		myArray = newArray;	
	    	}
	    	
	    	myArray[size] = d;
	    	size++;
	    }
	    
	    //pop removes the given value from the stack and then returns it
	    //if the stack is empty, pop throws an EmptyStackException
	    public T pop(){
	    	if(isEmpty())
	    		throw new EmptyStackException();
	    	else{
	    		T i = myArray[size-1];
	    		myArray[size-1] = null;
	    		size--;
	    		return i;
	    	}
	    }

	    //peek returns the current value at the top of the stack
	    //if the stack is empty, peek throws an EmptyStackException
	    public T peek(){
	    	if (isEmpty())
	    		throw new EmptyStackException();
	    	else
	    			return myArray[size-1]; 
	    }
}

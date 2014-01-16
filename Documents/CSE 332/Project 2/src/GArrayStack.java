// Blake Campbell CSE332

// This class implements the Stack data type defined by the
// GStack interface and does so using an array.

import java.util.EmptyStackException;

public class GArrayStack<T> implements GStack<T> {
	private T[] data;
	private int end; //index of the next spot in the array
	
	// constructs a new GArrayStack using an empty array
	@SuppressWarnings("unchecked")
	public GArrayStack() {
		data = (T[]) new Object[10];
		end = 0;
	}
	
	// pushes the given value onto the stack. If the array is full
	// then the array is copied into a new array with double the size
	// and then the new value is pushed onto the stack.
	public void push(T val) {
		if (end == data.length) {
			@SuppressWarnings("unchecked")
			T[] temp = (T[]) new Object[data.length * 2];
			for (int i = 0; i < data.length; i++) {
				temp[i] = data[i];
			}
			data = temp;
		}
		data[end] = val;
		end = end + 1;
	}
	
	// removes the top element of the stack and returns its value.
	// Throws an EmptyStackException if called on an empty stack.
	public T pop() {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}
		end = end - 1;
		return data[end];
	}
	
	// returns whether the stack is empty
	public boolean isEmpty() {
		return end == 0;
	}
	
	// returns the top value of the stack. Throws an
	// EmptyStackException if called on an empty stack.
	public T peek() {
		if (this.isEmpty()) {
			throw new EmptyStackException();
		}
		return data[end - 1];
	}
}

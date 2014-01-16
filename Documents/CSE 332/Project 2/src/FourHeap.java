
//Blake Campbell & Samantha Brender

/**
 * FourHeap implements the PriorityQueue interface using a heap
 * where each node has four children. The heap is implemented using
 * an array.
 */

import java.util.NoSuchElementException;

public class FourHeap<E> implements PriorityQueue<E> {
	private static final int DEFAULT_CAPACITY = 100; //initial capacity of the array
	private E[] data; //array representing the heap
	private int size; //number of nodes in the heap
	private Comparator<? super E> c;
	
	//getter method for size field to use with unit tests
	public int getSize() {
		return size;
	}
	
	//getter method for data field to use with unit tests
	public E[] getData() {
		return data;
	}
	
	//creates a new heap with no elements
	@SuppressWarnings("unchecked")
	public FourHeap(Comparator<? super E> comp) {
		data = (E[]) new Object[DEFAULT_CAPACITY];
		c = comp;
		size = 0;
	}

	// Inserts a new element into the heap
	@Override
	public void insert(E item) {
		if(size == data.length - 1) {
            resize(data.length * 2);
		}
		//percolateUp
        int hole = ++size;
        for(data[0] = item; c.compare(item, data[(hole+2) / 4]) < 0; hole = (hole+2) / 4) {
            data[hole] = data[(hole+2) / 4];
        }
        data[hole] = item;
	}
	
	//returns the smallest element in the heap
	@Override
	public E findMin() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return data[1];
	}

	//removes and returns the smallest element from the heap
	@Override
	public E deleteMin() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		E min = findMin();
		data[1] = data[size--];
		percolateDown(1);
		return min;
	}

	//returns whether the heap contains any elements
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	private void resize(int newSize) {
		@SuppressWarnings("unchecked")
		E[] temp = (E[]) new Object[newSize];
		for (int i = 0; i < data.length; i++) {
			temp[i] = data[i];
		}
		data = temp;
	}
	
    private void percolateDown( int hole ) {
        int child;
        E tmp = data[ hole ];
        for( ; hole * 4 - 2 <= size; hole = child ) {
            child = hole * 4 - 2;
            int nextChild = child + 1;
            for ( int i = 0; i < 3; i++) {
	            if( nextChild <= size &&
	                    c.compare(data[ nextChild ], data[ child ] ) < 0 ) {
	                child = nextChild;
	            }
	            nextChild++;
            }
            if(c.compare( data[ child ], tmp ) < 0 ) {
                data[ hole ] = data[ child ];
            } else {
                break;
            }
        }
        data[ hole ] = tmp;
    }
}

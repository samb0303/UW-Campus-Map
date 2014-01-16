//Blake Campbell & Samantha Brender

/** 
 * MoveToFrontList implements the DataCounter interface using a
 * linked list. Whenever an element of the linked list has its count
 * incremented or retrieved using incCount or getCount that element 
 * is moved to the front of the list.
 */
 

public class MoveToFrontList<E> implements DataCounter<E> {
	private ListNode front; //first node in the linked list
    private Comparator<? super E> c;
    private int size; //number of nodes in the linked list

	
	private class ListNode {
		public E item; //data element stored at this node
		public int count; //count for this data element
		public ListNode next; //next node in the linked list
		
		public ListNode(E i, ListNode n) {
			item = i;
			count = 1;
			next = n;
			size++; //increment overall size of linked list
		}
	}
	
	//getter method for the item in the front ListNode
	//for use in unit tests
	public E getFrontItem() {
		return front.item;
	}
	
	//getter method for the front field to use with unit tests.
	public ListNode getFront() {
		return front;
	}
	
	// create an empty list
	public MoveToFrontList(Comparator<? super E> comp) {
		front = null;
		c = comp;
		size = 0;
	}
	
	/** {@inheritDoc} */
	@Override
	public void incCount(E data) {
		ListNode temp = front;
		if (front == null) {
			front = new ListNode(data,null);
		} else if (c.compare(front.item, data) == 0) {
			front.count++;
		} else {
			while (temp.next != null) {
				//if we find the target, increment its count and move it 
				//to the front of the list
				if (c.compare(temp.next.item, data) == 0) {
					ListNode target = temp.next;
					target.count++;
					temp.next = target.next;
					target.next = front;
					front = target;
					return;
				} else {
				temp = temp.next;
				}
			}
			
			//if we didn't find the target then create a new node
			//at the front of the list
			front = new ListNode(data, front);
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public int getSize() {
		return size;
	}
	
	/** {@inheritDoc} */
	@Override
	public int getCount(E data) {
		if (front == null) {
			return 0;
		} else if (c.compare(front.item, data) == 0) {
			return front.count;
		} else {
			ListNode temp = front;
			while (temp.next != null) {
				//if we find the target move it to the front of the
				//list and return its count
				if (c.compare(temp.next.item, data) == 0) {
					ListNode target = temp.next;
					temp.next = target.next;
					target.next = front;
					front = target;
					return front.count;
				} else {
				temp = temp.next;
				}
			}
			return 0;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public SimpleIterator<DataCount<E>> getIterator() {
		return new SimpleIterator<DataCount<E>>() {
			ListNode iterate = front;
			public DataCount<E> next() {
				if (!hasNext()) {
					throw new java.util.NoSuchElementException();
				}
				DataCount<E> dat = new DataCount<E>(iterate.item, iterate.count);
				iterate = iterate.next;
				return dat;
			}

			public boolean hasNext() {
				return iterate != null;
			}
		};
	}
}


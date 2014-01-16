
public class NewHashTable<E> implements DataCounter<E>{

	private MoveToFrontList<E>[] thetable;
	
	private int tableSize;
	
	private Hasher<E> hasher;

	
	

	
	@SuppressWarnings("unchecked")
	public NewHashTable(Comparator<E> c, Hasher<E> h, int t){
		tableSize = t;
		hasher = h;
		thetable = (MoveToFrontList<E>[]) new MoveToFrontList[tableSize];
		
		
		for(int i = 0; i < thetable.length; i++){
			thetable[i] = new MoveToFrontList<>(c);
		}
		
	}
	
	/**	
     * Increment the count for a particular data element.
     * 
     * @param data data element whose count to increment.
     */
 
	public void incCount(E data){
    	
    	int i = hasher.hash(data) % tableSize;
    	
    	thetable[i].incCount(data);
    	
    }

    /**
     * The number of unique data elements in the structure.
     * 
     * @return the number of unique data elements in the structure.
     */
    public int getSize(){
    	int size = 0;
    	for(int i = 0; i < thetable.length; i++){
    		size += thetable[i].getSize();
    	}
    	return size;
    }
   
    /**
     * The current count for the data, 0 if it is not in the counter.
     */
	public int getCount(E data){
    	
    	int i = hasher.hash(data) % tableSize;
    	MoveToFrontList<E> l = thetable[i];
    	return l.getCount(data);
    }
    
    /**
     * Clients must not increment counts between an iterator's creation and its
     * final use.  Data structures need not check this.
     * 
     * @return an iterator for the elements.
     */
    public SimpleIterator<DataCount<E>> getIterator(){
    	return new SimpleIterator<DataCount<E>>() {
			
    	
			
    		SimpleIterator<DataCount<E>> indexIter = thetable[0].getIterator();
    		int index = 0;
    		
			public DataCount<E> next() {
				while(index < tableSize){
					if(indexIter.hasNext())
						return indexIter.next();
					else{
						index++;
						if(index < tableSize)
							indexIter = thetable[index].getIterator();
					}
				}
				throw new java.util.NoSuchElementException();
			}

			public boolean hasNext() {
				while(index < tableSize){
					if(indexIter.hasNext())
						return true;
					else{
						index++;
						if(index < tableSize)
							indexIter = thetable[index].getIterator();
					}
				}
				return false;				
			}
		};
    }


}

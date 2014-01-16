//Blake Campbell & Samantha Brender

import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	// iterates through a DataCounter and stores all the DataCount
	// objects from the DataCounter in an array.
	@SuppressWarnings("unchecked")
	public static <E> DataCount<E>[] getCountsArray(DataCounter<E> counter) {
		DataCount<E>[] counts = (DataCount<E>[])new DataCount[counter.getSize()]; 
		SimpleIterator<DataCount<E>> i = counter.getIterator();
		int j = 0;
		while (i.hasNext()) {
			counts[j] = i.next();
			j++;
		}
		return counts;
	}
	
    private static void countWords(String structure, String sort, String file) {
    	DataCounter<String> counter;
    	if (structure.equals("-b")) {
        	counter = new BinarySearchTree<String>(new StringComparator());
        } else if (structure.equals("-a")) {
        	counter = new AVLTree<String>(new StringComparator());
        }else if(structure.equals("-h")){
        	counter = new Hashtable<String>(new StringComparator(), new StringHasher());
        }
       /* else if(structure.equals("-h")){
        	counter = new NewHashTable<String>(new StringComparator(), new StringHasher(), 1019);
        }*/
        else {
        	counter = new MoveToFrontList<String>(new StringComparator());
        }
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        } catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }

        DataCount<String>[] counts = getCountsArray(counter);
        if (sort.equals("-is")) {
        	insertionSort(counts, new DataCountStringComparator());
        } else if (sort.equals("-hs")) {
        	heapSort(counts, new DataCountStringComparator());
        } else {
        	mergesort(counts, new DataCountStringComparator());
        }
        for (DataCount<String> c : counts) { 
            System.out.println(c.count + " \t" + c.data);
        }
    }
    
    /**
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be in ordered according to the comparator,
     * but the code below does not do this. 
     * 
     * This code uses insertion sort. The code is generic, but in this project
     * we use it with DataCount<String> and DataCountStringComparator.
     * 
     * @param counts array to be sorted.
	 * @param comparator for comparing elements.
     */
    public static <E> void insertionSort(E[] array, Comparator<E> comparator) {
    	for (int i = 1; i < array.length; i++) {
            E x = array[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (comparator.compare(x,array[j]) >= 0) {
                    break;
                }
                array[j + 1] = array[j];
            }
            array[j + 1] = x;
        }
    }
    
    //same purpose as insertionSort except here we use a heap to sort the
    //data
    public static <E> void heapSort(E[] array, Comparator<E> comparator) {
    	FourHeap<E> sorter = new FourHeap<E>(comparator); 
    	for (int i = 0; i < array.length; i++) {
    		sorter.insert(array[i]);
    	}
    	int j = 0;
    	while (!sorter.isEmpty()) {
    		array[j] = sorter.deleteMin();
    		j++;
    	}
    }
    
    public static <E> void kSort(E[] array, int k, Comparator<E> comparator) {
    	FourHeap<E> sorter = new FourHeap<E>(comparator);
    	if (k > array.length) {
    		k = array.length;
    	}
    	for (int i = 0; i < k; i++) {
    		sorter.insert(array[i]);
    	}
    	for (int i = k; i < array.length; i++) {
    		if (comparator.compare(sorter.findMin(), array[i]) > 0) {
    			sorter.deleteMin();
    			sorter.insert(array[i]);
    		}
    	}
    	for (int j = k - 1; j == 0; j--) {
    		array[j] = sorter.deleteMin();
    	}
    }
    
    @SuppressWarnings("unchecked")
	public static <E> void mergesort(E[] array, Comparator<E> comparator){
    	
    	E[] tempArray = (E[]) new Object[array.length];
    	mergeSort(array, tempArray, 0, array.length -1, comparator);
    	
    }
    private static <E> void mergeSort(E[] array, E[] tempArray, int left, int right, Comparator<E> comparator){
    	if(left < right){
    		int center = (left+right)/2;
    		mergeSort(array, tempArray, left, center, comparator);
    		mergeSort(array, tempArray, center+1, right, comparator);
    		merge(array, tempArray, left, center +1, right, comparator);
    	}
    }
    private static <E> void merge(E[] array, E[] tempArray, int leftPos, int rightPos, int rightEnd, Comparator<E> comparator){
    	int leftEnd = rightPos -1;
    	int tempPos = leftPos;
    	int numElements = rightEnd - leftPos + 1;
    	
    	while(leftPos <= leftEnd && rightPos <= rightEnd){
    		if(comparator.compare(array[leftPos], array[rightPos]) <= 0){
    			tempArray[tempPos++] = array[leftPos++];	
    		}else{
    			tempArray[tempPos++] = array[rightPos++];
    		}
    	}
    	while(leftPos <= leftEnd){
    		tempArray[tempPos++] = array[leftPos++];
    	}
    	while(rightPos <= rightEnd){
    		tempArray[tempPos++] = array[rightPos++];
    	}
    	for( int i = 0; i < numElements; i++, rightEnd--){
    		array[rightEnd] = tempArray[rightEnd];
    	}
    }
    
    public static void main(String[] args) {
    	if (args.length != 3) {
    		System.err.println("Usage: [ -b | -a | -m | -h ] " +
    				"[ -is | -hs | -os | -k <number>] <filename>");
        		System.exit(1);
    	}
    	countWords(args[0], args[1], args[2]);	
    }
}

//Blake Cambell & Samantha Brender
//Project2, Phase A
//AVLTree
//
//The AVLTree class implements the DataCounter interface using
//an AVL tree and extends BinarySearchTree. The constructor takes a Comparator<? super E> 
//"fuction object" so that items of type E can be compared.  
//Each AVLtree node associates a count and a height with an E.

public class AVLTree<E> extends BinarySearchTree<E> implements DataCounter<E> {
	
	//Public constructor
	public AVLTree(Comparator<? super E> c) {
		super(c);
	}
	
	/**
     * Increment the count for a particular data element.
     * 
     * @param data data element whose count to increment.
     */
	@SuppressWarnings("unchecked")
	@Override
	public void incCount(E data) {

		overallRoot = incCount(data, (AVLNode) overallRoot);
		
		//Check to see if tree is AVL balanced
		isAVLBalance((AVLNode) overallRoot);
    }
	
	//Helper method that increments the count for a given data element. 
	//Recurses the tree and restores AVL balance when a new data element is inserted.
	@SuppressWarnings("unchecked")
	private AVLNode incCount(E data, AVLNode root ){
		if(root == null)
			return new AVLNode(data);
		
		int cmp = comparator.compare(data, root.data);
		
		if(cmp < 0){
			root.left = incCount(data, (AVLNode) root.left);
			if(getHeight((AVLNode) root.left) - getHeight((AVLNode) root.right) == 2 )
                if(comparator.compare(data, root.left.data) < 0 )
                    root = rotateWithLeftChild(root);
                else
                    root = doubleWithLeftChild(root);
		}else if( cmp > 0 ){
            root.right = incCount(data, (AVLNode) root.right);
            if(getHeight((AVLNode) root.right) - getHeight((AVLNode) root.left) == 2)
                if(comparator.compare(data, root.right.data) > 0)
                    root = rotateWithRightChild(root);
                else
                    root = doubleWithRightChild(root);
        }else{
        	root.count++;	
        }
		root.height = Math.max(getHeight((AVLNode) root.left), getHeight((AVLNode) root.right)) + 1;
		return root;
		
	}
	
	//Returns the height of the given root.
	//If root is null, returns -1
	private int getHeight(AVLNode root){
		if(root== null){
			return -1;
		}
		return root.height;
	}
	//Performs a Case 1 shift on the given root 
	@SuppressWarnings("unchecked")
	private AVLNode rotateWithLeftChild(AVLNode root){
	     AVLNode temp = (AVLNode) root.left;
	     root.left = temp.right;
	     temp.right = root;
	     root.height = Math.max(getHeight((AVLNode) root.left), getHeight((AVLNode) root.right )) + 1;
	     temp.height = Math.max(getHeight((AVLNode) temp.left), root.height) + 1;
	     return temp;
	 }
	
	 //Performs a Case 4 shift on the given root
	 @SuppressWarnings("unchecked")
	private AVLNode rotateWithRightChild(AVLNode root){
	     AVLNode temp = (AVLNode) root.right;
	     root.right = temp.left;
	     temp.left = root;
	     root.height = Math.max(getHeight((AVLNode) root.left), getHeight((AVLNode) root.right)) + 1;
	     temp.height = Math.max(getHeight((AVLNode) temp.right), root.height) + 1;
	     return temp;
	 }
	 
	 //Performs a Case 2 Shift on the given root
	 @SuppressWarnings("unchecked")
	private AVLNode doubleWithLeftChild(AVLNode root){
	     root.left = rotateWithRightChild((AVLNode) root.left );
	     return rotateWithLeftChild(root);
    }
	 
	//Performs a Case 3 shift on the given root
	 @SuppressWarnings("unchecked")
	private AVLNode doubleWithRightChild(AVLNode root){
		 root.right = rotateWithLeftChild((AVLNode) root.right);
	     return rotateWithRightChild(root);
	}
    /**
     * The number of unique data elements in the structure.
     * 
     * @return the number of unique data elements in the structure.
     */
    public int getSize(){
    	return super.size;
    }
   
    /**
     * The current count for the data, 0 if it is not in the counter.
     */
    public int getCount(E data){
    	return super.getCount(data);
    }
    
    /**
     * Clients must not increment counts between an iterator's creation and its
     * final use.  Data structures need not check this.
     * 
     * @return an iterator for the elements.
     */
    public SimpleIterator<DataCount<E>> getIterator(){
    	return super.getIterator();
    }
    
    private class AVLNode extends BSTNode{
    	
    	private int height;
    
    	private AVLNode(E x){
    		super(x);
    		height = 0;
    	}
    
    }
    
    //isAVLBalance is a helper method that returns true if the given root is an AVLTree. 
    //It is used as a check in the incCount method.
    @SuppressWarnings({ "unchecked"})
	private boolean isAVLBalance(AVLNode root){
    	if(root == null){
    		return true;
    	}
    	if(Math.abs(getHeight((AVLNode) root.right) - getHeight((AVLNode) root.left)) >= 2){
    			return false;
    		}else{
    			return (isAVLBalance((AVLNode) root.right) && isAVLBalance((AVLNode) root.left));
    		}    		
   }
}

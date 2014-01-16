//Samantha Brender & Blake Campbell
//Project 3, Phase B
//
//Updates one shared grid to hold the contents of a grid for step 1 in parallel 
//grid is of size x*y where each element of the grid holds the total population for that grid position
public class lockParallel extends java.lang.Thread {
	
	private int lo;
	private int hi;
	private CensusData census;
	private int[][] grid;
	private Object[][] locks;
	private Pair<Rectangle, Integer> rectPop;
	private int x;
	private int y;
	
	public lockParallel(int l, int h, CensusData cd, int[][] r, Object[][] o, Pair<Rectangle, Integer> rp, int xVal, int yVal){
		lo = l;
		hi = h;
		census = cd;
		grid = r;
		locks = o;
		rectPop = rp;
		x = xVal;
		y = yVal;
	}
	
	public void run(){
				
		Rectangle USrect = rectPop.getElementA();
		float widthRect = (USrect.right - USrect.left) / x;
		float heightRect = (USrect.top - USrect.bottom) / y;
					
		for(int i = lo; i < hi; i++){
							
			CensusGroup group = census.data[i];
							
			int column;
			int row;
			//if data point is on the right edge
			if(group.longitude == USrect.right){
				column = (int) ((USrect.right - USrect.left) / widthRect);
			}else{
				column = (int) (((group.longitude - USrect.left) + widthRect) / widthRect);	
			}
			
			//if data point is on the top edge
			if(group.latitude == USrect.top){
				row = (int) ((USrect.top - USrect.bottom) / heightRect);
			}else{
				row = (int) (((group.latitude - USrect.bottom) + heightRect) / heightRect);
			}
			synchronized(locks[row-1][column-1]){
				grid[row-1][column-1] += group.population;
			}	
		}
	}
} 	


//Samantha Brender & Blake Campbell
//Project 3, Phase B
//
//gets the initial grid for step 1 in parallel
//grid is of size x*y where each element of the grid holds the total population for that grid position
import java.util.concurrent.RecursiveTask;


@SuppressWarnings("serial")
public class getGridStep1 extends RecursiveTask<int[][]>{
	
	
	private static final int SEQUENTIAL_THRESHOLD = 150;
	private int lo;
	private int hi;
	private CensusData census;
	private Pair<Rectangle, Integer> rectPop;
	private int x;
	private int y;
	
	
	public getGridStep1(int l, int h, CensusData cd, int xVal, int yVal, Pair<Rectangle, Integer> rp){
		lo = l;
		hi = h;
		census = cd;
		x = xVal;
		y = yVal;
		rectPop = rp;
		
	}
	
	
	@Override
	protected int[][] compute() {
		
		if(hi - lo < SEQUENTIAL_THRESHOLD){
			int[][] grid = new int[y][x];
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
					
					grid[row-1][column-1] += group.population;
				}
				return grid;
		}else{
			getGridStep1 leftFork = new getGridStep1(lo, ( hi + lo ) /2, census, x, y, rectPop);
			getGridStep1 rightFork = new getGridStep1(( hi + lo ) /2, hi, census, x, y, rectPop);
			leftFork.fork();
			int[][] right = rightFork.compute();
			int[][] left = leftFork.join();
			
			//combines the grids in parallel using parallelCombine
			parallelCombine combine = new parallelCombine(0, x, y, 0, left, right);
			combine.compute();
			return right;
		}
	}
}

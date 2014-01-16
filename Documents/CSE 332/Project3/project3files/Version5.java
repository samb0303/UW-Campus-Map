//Samantha Brender & Blake Campbell
//Project 3, Phase B
//
//Implements the Smarter and Lock-Based version (-v5)
public class Version5 {

	private CensusData census;
	private int pop;
	private Pair<Rectangle, Integer> rectPop;

	
	public Version5(CensusData cd){
		census = cd; 
		pop = 0;
		rectPop = null;
	}
	
	//gets the initial grid for step 1 in parallel
	//grid is of size x*y where each element of the grid holds the total population for that grid position
	public int[][] gridStep1(int x, int y) throws java.lang.InterruptedException {
		int[][] result  = new int[y][x];
		Object[][] locks = new Object[y][x];
		//must fill grid with zero's, overwrites the null's
		for(int j = 0; j < x; j++){
		    for(int i = 0; i < y; i++){  
		        locks[i][j] = new Integer(0);
		    }
		}
		//splits building the grid into four threads
		lockParallel one = new lockParallel(0, census.data_size/4, census, result, locks, rectPop, x, y);
		lockParallel two = new lockParallel(census.data_size/4, 2*census.data_size/4, census, result, locks, rectPop, x, y);
		lockParallel three = new lockParallel(2*census.data_size/4, 3*census.data_size/4, census, result, locks, rectPop, x, y);
		lockParallel four = new lockParallel(3*census.data_size/4, census.data_size, census, result, locks, rectPop, x, y);

		one.start();
		two.start();
		three.start();
		four.run();
		one.join();
		two.join();
		three.join();
		return result;
	}
	
	//gets the four corners for the U.S rectangle in parallel
	//sums and returns the total population of the U.S.
	public int get4Corners(){
		Pair<Rectangle, Integer> result = Main.get_4_Corners(census, pop);
		pop = result.getElementB();
		rectPop = result;
		return pop;
	}
	
	//gets the second grid for step 2 that is used in getSubPop to calculate the population of the desired query
	//takes in the grid from step 1 and the x and y dimensions 
	//grid is of size x*y where each element holds the total for all positions that are neither farther East nor farther South
	public int[][] gridStep2(int[][] grid, int x, int y){
		
		for(int i = 1; i < x; i++){
			grid[y-1][i] += grid[y-1][i-1];
		}
		for(int i = y-2; i >= 0; i--){
			grid[i][0] += grid[i+1][0];
			for(int j = 1; j < x; j++){
				grid[i][j] = grid[i][j] + grid[i+1][j] + grid[i][j-1] - grid[i+1][j-1];
			}
		}
	
		return grid;		
	}
	
	//Returns the sup population contained in the given query boundaries using the grid constructed by gridStep2
	public int getSubPop(int west, int south, int east, int north, int x, int y, int[][] grid){
		
		//Test if west, south, east, and north inputs are acceptable 
		if(west < 1 || west > x || south < 1 || south > y || east < west || east > x || north < south || north > y){
			throw new IllegalArgumentException("provide proper arguments");
		}
		
		
		int bottomleft;
		int topRight;
		int topLeft;
		boolean flag = false;
		if(west -1 == 0){
			bottomleft = 0;
			flag = true;
		}else{
			bottomleft = grid[south-1][west-2];
		}
		
		if(north == y){
			topRight = 0;
			flag = true;
		}else{
			topRight = grid[north][east-1];
		}
		if(flag){
			topLeft = 0;
		}else{
			topLeft = grid[north][west-2];
		}
		
		return (grid[south-1][east-1] - topRight - bottomleft + topLeft);
	}
}
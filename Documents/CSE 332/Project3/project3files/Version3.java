//Samantha Brender & Blake Campbell
//Project 3, Phase B
//
//Implement the Smarter and Sequential (-v3)
public class Version3 {
	
	private float left;
	private float right;
	private float top;
	private float bottom;
	private CensusData census;
	private int totalPop;
	
	public Version3(CensusData cd){
		census = cd; 
		left = 0;
		right = 0;
		top = 0;
		bottom = 0;
		totalPop = 0;
	}
	
	//gets the four corners for the U.S rectangle sequentially
	//sums and returns the total population of the U.S.
	public int get4Corners(){
		
		CensusGroup group = census.data[0];
		left = group.longitude;
		right = group.longitude;
		top = group.latitude;
		bottom = group.latitude;
		totalPop =+ group.population;
		
		for(int i = 1; i < census.data_size; i++){
			group = census.data[i];
			
			if(group.latitude < bottom){
				bottom = group.latitude;
			}
			if(group.latitude > top){
				top = group.latitude;
			}
			if(group.longitude < left){
				left = group.longitude;
			} 
			if(group.longitude > right){
				right = group.longitude;
			}
			totalPop += group.population;
		}
		return totalPop;
	}
	
	//gets the initial grid for step 1 sequentially 
	//grid is of size x*y where each element of the grid holds the total population for that grid position
	public int[][] gridStep1(int x, int y){
		 
		int[][] grid = new int[y][x];
		
		float widthRect = (right - left) / x;
		float heightRect = (top - bottom) / y;
		
		for(int i = 0; i < census.data_size; i++){
				
				CensusGroup group = census.data[i];
				
				int column;
				int row;
				//if data point is on the right edge
				if(group.longitude == right){
					column = (int) ((right - left) / widthRect);
				}else{
					column = (int) (((group.longitude - left) + widthRect) / widthRect);	
				}
				
				//if data point is on the top edge
				if(group.latitude == top){
					row = (int) ((top - bottom) / heightRect);
				}else{
					row = (int) (((group.latitude - bottom) + heightRect) / heightRect);
				}
				
				grid[row-1][column-1] += group.population;
			}
		return grid;
		
	}
	
	//gets the second grid for step 2
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
	
	//gets the second grid for step 2 that is used in getSubPop to calculate the population of the desired query
	//takes in the grid from step 1 and the x and y dimensions 
	//grid is of size x*y where each element holds the total for all positions that are neither farther East nor farther South
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

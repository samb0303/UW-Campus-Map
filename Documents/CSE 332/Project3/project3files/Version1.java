//Samantha Brender & Blake Campbell
//Project 3, Phase A
//
//
//Implement the Simple an Sequential version (-v1)

public class Version1 {

	private float left;
	private float right;
	private float top;
	private float bottom;
	private CensusData census;
	private int totalPop;
	
	public Version1(CensusData cd){
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
	
	//Returns the sup population contained in the given query boundaries
	public int getSubPopulation(int w, int s, int e, int n, int x, int y){
		
		//Test if west, south, east, and north inputs are acceptable 
		if(w < 1 || w > x || s < 1 || s > y || e < w || e > x || n < s || n > y){
			throw new IllegalArgumentException("provide proper arguments");
		}
		
		int subPop = 0;
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
			if(column >= w && column <= e && row >= s && row <= n){
				
				subPop += census.data[i].population;
			}
		}	
		return subPop;
	}
}

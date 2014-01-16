//Samantha Brender & Blake Campbell
//Project 3, Phase A
//
//
//Implement the Simple and Parallel version (-v2)
public class Version2 {

	private CensusData census;
	private int pop;
	private Pair<Rectangle, Integer> rectPop;

	public Version2(CensusData cd){
		census = cd; 
		pop = 0;
		rectPop = null;
	}
	
	//gets the four corners for the U.S rectangle in parallel
	//sums and returns the total population of the U.S.
	public int get4Corners(){
		Pair<Rectangle, Integer> result = Main.get_4_Corners(census, pop);
		pop = result.getElementB();
		rectPop = result;
		return pop;
	}
	
	//Returns the sup population contained in the given query boundaries in parallel 
	public int getSubPopulation(int w, int s, int e, int n, int x, int y){
		//Test if west, south, east, and north inputs are acceptable 
		if(w < 1 || w > x || s < 1 || s > y || e < w || e > x || n < s || n > y){
			throw new IllegalArgumentException("provide proper arguments");
		}
		
		Integer result = Main.get_sub_pop(census, rectPop, w, s, e, n, x, y);
		return result;
	}
}

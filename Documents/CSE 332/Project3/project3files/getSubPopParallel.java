//Samantha Brender & Blake Campbell
//Project 3, Phase B
//
////Returns the sup population contained in the given query boundaries in parallel 
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class getSubPopParallel extends RecursiveTask<Integer> {
			
		private static final int SEQUENTIAL_THRESHOLD = 150;
		private int lo;
		private int hi;
		private CensusData census;
		private Pair<Rectangle, Integer> rectPop;
		private int x;
		private int y;
		private int west;
		private int south; 
		private int east;
		private int north;
		
		
		public getSubPopParallel(int lowb, int upb, CensusData cd, Pair<Rectangle, Integer> rp, int w, int s, int e, int n, int xVal, int yVal){
			
			lo = lowb;
			hi = upb;
			census = cd;
			rectPop = rp;
			x = xVal;
			y = yVal;
			west = w;
			south = s;
			east = e;
			north = n;

		}
		
		//Computes and returns the sub Population in the desired query 
		@Override
		protected Integer compute() {
			
			if(hi-lo < SEQUENTIAL_THRESHOLD){
				int subPop = 0;
				Rectangle rect = rectPop.getElementA();
				float widthRect = (rect.right - rect.left) / x;
				float heightRect = (rect.top - rect.bottom) / y;
				
				for(int i = lo; i < hi; i++){
					
					CensusGroup group = census.data[i];
											
					int column;
					int row;
					//if data point is on the right edge
					if(group.longitude == rect.right){
						column = (int) ((rect.right - rect.left) / widthRect);
					}else{
						column = (int) (((group.longitude - rect.left) + widthRect) / widthRect);	
					}
					
					//if data point is on the top edge
						if(group.latitude == rect.top){
							row = (int) ((rect.top - rect.bottom) / heightRect);
						}else{
							row = (int) (((group.latitude - rect.bottom) + heightRect) / heightRect);
						}
						if(column >= west && column <= east && row >= south && row <= north){
							
							subPop += census.data[i].population;
						}
					}	
					return subPop;
				}else{
					getSubPopParallel leftFork = new getSubPopParallel(lo, (hi+lo)/2, census, rectPop, west, south, east, north, x, y);
					getSubPopParallel rightFork = new getSubPopParallel((hi+lo)/2, hi, census, rectPop, west, south, east, north, x, y);
					leftFork.fork();
					Integer rightGroup = rightFork.compute();
					Integer leftGroup = leftFork.join();
					
					return (rightGroup + leftGroup);
				}	
			}
}

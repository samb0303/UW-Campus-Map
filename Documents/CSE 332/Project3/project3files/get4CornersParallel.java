//Samantha Brender & Blake Campbell
//Project 3, Phase B
//
//Calculates the 4 corners of the U.S and the total population
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class get4CornersParallel extends RecursiveTask<Pair<Rectangle, Integer>> {
	
		static int SEQUENTIAL_THRESHOLD = 150;
		private int lo;
		private int hi;
		private CensusData census;
		
		public get4CornersParallel(int lowb, int upb, CensusData cd) {
			lo = lowb;
			hi = upb;
			census = cd;	
		}
		
		//Computes and returns a Pair<Rectangle, Integer> that contains a rectangle with the dimensions of the U.S
		//and an Integer representing the total Population of the U.S.
		@Override
		protected Pair<Rectangle, Integer> compute() {
			
			if(hi - lo < SEQUENTIAL_THRESHOLD){
				CensusGroup group = census.data[lo];
				float left = group.longitude;
				float right = group.longitude;
				float top = group.latitude;
				float bottom = group.latitude;
				int totalPop =+ group.population;
				
				for(int i = lo + 1; i < hi; i++){
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
				Rectangle r = new Rectangle(left, right, top, bottom);
				Pair<Rectangle, Integer> result = new Pair<>(r, totalPop);
				return result;
			}else{
				get4CornersParallel leftFork = new get4CornersParallel(lo, ( hi + lo ) /2, census);
				get4CornersParallel rightFork = new get4CornersParallel(( hi + lo ) /2, hi, census);
				leftFork.fork();
				Pair<Rectangle, Integer> rightGroup = rightFork.compute();
				Pair<Rectangle, Integer> leftGroup = leftFork.join();
				
				Rectangle rightRect = rightGroup.getElementA();
				Rectangle leftRect = leftGroup.getElementA();
				
				float left = Math.min(rightRect.left, leftRect.left);
				float right = Math.max(leftRect.right, rightRect.right);
				float top = Math.max(rightRect.top, leftRect.top);
				float bottom = Math.min(rightRect.bottom, leftRect.bottom);
				
				int pop = rightGroup.getElementB() + leftGroup.getElementB();
				Rectangle rect = new Rectangle(left, right, top, bottom);
				
				Pair<Rectangle, Integer> r = new Pair<>(rect, pop);
				
				return r;
			}
		}	
}


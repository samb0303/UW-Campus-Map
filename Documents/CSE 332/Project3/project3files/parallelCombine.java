//Samantha Brender & Blake Campbell
//Project 3, Phase B
//
//Combines the two given grids in parallel
import java.util.concurrent.RecursiveAction;


@SuppressWarnings("serial")
public class parallelCombine extends RecursiveAction {

	static int SEQUENTIAL_THRESHOLD = 150;
	private int left;
	private int right;
	private int top;
	private int bottom;
	private int[][] arrLeft;
	private int[][] arrRight;
	
	public parallelCombine(int l, int r, int t, int b, int[][] al, int[][] ar){
		left = l;
		right = r;
		top = t;
		bottom = b;
		arrLeft = al;
		arrRight = ar;
	}
	@Override
	protected void compute() {
		
		if(right-left < SEQUENTIAL_THRESHOLD){
			for(int j = bottom; j < top; j++){
				for(int i = left; i < right; i++){
					arrRight[j][i] += arrLeft[j][i];
				}
			}
			
		}else{
			
			parallelCombine lowLeft = new parallelCombine(left, ( left + right ) /2, bottom, (top + bottom) / 2, arrLeft, arrRight);
			parallelCombine lowRight = new parallelCombine(( left + right ) /2, right, bottom, (top + bottom) / 2, arrLeft, arrRight);
			parallelCombine upLeft = new parallelCombine(left, ( left + right ) /2, (bottom + top)/2, top, arrLeft, arrRight);
			parallelCombine upRight = new parallelCombine(( left + right ) /2, right, (bottom + top)/2, top, arrLeft, arrRight);
			
			
			upLeft.fork();
			upRight.fork();
			lowLeft.fork();
			lowRight.compute();
			upLeft.join();
			upRight.join();
			lowLeft.join();
		}
	
	}
}

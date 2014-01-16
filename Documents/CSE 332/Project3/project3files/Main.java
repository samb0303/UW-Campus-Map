//Samantha Brender & Blake Campbell
//Project 3, Phase A
//
//
//Main ForkJoin class. Invokes the sub classes get4CornersParllel and getSubPopParallel

import java.util.concurrent.ForkJoinPool;

	public class Main {
		
		static final ForkJoinPool fjPool = new ForkJoinPool();
		
		//invokes get4cornersParallel
		//returns a Pair<Rectangle, Integer>
		static Pair<Rectangle, Integer> get_4_Corners(CensusData data, int totalPop) {
			Pair<Rectangle, Integer> result = fjPool.invoke(new get4CornersParallel(0, data.data_size, data));	
			return result;
		}
		
		//invokes getSubPopParallel
		//returns the subPopulation of the desired query
		static Integer get_sub_pop(CensusData data, Pair<Rectangle, Integer> rectPop, int west, int south, int east, int north, int x, int y){
			Integer i = fjPool.invoke(new getSubPopParallel(0, data.data_size, data, rectPop, west, south, east, north, x, y));
			return i;
		}
		
		//invokes getGridStep1
		//returns a grid of size x*y where each element of the grid holds the total population for that grid position
		static int[][] get_grid_step1(int x, int y, CensusData data, Pair<Rectangle, Integer> rectPop){
			int[][] result = fjPool.invoke(new getGridStep1(0, data.data_size, data, x, y, rectPop));
			return result;
		}
		
		//invokes parallelCombine
		//parallelCombine combines the contents of one grid with another grid in parallel 
		static void parallel_combine(int x, int y, int[][] leftArr, int[][] rightArr){
			fjPool.invoke(new parallelCombine(0, x, y, 0, leftArr, rightArr));
		}
	}

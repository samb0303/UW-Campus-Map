//Samantha Brender & Blake Campbell
//Project 3, Phase A


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE  = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX   = 5;
	public static final int LONGITUDE_INDEX  = 6;
	
	// parse the input file into a large array held in a CensusData object
	@SuppressWarnings("resource")//I ADDED
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();
		
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(filename));
            
            // Skip the first line of the file
            // After that each line has 7 comma-separated numbers (see constants above)
            // We want to skip the first 4, the 5th is the population (an int)
            // and the 6th and 7th are latitude and longitude (floats)
            // If the population is 0, then the line has latitude and longitude of +.,-.
            // which cannot be parsed as floats, so that's a special case
            //   (we could fix this, but noisy data is a fact of life, more fun
            //    to process the real data as provided by the government)
            
            String oneLine = fileIn.readLine(); // skip the first line

            // read each subsequent line and add relevant data to a big array
            while ((oneLine = fileIn.readLine()) != null) {
                String[] tokens = oneLine.split(",");
                if(tokens.length != TOKENS_PER_LINE)
                	throw new NumberFormatException();
                int population = Integer.parseInt(tokens[POPULATION_INDEX]);
                if(population != 0)
                	result.add(population,
                			   Float.parseFloat(tokens[LATITUDE_INDEX]),
                		       Float.parseFloat(tokens[LONGITUDE_INDEX]));
            }

            fileIn.close();
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        return result;
	}
	
	// argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
	// argument 4: -v1, -v2, -v3, -v4, or -v5
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		
		CensusData cd = parse(args[0]);
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);	
		String version = args[3];
		
		//Prompts user for dimensions of desired query: 
	    //The Western-most column that is part of the rectangle; error if this is less than 1 or greater than x.
	    //The Southern-most row that is part of the rectangle; error if this is less than 1 or greater than y.
	    //The Eastern-most column that is part of the rectangle; error if this is less than the Western-most column (equal is okay) or greater than x.
	    //The Northern-most row that is part of the rectangle; error if this is less than the Southern-most column (equal is okay) or greater than y.
		//Continues prompting user until user input is something other than 4 ints
		for(;;){
			System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
			Scanner a = new Scanner(System.in);
			String s = a.nextLine();
			a = new Scanner(s);
			int[] arr = new int[4];
			for(int i = 0; i < 4; i++){
				if(a.hasNextInt()){	
					arr[i] = a.nextInt();
				}else{
					System.exit(0);
				}	
			}
			if(a.hasNext()){
				System.exit(0);
			}
			
			int west = arr[0];
			int south = arr[1];
			int east = arr[2];
			int north = arr[3];
			
			
			//executes with Version one if arg[3] is -v1
			if(version.equals("-v1")){
				Version1 v = new Version1(cd);
				int totalPop = v.get4Corners();
				
				int i = v.getSubPopulation(west, south, east, north, x, y);
				System.out.print("population of rectangle: ");
				System.out.println(i);
				double percent = (i * 100.0 / totalPop);
				System.out.print("percent of total population: ");
				System.out.printf("%.2f\n", percent);	
			}	
			
			//executes with Version two if arg[3] is -v2
			if(version.equals("-v2")){
				Version2 v = new Version2(cd);
				int totalPop = v.get4Corners();
				int i = v.getSubPopulation(west, south, east, north, x, y);
				System.out.print("population of rectangle: ");
				System.out.println(i);
				double percent = (i * 100.0 / totalPop);
				System.out.print("percent of total population: ");
				System.out.printf("%.2f\n", percent);
			}
			
			//executes with Version three if arg[3] is -v3
			if(version.equals("-v3")){
				Version3 v3 = new Version3(cd);
				int totalPop = v3.get4Corners();
				int[][] grid1 = v3.gridStep1(x, y);
				int[][] grid2 = v3.gridStep2(grid1, x, y);
				
				int i = v3.getSubPop(west, south, east, north, x, y, grid2);
				System.out.print("population of rectangle: ");
				System.out.println(i);
				double percent = (i * 100.0 / totalPop);
				System.out.print("percent of total population: ");
				System.out.printf("%.2f\n", percent);
			}
			
			//executes with Version four if arg[3] is -v4
			if(version.equals("-v4")){
				Version4 v4 = new Version4(cd);
				
				int totalPop = v4.get4Corners();
				int[][] grid1 = v4.gridStep1(x, y);
				
				int[][] grid2 = v4.gridStep2(grid1, x, y);
				
				int i = v4.getSubPop(west, south, east, north, x, y, grid2);
				System.out.print("population of rectangle: ");
				System.out.println(i);
				double percent = (i * 100.0 / totalPop);
				System.out.print("percent of total population: ");
				System.out.printf("%.2f\n", percent);
			}
			
			//executes with Version five if arg[3] is -v5
			if(version.equals("-v5")){
				Version5 v = new Version5(cd);
				int totalPop = v.get4Corners();
				int[][] grid1 = v.gridStep1(x, y);
				int[][] grid2 = v.gridStep2(grid1, x, y);
				
				int i = v.getSubPop(west, south, east, north, x, y, grid2);
				System.out.print("population of rectangle: ");
				System.out.println(i);
				double percent = (i * 100.0 / totalPop);
				System.out.print("percent of total population: ");
				System.out.printf("%.2f\n", percent);
			}
		}
	}
}





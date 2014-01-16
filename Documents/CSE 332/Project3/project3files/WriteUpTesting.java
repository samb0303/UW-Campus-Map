import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class WriteUpTesting {
	
	public static void main(String[] args){
		
		for(int t = 0; t < 50; t++){
			CensusData cd = parse("CenPop2010.txt");
			Version4 v4 = new Version4(cd);
			long startTime = System.currentTimeMillis();
			v4.get4Corners();
			int[][] grid1 = v4.gridStep1(100, 500);
			int[][] grid2 = v4.gridStep2(grid1, 100, 500);
			v4.getSubPop(1, 1, 100, 500, 100, 500, grid2);
			long stopTime = System.currentTimeMillis();
			
			long elapsedTime = stopTime - startTime;
			System.out.println("cutoff: " + 100 + "time: " + elapsedTime);
		}
	}
	
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
}

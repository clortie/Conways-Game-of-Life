import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Program to play Conway's Game of Life
 *
 * @author Calvin Lortie
 *
 */
public final class ConwaysGameOfLife {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ConwaysGameOfLife() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments 
     */
    public static void main(String[] args) throws IOException {
    		
        // Get the name of the file to open.
        String fileName = getFileName();
        
        // Read in generation to output
        int generationNumber=getGenNum();
        
        // useful info for user
        System.out.println("The resultant data was stored in lib/final-gen.txt.\n");

    	// set up seed
        Game life = new Game(fileName);
    	
    	// calculate the next generation until we have the specified generation
    	getFinalGeneration(life, generationNumber);
    	
    	// output the result
    	outputFinalGen(life);
    	
    	//end
    	System.out.println("Goodbye");

    }
    
    /**
	 * Function to get a file name from the user
	 *
	 * @returns a string of a valid path to a file name
	 */
    public static String getFileName(){
    	String fileName="";
    	// Read in file name
        while(fileName.length()<1){
        	System.out.println("Enter a seed file name or path to a seed file: ");
        	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        	try {
				fileName = br.readLine();
				
			} catch (IOException e) {
				System.out.println("Something went wrong with your input.");
				e.printStackTrace();
			}
        }
        return(fileName);
    }
    
    /**
	 * Function to get the final generation number from the user
	 * 
	 * @returns generation number to compute for the game
	 * 
	 * @requires the input number must be a non negative integer.
	 */
    public static int getGenNum(){
    	// Read in generation to output
        int generationNumber=0;
        System.out.println("Please enter the generation number to calculate (positive, non zero integer): ");;
        while(generationNumber<=0){
        	try {
				generationNumber=System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	if(generationNumber<=0){
        		System.out.println("Generation number must be a positive integer. Try again: ");
        	}
        }
        return(generationNumber);
    }
    
    /**
	 * Function to get the final generation for the game
	 * 
	 * @param life
	 * 				the Game instance
	 * @param finalGen
	 * 				the generation number to get
	 * @updates life.matrix
	 */
    public static void getFinalGeneration(Game life, int finalGen){
    	// get next gen until we get the correct gen
    	for(int i=0;i<finalGen;i++){
    		life.calculateNextGen();
    	}
    }
    
    /**
	 * Function to output the result of the game to a file
	 * 
	 * @param life
	 * 				the Game instance
	 */
    public static void outputFinalGen(Game life){
    	BufferedWriter writer = null;
        try {

            writer = new BufferedWriter(new FileWriter("lib/final-gen.txt"));
            writer.write(life.numRows+" "+life.numCols);
            writer.flush();
            for ( int i = 0; i < life.numRows; i++){
            	writer.newLine();
            	for(int j=0;j<life.numCols;j++){
                    writer.write(life.matrix[i][j]);
            	}
                writer.flush();
            }

        } catch(IOException ex) {
            ex.printStackTrace();
        } finally{
            if(writer!=null){
                try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }  
        }
    } 
}
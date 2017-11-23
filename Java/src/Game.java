import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 
 * @author Calvin Lortie
 * Game object for Conway's Game of Life
 *
 */
public class Game {
	public int numRows;
	public int numCols;
	public char[][] matrix;
	
	/**
	 * Constructor with row and column
	 * Initializes member vars
	 * 
	 * @param numRows
	 * 				number of rows
	 * @param numCols
	 * 				number of columns
	 * @updates
	 * 			numRows, numCols, matrix
	 */
	public Game(int numRows, int numCols){
		this.numRows=numRows;
		this.numCols=numCols;
		this.matrix = new char[numRows][numCols];
		
	}
	/**
	 * Constructor with file name arg
	 * 
	 * @param fileName
	 * 				the file with game object information
	 * @updates numRows, numCols, matrix
	 * @requires <pre>
	 * fileName relates to a valid seed file
	 * </pre>
	 */
	public Game(String fileName){

        try {
            
            Scanner in = new Scanner(new BufferedReader(new FileReader(fileName)));
            
            // get rows and columns
            this.numRows = in.nextInt();
            this.numCols = in.nextInt();
            
            // initialize matrix
            this.matrix = new char [numRows][numCols];
            
            // read into matrix
            for(int i=0; i<numRows; i++){
            	String row = in.next(); 
            	this.matrix[i] = row.toCharArray();
            }

            // close file
            in.close();
            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
	}
	
	/**
	 * Method to calculate the next generation of the game according to the rules
	 * 
	 * @updates this.matrix
	 */
	public void calculateNextGen(){
		Game nextGen = new Game(this.numRows, this.numCols);
		
		int top, bottom, leftInd, rightInd;
		
		boolean above, below, leftEx, rightEx;
		
		int alive=0;
		
		// iterate through seed, generating nextGen according to rules
	    for(int i=0; i<this.numRows; i++){
	        // get row above and below
	        top = i-1;
	        bottom = i+1;
	        above = top>=0;
	        below = bottom<this.numRows;
	        
	        for(int j=0; j<this.numCols; j++){
	            // get left and right column indices
	            leftInd = j-1;
	            rightInd = j+1;
	            
	            // get existence left and right columns
	            leftEx = leftInd>=0;
	            rightEx = rightInd<this.numCols;
	            
	            // determine number of living and dead neighbors
	            // top neighbors
	            if(above){
	                if(leftEx && this.matrix[top][leftInd]=='*'){
	                    alive++;
	                }
	                if(rightEx &&this.matrix[top][rightInd]=='*'){
	                    alive++;
	                }
	                if(this.matrix[top][j]=='*'){
	                    alive++;
	                }
	            }
	            // bottom neighbors
	            if(below){
	                if(leftEx && this.matrix[bottom][leftInd]=='*'){
	                    alive++;
	                }
	                if(rightEx && this.matrix[bottom][rightInd]=='*'){
	                    alive++;
	                }
	                if(this.matrix[bottom][j]=='*'){
	                   alive++;
	                }
	            }
	            // left side
	            if(leftEx && this.matrix[i][leftInd]=='*'){
	                alive++;
	            }
	            // right side
	            if(rightEx && this.matrix[i][rightInd]=='*'){
	                alive++;
	            }
	            
	            // what is the next state of this cell?
	            // if it is dead and there are three living neighbors, it is alive next
	            if(this.matrix[i][j]=='.'){
	                nextGen.matrix[i][j] = alive==3 ? '*' : '.';
	            }
	            // if it is alive
	            else{
	                // dies
	                if(alive<2 || alive>3){
	                    nextGen.matrix[i][j] = '.';
	                }
	                // lives
	                else{
	                    nextGen.matrix[i][j] = '*';
	                }
	            }
	            
	            // reset alive to zero for next index
	            alive=0;
	        }
	    }
	    
	    this.matrix=nextGen.matrix;
	}
}

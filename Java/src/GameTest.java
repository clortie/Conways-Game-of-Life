

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void constructorWithFileNameTest() {
		int r = 4;
		int c = 5;
		char star = '*';
		char dot = '.';
		
		String fileName = "lib/test-input.txt";
		Game life = new Game(fileName);
		
		assertEquals(r, life.numRows);
		assertEquals(c, life.numCols);
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				if(i%2==0){
					assertEquals(dot,life.matrix[i][j]);
				}
				else{
					assertEquals(star,life.matrix[i][j]);
				}
			}
		}
		
	}
	
	@Test
	public void constructorWithRowAndColumnValuesTest() {
		int r = 4;
		int c = 5;
		Game life = new Game(r,c);
		
		assertEquals(r, life.numRows);
		assertEquals(c, life.numCols);
		assertEquals(r, life.matrix.length);
		for(int i=0;i<r;i++){
			assertEquals(c,life.matrix[i].length);
		}
	}
	
	@Test
	public void calculateNextGenSquareTest(){
		char star = '*';
		
		Game life = new Game("lib/test-square.txt");
		
		life.calculateNextGen();
		
		for(char[] i : life.matrix){
			assertEquals(star, i[0]);
			assertEquals(star, i[1]);
		}
	}
	
	@Test
	public void calculateNextGenSuperTest(){
		char star = '*';
		char dot = '.';
		char [][] superExpected = {{dot,dot,dot,dot,dot,dot},
				{dot,dot,dot,star,dot,star},
				{dot,dot,dot,star,star,dot},
				{dot,dot,dot,star,star,dot},
				{star,star,dot,dot,star,dot},
				{star,star,dot,dot,star,star},
				{star,star,dot,dot,star,star}};
		
		Game life = new Game("lib/test-supertest.txt");
		
		life.calculateNextGen();
		
		for(int i=0; i<life.numRows; i++){
			for(int j=0; j<life.numCols; j++){
				assertEquals(superExpected[i][j], life.matrix[i][j]);
			}
		}
	}

}

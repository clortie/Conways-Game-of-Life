import static org.junit.Assert.*;

import org.junit.Test;

public class ConwaysGameOfLifeTest {

	@Test
	public void getFinalGenerationSquareTest() {
		
		char star = '*';

		Game life = new Game("lib/test-square.txt");
		
		for(int i=0; i<4; i++){
			// will be generation 0, 1, 3, 6, 10
			ConwaysGameOfLife.getFinalGeneration(life, i);
			assertEquals(star, life.matrix[0][0]);
			assertEquals(star, life.matrix[0][1]);
			assertEquals(star, life.matrix[1][0]);
			assertEquals(star, life.matrix[1][1]);
		}
	}
	
	@Test
	public void getFinalGenerationSeedFileTest(){
		
		Game life = new Game("lib/test-multigen.txt");
		
		char star = '*';
		char dot = '.';
		
		char[][] gen1 = {{star, star, dot},
				{dot, dot, dot},
				{dot, star, star}};
		
		char[][] gen2 = {{dot, dot, dot},
				{star, dot, star},
				{dot, dot, dot}};
		
		// first generation test
		ConwaysGameOfLife.getFinalGeneration(life, 1);
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				assertEquals(gen1[i][j], life.matrix[i][j]);
			}
		}
		
		//reset life
		life = new Game("lib/test-multigen.txt");
		
		// second generation test
		ConwaysGameOfLife.getFinalGeneration(life, 2);
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				assertEquals(gen2[i][j], life.matrix[i][j]);
			}
		}
	}
}

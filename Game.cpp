//
//  Game.cpp
//  calvin_lortie_cplusplus_audition
//
//  An implementation of Conway's Game of Life
//
//  Created by Calvin Lortie on 10/17/17.
//  Copyright © 2017 Calvin Lortie. All rights reserved.
//

#include <stdio.h>
#include <iostream>
#include <fstream>
#include <cassert>

using namespace std;

//----------------------------------------------------------------------

void setup(string, int&, int&, char**&);

char** calculateNextGen(int, int, char**);

void output(string outFilename, const int, const int, char**);

void dealloc(int, char**);

void test_setup();

void test_calculateNextGen();

void test_output();

//----------------------------------------------------------------------

int main(int argc, const char * argv[]) {
    

    string filename="";
    
    // get seed file path
    while(filename.length()<1){
        cout<<"Please enter the name of or path to a seed .txt file: ";
        getline(cin, filename);
        cout<<endl;
    }
    
    // get generation to output
    int generationNumber=0;
    cout<<"Please enter the generation number to calculate (positive, non zero integer): ";
    while(generationNumber<=0){
        cin>>generationNumber;
        if(generationNumber<=0){
            cout<<"Generation number must be a positive integer. Try again: ";
        }
    }
    cout<<endl;
    
    // useful info for user
    cout<<"The resultant file will output to the working directory as nextGen.txt."<<endl<<endl;
    
    
    // declare matrix vars
    int numRows, numCols;
    char **seed;
    
    // read file into matrix
    setup(filename, numRows, numCols, seed);
    
    // calculate the next generation until we have the specified generation
    char **nextGen = nullptr;
    for(int i=0; i<generationNumber; i++){
        // get next gen
        nextGen = calculateNextGen(numRows, numCols, seed);
        
        // dealloc seed so it can store this gen
        dealloc(numRows, seed);
        
        // point seed to this gen
        seed = nextGen;
    }
    
    // output next gen to file
    string outFilename = "next-gen.txt";
    
    output(outFilename, numRows, numCols, nextGen);
    
    cout<<"Calculated generation "<<generationNumber<<" and stored it in next-gen.txt."<<endl<<endl;
    

    // deallocate dynamically assigned memory
    dealloc(numRows, nextGen);
    
    
    // run tests
    
    cout<<"Running tests..."<<endl;
    
    cout<<"\tTest setup..."<<endl;
    test_setup();
    cout<<"\tTest setup completed."<<endl;
    
    cout<<"\tTest calculateNextGen..."<<endl;
    test_calculateNextGen();
    cout<<"\tTest calculateNextGen completed."<<endl;
    
    cout<<"\tTest output..."<<endl;
    test_output();
    cout<<"\tTest output completed."<<endl;
    
    cout<<"Testing completed."<<endl<<endl;
    cout<<"Goodbye."<<endl<<endl;

    return 0;
}

//----------------------------------------------------------------------


/**
 function to setup a game matrix. updates params via pass-by-reference
 filename is the seed file
 numRows updated to number of rows in matrix
 numCols updated to number of columns in matrix
 seed updated to matrix of '.' and '*', living and dead, respectively
 **/
void setup(string filename, int& numRows, int& numCols, char**& seed){
    
    // open file
    ifstream ifs;
    ifs.open(filename.c_str());
    
    // get size of game
    ifs >> numRows >> numCols;
    
    // initialize game matrix
    seed = new char*[numRows];
    for(int i = 0; i<numRows; i++){
        seed[i] = new char[numCols];
    }
    
    // fill matrix with file values
    for(int i=0; i<numRows; i++){
        for(int j=0; j<numCols; j++){
            ifs>>seed[i][j];
        }
    }

}

//----------------------------------------------------------------------
/**
 A function to caculate the next generation of Conway's game of life given an initial state according to the following rules:
   1. Any live cell with fewer than two live neighbors dies, as if caused by under population.
   2. Any live cell with more than three live neighbors dies, as if by overcrowding.
   3. Any live cell with two or three live neighbors lives on to the next generation.
   4. Any dead cell with exactly three live neighbors becomes a live cell.
   5. A cell’s neighbors are those cells which are horizontally, vertically or
   diagonally adjacent. Most cells will have eight neighbors. Cells placed on the
   edge of the grid will have fewer.
 returns matrix with values of the calculated next generation
 numRows is the number of rows in the seed
 numCols is the number of columns in the seed
 seed is the matrix to use in the calculation of the next generation
**/
char** calculateNextGen(int numRows, int numCols, char** seed){
    
    // delcare and instatiate next gen matrix
    char** nextGen = new char*[numRows];
    for(int i=0; i<numRows; i++){
        nextGen[i] = new char[numCols];
    }
    
    // store neighbor indices for ease of read/write
    int top, bottom, leftInd, rightInd;
    
    // bool for existance of top bottom left right access
    bool above, below, leftEx, rightEx;
    
    // keep count of alive neighbors
    int alive=0;
    
    // iterate through seed, generating next gen according to rules
    for(int i=0; i<numRows; i++){
        // get row above and below
        top = i-1;
        bottom = i+1;
        above = top>=0;
        below = bottom<numRows;
        
        for(int j=0; j<numCols; j++){
            // get left and right column indices
            leftInd = j-1;
            rightInd = j+1;
            
            // get existance left and right columns
            leftEx = leftInd>=0;
            rightEx = rightInd<numCols;
            
            // determine number of living and dead neighbors
            // top neighbors
            if(above){
                if(leftEx && seed[top][leftInd]=='*'){
                    alive++;
                }
                if(rightEx &&seed[top][rightInd]=='*'){
                    alive++;
                }
                if(seed[top][j]=='*'){
                    alive++;
                }
            }
            // bottom neighbors
            if(below){
                if(leftEx && seed[bottom][leftInd]=='*'){
                    alive++;
                }
                if(rightEx && seed[bottom][rightInd]=='*'){
                    alive++;
                }
                if(seed[bottom][j]=='*'){
                   alive++;
                }
            }
            // left side
            if(leftEx && seed[i][leftInd]=='*'){
                alive++;
            }
            // right side
            if(rightEx && seed[i][rightInd]=='*'){
                alive++;
            }
            
            // what is the next state of this cell?
            // if it is dead and there are three living neighbors, it is alive next
            if(seed[i][j]=='.'){
                (alive==3) ? (nextGen[i][j] = '*') : (nextGen[i][j] = '.');
            }
            // if it is alive
            else{
                // dies
                if(alive<2 || alive>3){
                    nextGen[i][j] = '.';
                }
                // lives
                else{
                    nextGen[i][j] = '*';
                }
            }
            
            // reset alive to zero for next index
            alive=0;
        }
    }
    
    return(nextGen);
}

//----------------------------------------------------------------------

/**
 function to output a state of the game to a file
 outFilename is the name of the output file
 numRows is the number of rows in the matrix
 numCols is the number of columns in the matrix
 matrix is the game state to output
 **/
void output(string outFilename, const int numRows, const int numCols, char** matrix){

    // open file
    ofstream ofs(outFilename.c_str());
    
    // row and column line
    ofs<<numRows<<' '<<numCols<<'\n';
    
    // output matrix to file
    for(int i=0; i<numRows; i++){
        for(int j=0; j<numCols; j++){
            ofs<<matrix[i][j];
        }
        ofs<<'\n';
    }
    
}

//----------------------------------------------------------------------

/**
 function to deallocate the dynamic memory of a matrix
 numRows is the number of rows in the matrix
 matrix is the matrix to deallocate
 **/
void dealloc(int numRows, char** matrix){
    // handle rows
    for(int i=0; i<numRows; i++){
        delete[] matrix[i];
    }
    // handle matrix
    delete[] matrix;
}

//----------------------------------------------------------------------

/**
 function to verify the setup function works as intended
 **/
void test_setup(){
    
    // rows, columns
    int r, c;
    // matrix
    char **m;
    
    string testfile = "test-input.txt";
    
    // call setup
    setup(testfile, r, c, m);
    
    // test row and column values
    assert(r==4);
    assert(c==5);
    
    // test matrix values
    for(int i = 0; i<r; i++){
        for(int j=0; j<c; j++){
            (i%2==0) ? (assert(m[i][j]=='.')) : (assert(m[i][j]=='*'));
        }
    }
    
    // deallocate memory
    dealloc(r, m);
    
}

//----------------------------------------------------------------------

/**
 function to verify the next generation calculation works as expected
 **/
void test_calculateNextGen(){
    
    // rows, columns
    int r, c;
    // matrix
    char **m;
    
    cout<<"\t\tTest test-supertest..."<<endl;
    
    string testfile = "test-supertest.txt";
    
    // call setup
    setup(testfile, r, c, m);
    
    // test row and column values
    assert(r==7);
    assert(c==6);
    
    char **nextGen = calculateNextGen(r, c, m);
    
    output("test-supertest-output.txt", r, c, nextGen);
    
    // manually verify
    char superExpected[7][6] = {{'.','.','.','.','.','.'},{'.','.','.','*','.','*'},{'.','.','.','*','*','.'},{'.','.','.','*','*','.'},{'*','*','.','.','*','.'},{'*','*','.','.','*','*'},{'*','*','.','.','*','*'}};
    
    for(int i=0; i<7; i++){
        for(int j=0; j<6; j++){
            assert(nextGen[i][j]==superExpected[i][j]);
        }
    }
    
    // deallocate memory
    dealloc(r, m);
    dealloc(r, nextGen);
    
    cout<<"\t\tTest test-supertest completed."<<endl;
    
    cout<<"\t\tTest test-square..."<<endl;
    
    testfile = "test-square.txt";
    
    // setup
    setup(testfile, r, c, m);
    
    // test row and column values
    assert(r==2);
    assert(c==2);
    
    // get next gen
    nextGen = calculateNextGen(r, c, m);
    
    // manually verify
    
    assert(nextGen[0][0]=='*');
    assert(nextGen[0][1]=='*');
    assert(nextGen[1][0]=='*');
    assert(nextGen[1][1]=='*');
    
    // output to file
    output("test-square-output.txt", r, c, nextGen);
    
    // deallocate memory
    dealloc(r, m);
    dealloc(r, nextGen);
    
    cout<<"\t\tTest test-square completed."<<endl;

}

//----------------------------------------------------------------------

/**
 function to verify the output function works as expected
 **/
void test_output(){
    
    // establish matrix for test

    int r = 4;
    int c = 5;
    char **m = new char*[r];
    for(int i=0;i<r;i++){
        m[i] = new char[c];
        for(int j=0;j<c;j++){
            (i%2==0) ? (m[i][j]='*') : (m[i][j]='.');

        }
    }

    string outpath = "test-output.txt";

    output(outpath, r, c, m);

    // stream to read in the test output file
    ifstream ifs;
    ifs.open(outpath);

    // check row and column numbers
    int row, col;
    ifs>>row>>col;
    assert(row==r);
    assert(col==c);

    // check matrix contents
    char nextChar;
    for(int i=0; i<r; i++){
        for(int j=0; j<c; j++){
            ifs>>nextChar;
            assert(nextChar==m[i][j]);
        }
    }

    dealloc(r, m);

}

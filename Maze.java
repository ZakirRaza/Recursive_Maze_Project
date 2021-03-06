package Assign_4;

import BasicIO.*;
import Media.*;
import java.lang.Math.*;

/**
 * @author Zakir Raza
 * @since March 29, 2021
 * @version 1.0
 */
public class Maze{
  private ASCIIDataFile input;
  private ASCIIOutputFile output;
  private char[][] maze;
  private int row, col;
  
  private int randomRowStart, randomColStart, randomRowEnd, randomColEnd;
  private int rowStartPos, colStartPos, rowEndPos, colEndPos;
  
  /**
   * This constructor is designed to contruct and maze
   * and solve it you using recursion.
   */
  public Maze(){
    input = new ASCIIDataFile();
    output = new ASCIIOutputFile();
    
    //Reads the maze size
    row = input.readInt();
    col = input.readInt();
    
    maze = new char[row][col];
    
    generateRandom();
    output.writeString("Start: " + randomRowStart + ", " + randomColStart);//Print Start cordinates of gretel
    
  }//Maze
  
  /**
   * This method is to read the maze and and store
   * the chracters in a char array.
   */
  public void readMaze(){
    output.newLine();
    for(int i = 0; i < maze.length; i++){
      String t = input.readLine();
      for(int j = 0; j < maze[i].length; j++){
        maze[i][j] = t.charAt(j);
        findStart(i, j);
        findEnd(i, j);
      }
    }
  }//readMaze
  
  /**
   * This method is to print the maze.
   */
  public void printMaze(){
    for(int i = 0; i < maze.length; i++){
      for(int j = 0; j < maze[i].length; j++){
        output.writeC(maze[i][j]);
      }
      output.newLine();
    }
  }//printMaze
  
  /**
   * This method is to randomly generate the starting
   * and ending positions.
   */
  public void generateRandom(){
    //Creates 2 starting points(row, col), and 2 end points (row, col)
    randomRowStart = (int)((row-2) * Math.random() + 1);
    randomColStart = (int)((col-2) * Math.random() + 1);
    randomRowEnd = (int)((row-2) * Math.random() + 1);
    randomColEnd = (int)((col-2) * Math.random() + 1);
    
    //If the staring and end are same, then it will re-generate the points
    if(randomRowStart == randomRowEnd && randomColStart == randomColEnd){
      randomRowStart = (int)((row-2) * Math.random() + 1);
      randomColStart = (int)((col-2) * Math.random() + 1);
      randomRowEnd = (int)((row-2) * Math.random() + 1);
      randomColEnd = (int)((col-2) * Math.random() + 1); 
    }
    
    readMaze();
    
    if(findPath(randomRowStart, randomColStart)){
      output.newLine();
      maze[rowStartPos][colStartPos] = 'G';
      output.newLine();
      output.writeString("Hansel is found at: (" + rowEndPos + ", " + colEndPos + ")");
      output.newLine();
    }
    else{
      output.writeString("Error");
    }
    input.close();
    printMaze();
  }
  
  /**
   * This method finds the start of the maze.
   * @params are the x, and y are the 
   */
  public void findStart(int x, int y){
    //If the start equals to wall (#), then it will choose a new start.
    if(maze[randomRowStart][randomColStart] == '#'){
      randomRowStart = (int)((row-2) * Math.random() + 1);
      randomColStart = (int)((col-2) * Math.random() + 1);
      
      if(randomRowEnd == randomRowStart && randomColEnd == randomColStart){
        findStart(x, y);
      }
      if(maze[randomRowStart][randomColStart] == '#'){
        findStart(x, y);
      }
    }
    //If G equals to a valid start, then that will be the starting position.
    else{
      maze[randomRowStart][randomColStart] = 'G';
      rowStartPos = randomRowStart;
      colStartPos = randomColStart;
    }
  }//findStart
  
  /**
   * This method finds the end of the maze.
   * @params are the x, and y index's passed.
   */
  public void findEnd(int x, int y){
    //If the end equals to a wall (#), then it will choose a new end.
    if(maze[randomRowEnd][randomColEnd] == '#'){
      randomRowEnd = (int)((row-2) * Math.random() + 1);
      randomColEnd = (int)((col-2) * Math.random() + 1);
      
      if(randomRowStart == randomRowEnd && randomColStart == randomColEnd){
        findEnd(x, y);
      }
      if(maze[randomRowEnd][randomColEnd] == '#'){
        findEnd(x, y);
      }
    }
    //If H equals to a valid end, then that will be the end position.
    else{
      maze[randomRowEnd][randomColEnd] = 'H';
      rowEndPos = randomRowEnd;
      colEndPos = randomColEnd;
    }
  }//findEnd
  
  /**
   * This method finds the path from the start to the end, recursively.
   * @param r the current row
   * @param c the current column 
   * @return false for a invalid path
   * @return true if the path is correct
   */
  public boolean findPath(int r, int c){
    if(maze[r][c] == 'H'){//Return if found Hansel
      return true;
    }
    //Check to see if we are on the right path
    if((maze[r][c]!= ' ') && maze[r][c]!= 'G' || maze[r][c] == '.'){
      return false;
    }
    maze[r][c] = '.';//".", if there is no solution
    
    //This recursive method will check right for a solution. If so, it will print ">"
    if(findPath(r, c+1)){
      maze[r][c] = '>';
      return true;
    }
    //This recursive method will check left for a solution. If so, it will print "<"
    if(findPath(r, c-1)){
      maze[r][c] = '<';
      return true;
    }
    //This recursive method will check up for a solution. If so, it will print "^"
    if(findPath(r-1, c)){
      maze[r][c] = '^';
      return true;
    }
    //This recursive method will check down for a solution. If so, it will print "v"
    if(findPath(r+1, c)){
      maze[r][c] = 'v';
      return true;
    }
    return false;
  }
  public static void main(String[] args) {Maze c = new Maze();}
}//Maze
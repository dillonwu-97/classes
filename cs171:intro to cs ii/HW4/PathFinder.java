/*

      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

      CODE WRITTEN BY OTHER STUDENTS. Dillon Wu

*/

/*
   Author:     Dillon Wu
   Written:       March 1, 2018

   Compilation:   javac PathFinder.java
   Execution:     java PathFinder

   This program finds the path to exit a maze by using queues and stacks. 
*/

import java.io.*;
import java.util.Scanner;
import java.util.ArrayDeque;

class Position{
	public int i;     //row
	public int j;     //column
	public char val;  //1, 0, or 'X'
	
	// reference to the previous position (parent) that leads to this position on a path
	Position parent;
	
	Position(int x, int y, char v){
		i=x; j = y; val=v;
	}
	
	Position(int x, int y, char v, Position p){
		i=x; j = y; val=v;
		parent=p;
	}

	// this replaces the character in the current position with a new character
	public void replace (char c) {
		val = c;
	}

	// this returns the position in the matrix of the object Position
	public String get() {
		return "[" + i + "][" + j + "] ";
	}

	// this returns the row
	public int rGet() {
		return i;
	}

	// this returns the column
	public int cGet() {
		return j;
	}

	// this returns the character
	public char charGet() {
		return val;
	}

	// this returns the parent
	public Position pGet() {
		return parent;
	}
	
}


public class PathFinder {
	
	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.err.println("***Usage: java PathFinder maze_file");
			System.exit(-1);
		}
		
		
		char [][] maze;
		maze = readMaze(args[0]);
		printMaze(maze);
		Position [] path = stackSearch(maze);
		System.out.println("stackSearch Solution:");
		printPath(path);
		printMaze(maze);
		

		char [][] maze2 = readMaze(args[0]);
		path = queueSearch(maze2);
		System.out.println("queueSearch Solution:");
		printPath(path);
		printMaze(maze2);


	}

	
	public static Position [] stackSearch(char [] [] maze){
				
		// A queue of all the positions I will traverse 
		ArrayDeque<Position> test = new ArrayDeque<Position>();

		// pushing the start position 
		test.push(new Position(0,0,'0'));

		int Tracker = 0;

		// keeping track of my positions 
		Position current;
		Position next;

		// this has a matrix of all the positions that have been visited so that I don't repeat myself
		int[][] checkedPos = new int[maze.length][maze.length]; 

		// I place a 0 in all of the positions in the matrix
		// 0 means that those positions have not been visited
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				checkedPos[i][j] = 0;
			}
		}

		// if there is no exit, then there is no path so I return null
		if (maze[maze.length-1][maze.length-1] != '0') {
			return null;
		}
		
		// if the stack is not empty, then this keeps on running
		while (!test.isEmpty()) {
			
			// store the value popped in the Position current
			current = test.pop();

			// Used for debugging
			//System.out.println("currently, the values are:");
			//System.out.println(current.rGet());
			//System.out.println(current.cGet());
			//System.out.println(current.charGet());

			// if I am in the last position, then break out of the while loop
			if (current.rGet() == maze.length-1 && current.cGet() == maze.length-1) {
				maze[current.rGet()][current.cGet()] ='X';
				perm = current; 
				break;		
			}

			// this puts the neighbors into the queue
			// this says if there is something below me, then I can go down
			// in other words, it asks if there is a barrier below me
			// Additionally, it checks to see if the position has been visited before already
			if (sEdge(current, maze) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = down(current, maze);
				// checks to see if the position I am adding is in the maze and if it has a 0
				// if it doesn't have a 0, then there is no point in adding the position
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.push(next);
					//System.out.println("Down");
					//System.out.println("down next row number:" + next.rGet());
					//System.out.println("down next col number:" + next.cGet());
				}
			}

			// does the same as above, except instead of adding the position below the current
			// position, it adds the one above it
			if (nEdge(current) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = up(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.push(next);
					//System.out.println("Up");
					//System.out.println("up next row number:" + next.rGet());
					//System.out.println("up next col number:" + next.cGet());
				}
			}

			// adds the position to the right
			if (eEdge(current, maze) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = right(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.push(next);
					//System.out.println("Right");
					//System.out.println("right next row number:" + next.rGet());
					//System.out.println("right next col number:" + next.cGet());
				}
			}

			// adds the position to the left
			if (wEdge(current) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = left(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.push(next);
					//System.out.println("Left");
					//System.out.println("left next row number:" + next.rGet());
					//System.out.println("left next col number:" + next.cGet());
				}
			}

			// this changes value of the current position in the matrix of positions visited
			// it changes the value from 0 to 1
			// 1 means the position has already been visited
			checkedPos[current.rGet()][current.cGet()] = 1;
			
			// A tracker is used to track how big I should make the array
			Tracker++;
		}

		// this creates an array of all the positions but in reverse order
		Position[] result = new Position[Tracker];
		int Tracker2 = 0;
		Position temp;
		temp = perm;
		// this puts in all those positions by backtracking
		while (temp != null) {
			result[Tracker2] = temp;
			temp = temp.pGet();
			Tracker2++;
		}

		// this reverses the order of the previous array to determine the positions that need
		// to be traversed from 0 to the end
		int Tracker3 = 0;
		Position[] resultFinal = new Position[Tracker];
		for (int i = Tracker2-1; i >= 0; i--) {
			resultFinal[Tracker3] = result[i];
			maze[result[i].rGet()][result[i].cGet()] = 'X';
			Tracker3++;
		}

		return resultFinal;		
	}
	
	// this is similar to above but uses a queue instead of a stack
	public static Position [] queueSearch(char [] [] maze){
		
		// A queue of all the positions I will try
		ArrayDeque<Position> test = new ArrayDeque<Position>();

		// adding the start position
		test.add(new Position(0,0,'0'));

		// this keeps track of how big I should make the array
		int Tracker = 0;

		// A matrix of visited positions
		int[][] checkedPos = new int[maze.length][maze.length]; 


		// keeping track of my positions
		Position current;
		Position next;

		// 0 means positions have not yet been visited
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				checkedPos[i][j] = 0;
			}
		}

		// if the exit is blocked, then there is no path
		if (maze[maze.length-1][maze.length-1] != '0') {
			return null;
		}
		
		// keep running the program until there are no more positions that can be added
		while (!test.isEmpty()) {
			
			// dequeues the first element in the queue
			current = test.removeFirst();
			
			//debugging
			//System.out.println("currently, the values are:");
			//System.out.println(current.rGet());
			//System.out.println(current.cGet());
			//System.out.println(current.charGet());

			// checks to see if the current position is the exit
			if (current.rGet() == maze.length-1 && current.cGet() == maze.length-1) {
				maze[current.rGet()][current.cGet()] ='X';
				perm = current;
				break;
			}

			// putting neighbors into the queue
			// this says if there is something below me, then I can go down
			// in other words, it asks if there is a barrier below me
			if (sEdge(current, maze) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = down(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.add(next);
					//System.out.println("Down");
					//System.out.println("down next row number:" + next.rGet());
					//System.out.println("down next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}

			// checks if there is a wall above me
			if (nEdge(current) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = up(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.add(next);
					//System.out.println("Up");
					//System.out.println("up next row number:" + next.rGet());
					//System.out.println("up next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}

			// checks if there is a wall to my right
			if (eEdge(current, maze) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = right(current, maze);

				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.add(next);
					//System.out.println("Right");
					//System.out.println("right next row number:" + next.rGet());
					//System.out.println("right next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}

			// checks if there is a wall to my left
			if (wEdge(current) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = left(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.add(next);
					//System.out.println("Left");
					//System.out.println("left next row number:" + next.rGet());
					//System.out.println("left next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}
			
			// 1 means the position has been visited
			checkedPos[current.rGet()][current.cGet()] = 1;
			Tracker++;

		}

		// adds all the positions into the queue, but in reverse order
		Position[] result = new Position[Tracker];
		int Tracker2 = 0;
		Position temp;
		temp = perm;
		while (temp != null) {
			result[Tracker2] = temp;
			temp = temp.pGet();
			Tracker2++;
		}

		// reverses the positions so that it is in the right order
		int Tracker3 = 0;
		Position[] resultFinal = new Position[Tracker];
		for (int i = Tracker2-1; i >= 0; i--) {
			resultFinal[Tracker3] = result[i];
			maze[result[i].rGet()][result[i].cGet()] = 'X';
			Tracker3++;
		}
		
		return resultFinal;		

	}
	
	// This prints the path to the exit
	// if there is nothing in the array, that means there is no path
	// Additionally, if the return value is null, then just print out [] to indicate there is no path
	public static void printPath(Position [] path){
		String result = "Path: ";
		try {
			if (path[0] == null) {
				result = result + "[]";
			}
			for (int i = 0; i < path.length; i++) {
				if (path[i] == null) {
					break;
				}
				result = result + path[i].get();
			}
			System.out.println(result);
		}
		catch (NullPointerException e) {
			System.out.println("Path: []");
		}
	}

	// this gives me a new Position that is above the current position
	public static Position up(Position current, char[][]maze) {
		return new Position(current.rGet()-1, current.cGet(), maze[current.rGet()-1][current.cGet()], current);
	}

	// this gives me a new Position that is below the current position
	public static Position down(Position current, char[][]maze) {
		return new Position(current.rGet()+1, current.cGet(), maze[current.rGet()+1][current.cGet()], current);
	}

	// this gives me a new Position that is to the right of the current position
	public static Position right(Position current, char[][]maze) {
		return new Position(current.rGet(), current.cGet()+1, maze[current.rGet()][current.cGet()+1], current);
	}

	// this gives me a new Position that is to the left of the current position
	public static Position left(Position current, char[][]maze) {
		return new Position(current.rGet(), current.cGet()-1, maze[current.rGet()][current.cGet()-1], current);
	}

	// checks if the position is in the maze
	// if it is in the maze, then return true else return false
	// it checks if the position is in the maze by seeing if the row and column is less than 0 or
	//greater than the maze length-1
	public static boolean isInMaze(Position current, char[][]maze) {
		if (current.rGet() >= 0 && current.rGet() < maze.length && current.cGet() >= 0 &&
			current.cGet() < maze.length) {
			return true;
		} else {
			return false;
		}
	}

    // if the position can be traversed, return true, i.e. if the position has a 0
    // if it has a 1, then return false
    public static boolean isGo (Position current) {
    	if (current.charGet() == '0') {
    		return true;
    	} else {
    		return false;
    	}
    }

	// north edge
	// returns false when the current row is 0 because means you can't go up
	public static boolean nEdge (Position current) {
		if (current.rGet() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	// south edge
	// returns false when current row is equal to the length of the maze - 1
	public static boolean sEdge (Position current, char[][]maze) {
		if (current.rGet() == maze.length-1) {
			return false;
		} else {
			return true;
		}
	}

	// west edge
	// returns false when you are on the left wall, i.e. when the column number is 0
	public static boolean wEdge (Position current) {
		if (current.cGet() == 0) {
			return false;
		} else {
			return true;
		}
	}

	// east edge
	// returns false when you are on the right edge, i.e. when the column number is maze.length-1
	public static boolean eEdge (Position current, char[][] maze) {
		if (current.cGet() == maze.length-1) {
			return false;
		} else {
			return true;
		}
	}

	// this creates a permanent position to store the temporary position called current in
	public static Position perm;

	
	/**
	 * Reads maze file in format:
	 * N  -- size of maze
	 * 0 1 0 1 0 1 -- space-separated 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static char [][] readMaze(String filename) throws IOException{
		char [][] maze;
		Scanner scanner;
		try{
			scanner = new Scanner(new FileInputStream(filename));
		}
		catch(IOException ex){
			System.err.println("*** Invalid filename: " + filename);
			return null;
		}
		
		int N = scanner.nextInt();
		scanner.nextLine();
		maze = new char[N][N];
		int i=0;
		while(i < N && scanner.hasNext()){
			String line =  scanner.nextLine();
			String [] tokens = line.split("\\s+");
			int j = 0;
			for (; j< tokens.length; j++){
				maze[i][j] = tokens[j].charAt(0);
			}
			if(j!=N){
				System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
				return null;
			}
			i++;
		}
		if(i!=N){
			System.err.println("*** Invalid file: has wrong number of rows: " + i);
			return null;
		}
		return maze;
	}
	
	public static void printMaze(char[][] maze){
		
		if(maze==null || maze[0] == null){
			System.err.println("*** Invalid maze array");
			return;
		}
		
		for(int i=0; i< maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				System.out.print(maze[i][j] + " ");	
			}
			System.out.println();
		}
		
		System.out.println();
	}

}

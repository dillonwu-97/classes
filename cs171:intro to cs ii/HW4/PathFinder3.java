/**
 * Starter code for the Maze path finder problem.
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayDeque;

/*
 * Recursive class to represent a position in a path
 */
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

	public void replace (char c) {
		val = c;
	}

	public void replace (int a, int b, char c) {
		i = a;
		j = b;
		val = c;
	}

	public void replace (int a, int b, char c, Position par) {
		i = a;
		j = b;
		val = c;
		parent = par;
	}

	public String get() {
		return "[" + i + "][" + j + "]";
	}

	public int rGet() {
		return i;
	}

	public int cGet() {
		return j;
	}

	public char charGet() {
		return val;
	}

	public Position pGet() {
		return parent;
	}
	
}


public class PathFinder3 {
	
	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.err.println("***Usage: java PathFinder maze_file");
			System.exit(-1);
		}
		
		
		char [][] maze;
		maze = readMaze(args[0]);
		System.out.println(maze[maze.length-1][maze.length-1]);
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
		// todo: your path finding algorithm here using the stack to manage search list
		// your algorithm should mark the path in the maze, and return array of Position 
		// objects coressponding to path, or null if no path found
				
		// A queue of all the locations I will try
		ArrayDeque<Position> test = new ArrayDeque<Position>();

		// adding the start position
		test.push(new Position(0,0,'0'));

		int Tracker = 0;

		// keeping track of my positions
		Position current;
		Position next;

		// if it is not visited, the 0
		// if it was visited and it was a 1, then it's a 1
		// if it was visited and it was originally a 0, then it's a 2
		int[][] checkedPos = new int[maze.length][maze.length]; 

		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				checkedPos[i][j] = 0;
			}
		}
		
		while (!test.isEmpty()) {
			current = test.pop();

			/*
			if (current.charGet() == '0') {
				result[Tracker] = current;
				Tracker++;
			}
			*/
			//System.out.println("currently, the values are:");
			//System.out.println(current.rGet());
			//System.out.println(current.cGet());
			//System.out.println(current.charGet());
			// true means the position has been visited


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
					test.push(next);
					//System.out.println("Down");
					//System.out.println("down next row number:" + next.rGet());
					//System.out.println("down next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}
			if (nEdge(current) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = up(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.push(next);
					//System.out.println("Up");
					//System.out.println("up next row number:" + next.rGet());
					//System.out.println("up next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}
			if (eEdge(current, maze) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = right(current, maze);

				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.push(next);
					//System.out.println("Right");
					//System.out.println("right next row number:" + next.rGet());
					//System.out.println("right next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}
			if (wEdge(current) == true && checkedPos[current.rGet()][current.cGet()] == 0) {
				next = left(current, maze);
				if (isInMaze(current, maze) == true && isGo(current) == true) {
					test.push(next);
					//System.out.println("Left");
					//System.out.println("left next row number:" + next.rGet());
					//System.out.println("left next col number:" + next.cGet());
					//maze[current.rGet()][current.cGet()] ='X';
				}
			}
			checkedPos[current.rGet()][current.cGet()] = 1;
		}


		Position[] result = new Position[1000];
		int Tracker2 = 0;
		Position temp;
		temp = perm;
		while (temp != null) {
			result[Tracker2] = temp;
			temp = temp.pGet();
			Tracker2++;
		}

		int Tracker3 = 0;
		Position[] resultFinal = new Position[1000];
		for (int i = Tracker2-1; i >= 0; i--) {
			resultFinal[Tracker3] = result[i];
			maze[result[i].rGet()][result[i].cGet()] = 'X';
			Tracker3++;
		}

		return resultFinal;		
	}
	



	// changes the character to tell me that it has been visited
	/*
	public static char change (Position current, char x, char[][] maze) {
		char temp = maze[current.rGet()-1][current.cGet()-1];
		maze[current.rGet()-1][current.cGet()-1] = x;
		return temp;
		
	}
	*/

	public static Position up(Position current, char[][]maze) {
		return new Position(current.rGet()-1, current.cGet(), maze[current.rGet()-1][current.cGet()], current);
	}

	public static Position down(Position current, char[][]maze) {
		return new Position(current.rGet()+1, current.cGet(), maze[current.rGet()+1][current.cGet()], current);
	}


	public static Position right(Position current, char[][]maze) {
		return new Position(current.rGet(), current.cGet()+1, maze[current.rGet()][current.cGet()+1], current);
	}


	public static Position left(Position current, char[][]maze) {
		return new Position(current.rGet(), current.cGet()-1, maze[current.rGet()][current.cGet()-1], current);
	}

	// checks if the position is in the maze
	public static boolean isInMaze(Position current, char[][]maze) {
		if (current.rGet() >= 0 && current.rGet() < maze.length && current.cGet() >= 0 &&
			current.cGet() < maze.length) {
			return true;
		} else {
			return false;
		}
	}

	// creates a clone of the original maze
    public static char[][] clone(char[][] maze) {
		char[][] mazeCopy = new char[maze.length][maze.length]; 
		for (int i = 0; i < maze.length; i++) {
	   		for (int j = 0; j < maze.length; j++) {
				mazeCopy[i][j] = maze[i][j];
	   		}
		}
		return mazeCopy; 
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

	public static int size(char[][] maze) {
		return maze.length;
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
	public static boolean sEdge (Position current, char[][]maze) {
		if (current.rGet() == maze.length-1) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean wEdge (Position current) {
		if (current.cGet() == 0) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean eEdge (Position current, char[][] maze) {
		if (current.cGet() == maze.length-1) {
			return false;
		} else {
			return true;
		}
	}

	public static Position perm;

	
	public static Position [] queueSearch(char [] [] maze){
		
		// A queue of all the locations I will try
		ArrayDeque<Position> test = new ArrayDeque<Position>();

		// adding the start position
		test.add(new Position(0,0,'0'));

		int Tracker = 0;
		int[][] checkedPos = new int[maze.length][maze.length]; 


		// keeping track of my positions
		Position current;
		Position next;

		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				checkedPos[i][j] = 0;
			}
		}
		
		while (!test.isEmpty()) {
			current = test.removeFirst();
			
			//System.out.println("currently, the values are:");
			//System.out.println(current.rGet());
			//System.out.println(current.cGet());
			//System.out.println(current.charGet());

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
			// true means the position has been visited
			checkedPos[current.rGet()][current.cGet()] = 1;
		}

		Position[] result = new Position[1000];
		int Tracker2 = 0;
		Position temp;
		temp = perm;
		while (temp != null) {
			result[Tracker2] = temp;
			temp = temp.pGet();
			Tracker2++;
		}

		int Tracker3 = 0;
		Position[] resultFinal = new Position[1000];
		for (int i = Tracker2-1; i >= 0; i--) {
			resultFinal[Tracker3] = result[i];
			maze[result[i].rGet()][result[i].cGet()] = 'X';
			Tracker3++;
		}

		return resultFinal;		

	}
	
	public static void printPath(Position [] path){
		// This prints the path to the exit
		for (int i = 0; i < path.length; i++) {
			if (path[i] == null) {
				break;
			}
			System.out.println(path[i].get());

		}


	}
	
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

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

	public void replace (int a, int b, char c, Position parent) {
		i = a;
		j = b;
		val = c;
		this.parent = parent;
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

public class HW4Sandbox {
	public static void main(String[] args) {
		//Position test = new Position(0,0,'0');
		//System.out.println(test.charGet());
		//System.out.println(test.rGet());
		//System.out.println(test.cGet());

		Position start = new Position(0,0,'0');
		Position moveD = new Position(1,0,'X',start); // this is the Position that goes down
		Position moveU = new Position(0,0,'X',moveD); // this is the Position that goes up
		Position moveR = new Position(0,0,'X',start); // this is the Position that goes right
		Position moveL = new Position(0,0,'X',start); // this is the Position that goes left
		Position move = new Position(0,0,'0',start); // this keeps track of the current position

		System.out.println("moveD before");

		System.out.println(moveD.charGet()); // should return X
		System.out.println(moveD.rGet()); // should return 0
		System.out.println(moveD.cGet()); // should return 0

		moveD.replace(move.rGet() + 1, move.cGet(), '0', move);
		moveR.replace(move.rGet(), move.cGet() + 1, '1', move);

		System.out.println("move");

		System.out.println(move.charGet()); // should return 0
		System.out.println(move.rGet()); // should return 0
		System.out.println(move.cGet()); // should return 0

		System.out.println("moveD after");

		System.out.println(moveD.charGet()); // should return 0
		System.out.println(moveD.rGet()); // should return 1
		System.out.println(moveD.cGet()); // should return 0

		System.out.println("moveR");

		System.out.println(moveR.charGet()); // should return 1
		System.out.println(moveR.rGet() + 1); // should return 1
		System.out.println(moveR.rGet()); // should return 0
		System.out.println(moveR.cGet()); // should return 1

		System.out.println("Testing deque");

		ArrayDeque<Position> test = new ArrayDeque<>();
		test.add(start);
		System.out.println((test.peek()).charGet());

		System.out.println("new stuff");
		System.out.println(north(move).rGet());
		System.out.println(move.rGet());
		System.out.println(sEdge(move));
		System.out.println((moveD.pGet().rGet()));
		System.out.println((moveD.pGet().cGet()));
		System.out.println((moveD.pGet().charGet()));

		System.out.println("almost got it");

		Position one = new Position (3,0,'0');
		Position two;
		two = north(one); // parent position of two should be one
		System.out.println((two.pGet()).rGet()); // should return 3
		Position three;
		three = north(two); // parent position of three should be two
		System.out.println((three.pGet()).rGet()); // should return two


	}

	public static Position north(Position current) {
		return new Position(current.rGet()-1, current.cGet(), '0', current);
	}

	public static boolean sEdge (Position current) {
		if (current.rGet() == 9) {
			return false;
		} else {
			return true;
		}
	}

		// if there are walls on all three sides, this means I need to backtrack until
	// I hit a place where there are at least two openings
	// a wall can be the edge, or a 1
	// I can do this with a tracker that records the number of walls there are
	/*
	public static boolean walls (Position current, char[][] maze) {
		int tracker = 0;
		if (nEdge(current) == false) {
			tracker++;
		}
		if (sEdge(current,maze) == false) {
			tracker++;
		}
		if (eEdge(current,maze) == false) {
			tracker++;
		}
		if (wEdge(current) == false) {
			tracker++;
		}
		if (isGo(maze[current.rGet()+1][current.cGet()]) == false || ) {
			tracker++;
		}
		if (isGo(maze[current.rGet()-1][current.cGet()]) == false) {
			tracker++;
		}
		if (isGo(maze[current.rGet()][current.cGet()]+1) == false) {
			tracker++;
		}
		if (isGo(maze[current.rGet()][current.cGet()]-1) == false) {
			tracker++;
		}
	}
	*/
}

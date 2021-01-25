/**
 * Simple Turtle library
 * Version 1.03 - 2016.11.01
 * @author Davide Fossati <davide@fossati.us>
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Turtle {
	
	public static final int WINDOW_WIDTH = 1000;
	public static final int WINDOW_HEIGHT = 1000;
	public static final int WORLD_WIDTH = 5000;
	public static final int WORLD_HEIGHT = 5000;
	
	public Turtle() {
		if (world == null) {
			initWorld();
		}
		if (allTurtles == null) {
			allTurtles = new ArrayList<Turtle>();
		}
		allTurtles.add(this);
		x = WINDOW_WIDTH / 2;
		y = WINDOW_HEIGHT / 2;
		dir = 0;
		turtleColor = Color.BLACK;
		delay = 100;
		turtleVisible = true;
		penDown = true;
	}

	private void initWorld() {
		JFrame worldFrame = new JFrame("Turtle");
		worldFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		world = new PaintArea();
		worldFrame.add(world);
		worldFrame.pack();
		worldFrame.setVisible(true);
	}

	/*
	 * Moves the turtle forward.
	 * @param steps the distance traveled by the turtle (1 step = 1 pixel)
	 */
	public void forward(double steps) {
		double x0 = x;
		double y0 = y;
		x += steps * Math.cos(dir);
		y -= steps * Math.sin(dir);
		if (penDown) {
			world.drawLine(x0, y0, x, y, turtleColor);
		}
		if (delay > 0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		world.repaint();
	}
	
	/*
	 * Moves the turtle backward.
	 * @param steps the distance traveled by the turtle (1 step = 1 pixel)
	 */
	public void backward(double steps) {
		forward(-steps);
	}

	/*
	 * Rotates the turtle counter-clockwise.
	 * @param degrees the rotation angle (in degrees)
	 */
	public void left(double degrees) {
		dir += degrees * Math.PI / 180;
		double pi2 = 2 * Math.PI;
		while (dir >= pi2) {
			dir -= pi2;
		}
		while (dir < 0) {
			dir += pi2;
		}
		if (delay > 0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		world.repaint();
	}

	/*
	 * Rotates the turtle clockwise.
	 * @param degrees the rotation angle (in degrees)
	 */
	public void right(double degrees) {
		left(-degrees);
	}
	
	/*
	 * Raises the turtle's pen. When the pen is up, the turtle does not draw lines on the screen.
	 */
	public void penup() {
		penDown = false;
	}
	
	/*
	 * Lowers the turtle's pen. When the pen is down, the turtle draws lines on the screen.
	 */
	public void pendown() {
		penDown = true;
	}
	
	/*
	 * Sets the turtle's delay between moves. The lower the delay, the faster the turtle. Default: 100 ms
	 * @param delay the amount of delay (in milliseconds)
	 */
	public void delay(int delay) {
		this.delay = delay;
	}
	
	/*
	 * Hides the turtle so it is not visible on the screen.
	 */
	public void hideturtle() {
		turtleVisible = false;
		world.repaint();
	}
	
	/*
	 * Shows the turtle so it is visible on the screen.
	 */
	public void showturtle() {
		turtleVisible = true;
		world.repaint();
	}
	
	/*
	 * Sets the color of the turtle.
	 * @param color a string representation of the color. Examples: "red", "green", "blue", "yellow", etc.
	 */
	public void color(String color) {
		try {
			turtleColor = (Color)Class.forName("java.awt.Color").getField(color).get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		world.repaint();
	}

	/*
	 * Sets the color of the turtle.
	 * @param red the red component of the color (0 <= red <= 255)
	 * @param green the blue component of the color (0 <= green <= 255)
	 * @param blue the blue component of the color (0 <= blue <= 255)
	 */
	public void color(int red, int green, int blue) {
		turtleColor = new Color(red, green, blue);
		world.repaint();
	}

	/*
	 * Flood-fills the area under the turtle with the turtle's current color.
	 */
	public void fill() {
		world.fill(x, y, turtleColor);
		world.repaint();
	}
	
	private static PaintArea world;
	
	private static ArrayList<Turtle> allTurtles;

	private double x; // x position of the turtle

	private double y; // y position of the turtle

	private double dir; // direction of the turtle in radians
	
	private Color turtleColor; // color of the turtle

	private long delay; // delay of the turtle
	
	private boolean turtleVisible; // true if the turtle is visible
	
	private boolean penDown; // true if the pen is down
	
	class PaintArea extends JPanel {

		private static final long serialVersionUID = 1L;

		public PaintArea() {
			this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
			drawingBuf = new BufferedImage(WORLD_WIDTH, WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = drawingBuf.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
		}

		public void drawLine(double x1, double y1, double x2, double y2, Color c) {
			Graphics2D g = drawingBuf.createGraphics();
			g.setColor(c);
			g.drawLine((int)Math.round(x1), (int)Math.round(y1), (int)Math.round(x2), (int)Math.round(y2));
		}

		public void fill(double x0, double y0, Color foreground) {
			int ix0 = (int)Math.round(x0);
			int iy0 = (int)Math.round(y0);
			int fgc = foreground.getRGB();
			int bgc = drawingBuf.getRGB(ix0, iy0);
			
			if (fgc == bgc) {
				return;
			}
			
			Queue<Point> q = new ArrayDeque<Point>();
			q.add(new Point(ix0, iy0));
			
			while (!q.isEmpty()) {
				Point p = q.remove();
				if (p.x >=0 && p.x < drawingBuf.getWidth()
						&& p.y >=0 && p.y < drawingBuf.getHeight()
						&& drawingBuf.getRGB(p.x, p.y) == bgc) {
					drawingBuf.setRGB(p.x, p.y, fgc);
					q.add(new Point(p.x-1, p.y));
					q.add(new Point(p.x+1, p.y));
					q.add(new Point(p.x, p.y-1));
					q.add(new Point(p.x, p.y+1));
				}
			}
		}
		

		public void paint(Graphics g) {
			g.drawImage(drawingBuf, 0, 0, Color.WHITE, null);
			for (Turtle t : allTurtles) {
				if (t.turtleVisible) {
					// draw the turtle
					g.setColor(t.turtleColor);
					double tw = 7;
					double th = 10;
					double sind = Math.sin(t.dir);
					double cosd = Math.cos(t.dir);
					int tx1 = (int)(t.x + th * cosd);
					int ty1 = (int)(t.y - th * sind);
					int tx2 = (int)(t.x - th * cosd - tw * sind);
					int ty2 = (int)(t.y + th * sind - tw * cosd);
					int tx3 = (int)(t.x - th * cosd + tw * sind);
					int ty3 = (int)(t.y + th * sind + tw * cosd);
					g.drawLine(tx1, ty1, tx2, ty2);
					g.drawLine(tx1, ty1, tx3, ty3);
					g.drawLine(tx2, ty2, tx3, ty3);
				}
			}
		}

		private BufferedImage drawingBuf;

	}
}

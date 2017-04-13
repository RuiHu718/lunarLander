// LunarLander.java
/*
 * Builds the Lunar Lander game -- CS106a. Play() runs one game, tick() updates the physics
 * for one tick of the clock, draw() updates the graphics.
*/

import stanford.cslib.*;

public class LunarLander {
	// Constants
	public static final boolean TESTING = false;	// true -> main() calls test()
	public static final int ALTITUDE_INIT = 400;	// height at start
	public static final int SHIP_X = 20;		// x value for ship drawing
	public static final int SHIP_SIZE = 100;	// ship width and height
	
	public static final double FUEL_INIT = 100;	// fuel at start
	public static final double TICKS = 30;	// (30) ticks per second
	public static final double GRAVITY_TICK =   15 / TICKS;	// (0.5) velocity change down per tick
	
	// When firing the rocket...
	public static final double ROCKET_TICK = 30 / TICKS;	// (1.0) velocity change up per tick
	public static final double FUEL_TICK = 30 / TICKS;		// (1.0) fuel used per tick
	
	// Ship image filenames
	public static final String SHIP = "lunar.png";
	public static final String SHIP_FIRING = "lunar-firing.png";
	public static final String SHIP_CRASHED = "lunar-crashed.png";

	
	// Instance variables
	private double myAltitude;	// number of pixels between bottom of ship and bottom of screen
	private double myVelocity;	// change in height per tick (pos or neg)
	private double myFuel;		// amount of fuel

	
	// Instance variables for graphics objects
	private DWindow window;
	private DImage image;
	private DString altitude;
	private DString velocity;
	private DString fuel;
	
	
	// Graphics Consants
	public static final int WINDOW_WIDTH = 300;
	public static final int WINDOW_HEIGHT = 500;
	
	// DString constants
	public static final int STRING_X = 150;
	public static final int STRING_Y = 200;
	public static final int SPACE = 50;	// vertical space between DStrings
	public static final int STRING_WIDTH = 200;
	public static final int STRING_HEIGHT = 20;
	

	/*
	 Sets up the lunar lander -- window, graphics objects, and physics.
	*/
	public LunarLander() {
		myAltitude = ALTITUDE_INIT;
		myVelocity = 0;
		myFuel = FUEL_INIT;
		window = new DWindow("LunarLander", WINDOW_WIDTH, WINDOW_HEIGHT);
		image = new DImage(SHIP_X, 0, SHIP_SIZE, SHIP_SIZE, SHIP);
		window.add(image);
		altitude = new DString(STRING_X, STRING_Y, STRING_WIDTH, STRING_HEIGHT, (int) myAltitude + "");
		window.add(altitude);
		velocity = new DString(STRING_X, STRING_Y+SPACE, STRING_WIDTH, STRING_HEIGHT, (int) myVelocity*TICKS + "");
		window.add(velocity);
		fuel = new DString(STRING_X, STRING_Y+2*SPACE, STRING_WIDTH, STRING_HEIGHT, (int) myFuel + "");
		window.add(fuel);			
	}
	
	public void reset(){
		myAltitude = ALTITUDE_INIT;
		myVelocity = 0;
		myFuel = FUEL_INIT;
		image.setLocation(SHIP_X, 0);
		altitude.setText((int) myAltitude + "");
		velocity.setText((int) myVelocity*TICKS + "");
		fuel.setText((int) myFuel+"");		
	}
	
	public void tick(boolean firing){
		if (firing){
			myVelocity = myVelocity - GRAVITY_TICK + ROCKET_TICK;
			myFuel = myFuel - FUEL_TICK;
			myAltitude = myAltitude + myVelocity;
		} else {
			myVelocity = myVelocity - GRAVITY_TICK;
			myAltitude = myAltitude + myVelocity;					
		}
	}
	
	public void draw(boolean firing){
		int currentY = (int) (WINDOW_HEIGHT - myAltitude - SHIP_SIZE);
		image.setLocation(SHIP_X, currentY);
		if (firing) {
			image.setImage(SHIP_FIRING);
		}
		altitude.setText((int) myAltitude + "");
		velocity.setText((int) myVelocity*TICKS + "");
		fuel.setText((int) myFuel+"");			
	}
	
	public void print(){
		System.out.println("altitude:" + myAltitude + "velocity:" + myVelocity + "fuel:" + myFuel);
	}
	
	
	/*
	 Plays one game -- initial setup, game loop until landing, scoring.
	*/
	public void play() {
		reset();
		draw(false);
		while (myAltitude >= 1) {
			boolean firing = window.isMousePressed() && (myFuel > FUEL_TICK);
			tick(firing);
			draw(firing);
			DWindow.waitSeconds(1.0 / TICKS);
		}
		
		double speed = -1 * myVelocity * TICKS;
		if (speed >= 100) {
			image.setImage(SHIP_CRASHED);
		}
		System.out.println("Score is: " + (100-speed)*(myFuel+1));
		window.waitClick();
	}
	
	
	/*
	 Debugging utility.
	 Try 4 ticks down, drawing and printing the state for each.
	*/
	public void test() {
		tick(false);
		print();
		draw(false);
		tick(true);
		print();
		draw(true);
		tick(false);
		print();
		draw(false);
		tick(false);
		print();
		draw(false);
	}
	
	public static void main(String[] args) {
		LunarLander lunar = new LunarLander();
		
		// Run the test() method if TESTING constant is true
		if (TESTING) {
			lunar.test();
		}
		else {
		// Otherwise play the game again and again
			
			while (true) {
				lunar.play();
			}
		
		}
	}
}
		

        
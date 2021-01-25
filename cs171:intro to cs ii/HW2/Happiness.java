public abstract class Happiness {
	
	// These are all doubles that determine how happy I am;
	// Happy sums to 100; everything below happy sums to 25
	// Review: initializing variables
	private double happy;
	private double grade;
	private double money;
	private double time;
	private double ath;
	protected int extra;

	// Review: this is a constructor
	public Happiness (double happy, double grade, double money, double time, double ath) {
		this.happy = happy;
		this.grade = grade;
		this.money = money;
		this.time = time;
		this.ath = ath;
	}


}
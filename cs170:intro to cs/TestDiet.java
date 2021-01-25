public class TestDiet {
	

	public static void main (String[] args) {
 		Ingredient flour = new Ingredient("flour", "g", 0.11, 0.012, 0.75);
 		System.out.println(flour.nutritionalValues(100)); 
 		
 		IngredientDatabase db = new IngredientDatabase();
 		
 		// testing if #3 works
 		System.out.println(db.findIngredient("flour"));

 		System.out.println(db.findIngredient("egsssss"));

 		// testing #4 
 		// sample recipe
		Recipe cake = new Recipe("chocolate cake");
		cake.addIngredient(db.findIngredient("egg"), 4);
		cake.addIngredient(db.findIngredient("sugar"), 100);
 		cake.addIngredient(db.findIngredient("butter"), 100);
 		cake.addIngredient(db.findIngredient("flour"), 200);
 		cake.addIngredient(db.findIngredient("milk"), 50);
		cake.addIngredient(db.findIngredient("cocoa"), 75);
		
		System.out.println(cake.computeProtein());
		System.out.println(cake.computeFat());
		System.out.println(cake.computeCarbohydrates());
		System.out.println(cake.computeCalories());

		System.out.println(cake);
		System.out.println(cake.nutritionalValues());
		System.out.println();

		// Recipe #1
		// Please do not actually try to make this
		Recipe aPudding = new Recipe("almond pudding");
		aPudding.addIngredient(db.findIngredient("egg"), 2);
		aPudding.addIngredient(db.findIngredient("sugar"), 50);
 		aPudding.addIngredient(db.findIngredient("flour"), 30);
 		aPudding.addIngredient(db.findIngredient("milk"), 20);
		aPudding.addIngredient(db.findIngredient("almonds"), 10);

		System.out.println(aPudding.toString());
		System.out.println(aPudding.nutritionalValues());
		System.out.println();

		// Recipe #2
		Recipe omelette = new Recipe("omolette");
		omelette.addIngredient(db.findIngredient("egg"), 4);
		omelette.addIngredient(db.findIngredient("ham"), 20);
		omelette.addIngredient(db.findIngredient("oil"), 20);
		omelette.addIngredient(db.findIngredient("mincemeat"), 30);
		omelette.addIngredient(db.findIngredient("cucumber"), 10);

		System.out.println(omelette.toString());
		System.out.println(omelette.nutritionalValues());
		System.out.println();

		// Recipe #3
		Recipe cereal = new Recipe("cereal");
		cereal.addIngredient(db.findIngredient("milk"), 50);
		cereal.addIngredient(db.findIngredient("cranberries"), 15);
		cereal.addIngredient(db.findIngredient("blackberries"), 15);
		cereal.addIngredient(db.findIngredient("cornflakes"), 40);

		System.out.println(cereal.toString());
		System.out.println(cereal.nutritionalValues());


	}

}

class Ingredient {

	// attributes
	// 2.1
	private String ingredient;
	private String unit;
	private double protein;
	private double fat;
	private double carbohydrates; 

	// Constructor
	// 2.2

	public Ingredient(String ingredient, String unit, double protein, double fat, double carbs) {
		this.ingredient = ingredient;
		this.unit = unit;
		this.protein = protein;
		this.fat = fat;
		this.carbohydrates = carbs;
	}

	// Getter methods
	// 2.3

	public String getName() {
		return ingredient;
	}

	public String getUnit() {
		return unit;
	}

	public double getProtein() {
		return protein;
	}

	public double getFat() {
		return fat;
	}

	public double getCarbs() {
		return carbohydrates;
	}

	// Computing Methods
	public double computeProtein(double quant) {
		return quant * getProtein();
	}

	public double computeFat(double quant) {
		return quant * getFat();
	}

	public double computeCarbs(double quant) {
		return quant * getCarbs();
	}

	public double computeCalories(double quant) {
		return computeProtein(quant) * 4 + computeFat(quant) * 9 + computeCarbs(quant) * 4;
	}

	public String toString() {
		return this.getName();
	}

	public String nutritionalValues(double x) {
		return x + " " + getUnit() + " of " + getName() + " contain " + computeProtein(x) + " " +
			getUnit() + " of protein, " + computeFat(x) + " " + getUnit() + " of fat, and " +
			computeCarbs(x) + " " + getUnit() + " of carbohydrates, for a total of " + computeCalories(x) +
			" calories.";
	}
}

// #3
class IngredientDatabase {
		
	private Ingredient[] ingredients;	
 	private int numIngredients;
 		
 	public IngredientDatabase() {
 
	 	ingredients = new Ingredient[100];
		ingredients[0] = new Ingredient("flour", "g", 0.11, 0.012, 0.75);
		ingredients[1] = new Ingredient("milk", "ml", 0.033, 0.038, 0.047);
		ingredients[2] = new Ingredient("oil", "g", 0, 1, 0);
		ingredients[3] = new Ingredient("egg", "unit", 6.15, 5.45, 0);
		ingredients[4] = new Ingredient("sugar", "g", 0, 0, 1);
		ingredients[5] = new Ingredient("butter", "g", 0.005, 0.82, 0);
		ingredients[6] = new Ingredient("cocoa", "g", 0.19, 0.22, 0.12);
		
		// new ingredients
		ingredients[7] = new Ingredient("almonds", "g", .17, .54, .04);
		ingredients[8] = new Ingredient("cranberries", "g", .005, 0, .04);
		ingredients[9] = new Ingredient("fried onions", "g", .02, .33, .1);
		ingredients[10] = new Ingredient("pears", "g", .002, 0, .08);
		ingredients[11] = new Ingredient("mincemeat", "g", 0, .04, .62);
		ingredients[12] = new Ingredient("ham", "g", .18, .05, 0);
		ingredients[13] = new Ingredient("cucumber", "g", .006, 0, .02);
		ingredients[14] = new Ingredient("cornflakes", "g", .08, .005, .82);
		ingredients[15] = new Ingredient("chestnuts", "g", .02, .027, .37);
		ingredients[16] = new Ingredient("blackberries", "g", .015, 0, .06);
		numIngredients = 17;
	}

	public Ingredient findIngredient(String name) {
		for (int i = 0; i < numIngredients; i++) {
			//System.out.println("line:" + ingredients[i].getName());
			if ((ingredients[i].getName()).equals(name)) {
				return ingredients[i];
			} 
		}
		return null;
	}
}

class Recipe {
	private String recipeName;
	private int ingredientAmnt; 
	private Ingredient[] list;
	private double[] listAmnt;

	public Recipe (String recipeName) {
		this.recipeName = recipeName;
		list = new Ingredient[100];
		listAmnt = new double[100];
		ingredientAmnt = 0;
	}

	public void addIngredient (Ingredient x, double quant) { 
		list[ingredientAmnt] = x;
		listAmnt[ingredientAmnt] = quant;
		ingredientAmnt++;
	}

	// this should take the amount of protein in each ingredient
	// then that amount should be multiplied by the quantity of each ingredient
	// Question 4.4 
	public double computeProtein() {
		double total = 0; // initializes the total amount of protein
		for (int i = 0; i < ingredientAmnt; i++) {
			total = total + list[i].getProtein() * listAmnt[i]; // list[i] is an ingredient; listAmnt[i] is the amount of that ingredient
		}
		return total;
	}

	public double computeFat() {
		double total = 0; // initializes the total amount of protein
		for (int i = 0; i < ingredientAmnt; i++) {
			total = total + list[i].getFat() * listAmnt[i]; // list[i] is an ingredient; listAmnt[i] is the amount of that ingredient
		}
		return total;
	}

	public double computeCarbohydrates() {
		double total = 0; // initializes the total amount of protein
		for (int i = 0; i < ingredientAmnt; i++) {
			total = total + list[i].getCarbs() * listAmnt[i]; // list[i] is an ingredient; listAmnt[i] is the amount of that ingredient
		}
		return total;
	}

	public double computeCalories() {
		double total = 0.0; 
		for (int i = 0; i < ingredientAmnt; i++) {
			total = total + list[i].getCarbs() * 4 * listAmnt[i] + 
			 list[i].getFat() * 9 * listAmnt[i] + list[i].getProtein() * 4 * listAmnt[i];
		}
		return total;
	}

	public String toString() {
		//System.out.println("Recipe of " + recipeName + ":");
		String result = "Recipe of " + recipeName + ":\n"; 
		for (int i = 0; i < ingredientAmnt; i++) {
			//System.out.println(listAmnt[i] + " " + list[i].getUnit() + " of " + list[i].getName());
			result = result + listAmnt[i] + " " + list[i].getUnit() + " of " + list[i].getName() + "\n";
		}
		return result;
	}

	public String nutritionalValues() {
		String result = "Nutritional values of " + recipeName + ":\n";
		//System.out.println(computeProtein());
		result = result + "Total Protein: " + computeProtein() + "\nTotal Fat: " + computeFat()
		 + "\nTotal Carbohydrates: " + computeCarbohydrates() + "\nTotal Calories: " + computeCalories();
		return result;
	}
	
}
































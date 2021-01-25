/*
	Author: 		Dillon Wu
	Written: 		September 28, 2017

	Compilation: 	javac Story.java
	Execution: 		java Story

	Tells a great story
*/

public class Story { 


	public static void main (String[] args) {
		System.out.println(storyteller ("Alice", "Betsy", "dresses", 6, "burgers", "angry", "her mother"));
	}

	// Contains seven different variables
	
	
	public static String storyteller (String firstG, String secondG, String possess, 
									  int quantity, String transform, String emotion, String speaker) {

		String magicWord = firstG.charAt(firstG.length() - 1) + "aa" + firstG.charAt(0) + " ";
		String magicWord2 = secondG.charAt(firstG.length() - 1) + "ee" + secondG.charAt(0) + " ";
		String magicWord3 = possess.charAt(possess.length()-1) + "ii" + possess.charAt(0);
		
		// The first chant is only lower case and the second is all upper.

		String chant1 = magicWord.toLowerCase() + magicWord2.toLowerCase() + magicWord3.toLowerCase();
		String chant2 = magicWord.toUpperCase() + magicWord2.toUpperCase() + magicWord3.toUpperCase();

		String fullChant = "\"" + chant1 + "! " + chant2 + "!" + "\"";


		String event = "Once upon a time, there was a programmer named " + firstG + ". " +
		firstG + " had " + quantity + " " + possess + ", whereas " + secondG + " had only " +
		quantity / 2 + " " + possess + ". Because of " + possess + ", " + secondG + " yelled " +
		"the ancient incantation " +  fullChant + ". At that point, the world was set ablaze. The " + 
		"chant had gone awry, and people were slowly transforming into " + transform + ". People felt " +
		 emotion + ". The world was thrown into upheaval. " + secondG + 
		 " needed to speak to someone about what she had done, but could find no one who wanted to listen except "
		 + speaker + ".";

		return event;
		
	}



}
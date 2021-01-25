public class TestAnimals {
	public static void main (String [] args) {
		Shark s = new Shark();
		s.swim();

		Lion l = new Lion();
		Alligator a = new Alligator();

		l.walk();
		a.swim();
		a.walk();

		// polymorphism = many shapes
		Swimmer x = new Shark();
		x.swim();
		Swimmer y = new Alligator();
		y.swim();
		// y.walk() does not work
		// Swimmer z = new Swimmer(); Another error

		Swimmer[] pool = new Swimmer[10];
		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) { 
				pool[i] = new Shark();
			} else {
				pool[i] = new Alligator();
			}
		}

		for (int i = 0; i < 10; i++) {
			System.out.print("pool[" + i + "] says: ");
			pool[i].swim();
		}

		Amphibian q = new Alligator();
		q.walk();
		q.swim();
	}
}
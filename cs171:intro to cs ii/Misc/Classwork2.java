public class Classwork2 {
	
	public static void main (String[] args) {
		Student Ben = new Student("sophomore", "Comp Sci", "LA");
		System.out.println(Ben.toString());
	}
}

class Student {

		//
	public String year;
	public String major;
	public String origin;

	// constructor
	public Student (String y, String m, String o) {
		year = y;
		major = m;
		origin = o;
	}

	public String toString() {
		return year;
	}
}

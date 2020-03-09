public class drawTriangle {
	public static void main (String[] args) {
	/* This function calls the printTriangle function. */	
		printTriangle ();
	}

	public static void printTriangle () {
	/* This function prints out a triangle of asterisks. */
		int numLines = 5;
		int i = 1;
		while (i <= numLines) {
			for (int numAsterisks = 1; numAsterisks <= i; numAsterisks += 1) { 
				System.out.print(" *");
			}
			System.out.println();
			i += 1;
		}
	}
}

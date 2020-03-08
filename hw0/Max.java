public class Max {
	public static void main (String[] args) {
		int max = 0;
		int i = 0;
		while (i < args.length) {
			if (Integer.parseInt (args[i]) > max) 
				max = Integer.parseInt (args[i]);
			i += 1;
		}
		System.out.println (max);
	}
}

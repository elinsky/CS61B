public class Max2 {
	public static void main (String[] args) {
		int max = 0;
		int i = 0;
		for (; i < args.length; i += 1) {
			if (Integer.parseInt (args[i]) > max) 
				max = Integer.parseInt (args[i]);
		}
		System.out.println (max);
	}
}

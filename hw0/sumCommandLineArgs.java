public class sumCommandLineArgs {
	public static void main(String[] args){
		int i = 0;
		int sum = 0;
		while (i < args.length){
			sum += Integer.parseInt(args[i]);
			i += 1;
		}
		System.out.println(sum);
	}

}

public class threeSum {
	public static void main (String[] args) {
		int[] a = {1, 2, -1, 4, 5};
		System.out.println (threeSum (a));	
	}

	public static boolean threeSum (int[] a) {
		int i = 0;
		int j = 0;
		int k = 0;
		for (; i < a.length; i += 1) {
			for (; j < a.length; j += 1) {
				for (; k < a.length; k += 1) {
					if (a[i] + a[j] + a[k] == 0)
						return true;
				}	
			}
		}
		return false;
	}
}

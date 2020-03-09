public class BreakContinue {
	public static void windowPosSum(int[] a, int n) {
		int N = a.length;
		for (int i = 0; i < N; i += 1){
			int newVal = a[i];
			if (newVal >= 0) {
				for (int j = i + 1; j < Math.min(i + n + 1, N); j += 1){
					newVal = newVal + a[j];
				}
			a[i] = newVal;
			} 
		}
	}
	
	public static void main(String[] args) {
		int[] a = {1, 2, -3, 4, 5, 4};
		int n = 3;
		windowPosSum(a, n);

		// Should print 4, 8, -3, 13, 9, 4
		System.out.println(java.util.Arrays.toString(a));
	}
}

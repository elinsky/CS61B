package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] hash_counts = new int[M];
        for (Oomage item : oomages) {
            int bucket_num = (item.hashCode() & 0x7FFFFFFF) % M;
            hash_counts[bucket_num] += 1;
        }
        for (int hash_count : hash_counts) {
            if (hash_count < oomages.size() / 50 || hash_count > oomages.size() / 2.5) {
                return false;
            }
        }
        return true;
    }
}

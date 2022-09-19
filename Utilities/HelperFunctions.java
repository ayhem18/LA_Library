package Utilities;

import java.util.stream.IntStream;

/**
 * This class offers a number of helper functions that are not directly related to Linear Algebra
 */
public class HelperFunctions {
    // Return an array representing the permutation from 1 to n with values p1 and p2 swapped
    public static int[] specialPermutation(int n, int p1, int p2) {
        int[] result = IntStream.range(0, n).toArray();
        int temp = result[p1];
        result[p1] = result[p2];
        result[p2] = temp;
        return result;
    }
}

package Utilities;

import java.util.stream.IntStream;

public class HelperFunctions {
    public static int[] specialPermutation(int n, int p1, int p2) {
        int[] result = IntStream.range(0, n).toArray();
        int temp = result[p1];
        result[p1] = result[p2];
        result[p2] = temp;
        return result;
    }
}

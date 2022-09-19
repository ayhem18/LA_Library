package ArraysOperations;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * This class allows extending the arithmetic operations to 1 dimensional arrays
 */
public class ArrayOp1D {
    // helper functions to verify that operations are executed on arrays of the same length
    private static void verifyLength(double[] array1, double[] array2) throws ArrayOpException{
        if (array1.length != array2.length) {
            throw new DimensionException1D(array1, array2);
        }
    }

    // adding two arrays
    public static double[] add(double[] array1, double[] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] + array2[i];
        }
        return result;
    }

    // adding an array and a double
    public static double[] add(double x, double[] array2) throws ArrayOpException{
        return add(IntStream.range(0, array2.length).mapToDouble(y -> x).toArray(), array2);
    }

    public static double[] add(double[] array1, double x) throws ArrayOpException{
        return add(x, array1);
    }

    // subtracting two arrays
    public static double[] subtract(double[] array1, double[] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] - array2[i];
        }
        return result;
    }

    public static double[] subtract(double x, double[] array2) throws ArrayOpException{
        return multiply(subtract(array2, x), -1);
    }

    public static double[] subtract(double[] array2, double x) throws ArrayOpException{
        return add(array2, -x);
    }

    // Element wise multiplication of two arrays
    public static double[] multiply(double[] array1, double[] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] * array2[i];
        }
        return result;
    }

    // multiplying all array elements by the same double value
    public static double[] multiply(double x, double[] array2) throws ArrayOpException {
        return multiply(IntStream.range(0, array2.length).mapToDouble(y -> x).toArray(), array2);
    }

    public static double[] multiply(double[] array1, double x) throws ArrayOpException {
        return multiply(IntStream.range(0, array1.length).mapToDouble(y -> x).toArray(), array1);
    }
    // element wise division where the second element should not contain a single zero value
    public static double[] divide(double[] array1, double[] array2) throws ArrayOpException {
        verifyLength(array1, array2);
        int l = array1.length;
        if (Arrays.stream(array2).anyMatch(x -> x == 0)) {
            throw new ZeroDivExceptionArray(array2);
        }

        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] / array2[i];
        }
        return result;
    }
    // divide every element of an array by a float value
    public static double[] divide(double[] array1, double x) throws ArrayOpException {
        return divide(array1, IntStream.range(0, array1.length).mapToDouble(y -> x).toArray());
    }
    // creating a new array where the i-th element is x / array2[i]
    public static double[] divide(double x, double[] array2) throws ArrayOpException {
        return divide(IntStream.range(0, array2.length).mapToDouble(y -> x).toArray(), array2);
    }

}


package ArraysOperations;

import java.util.Arrays;

/**
 * This class extends the basic arithmetic operations to 2D arrays
 */
public class ArrayOp2D {
    // verify that arrays are of the exact same shape
    private static void verifyLength(double[][] array1, double[][] array2) throws ArrayOpException{
        if (array1.length != array2.length || array1[0].length != array2[0].length) {
            throw new DimensionException2D(array1, array2);
        }
    }

    // add two arrays element wise
    public static double[][] add(double[][] array1, double[][] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.add(array1[i], array2[i]);
        }
        return result;
    }

    // subtract two arrays element wise
    public static double[][] subtract(double[][] array1, double[][] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.subtract(array1[i], array2[i]);
        }
        return result;
    }

    // multiply two arrays element wise
    public static double[][] multiply(double[][] array1, double[][] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.multiply(array1[i], array2[i]);
        }
        return result;
    }

    // divide two arrays element wise: make sure the second array has no zero elements
    public static double[][] divide(double[][] array1, double[][] array2) throws ArrayOpException {
        verifyLength(array1, array2);
        boolean zeroFree;
        int l = array1.length;
        for (double[] row: array2) {
            // verify whether the i-th row has a zero value
            zeroFree = Arrays.stream(row).anyMatch(x -> x == 0);
            if (zeroFree) {
                throw new ZeroDivExceptionArray(row);
            }
        }

        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.divide(array1[i], array2[i]);
        }
        return result;
    }

}



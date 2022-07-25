package ArraysOperations;

import java.util.Arrays;

public class ArrayOp2D {
    private static String DimensionsError(double[][] array1, double[][] array2) {
        return "Element-wise operations require arrays of the same dimensions"
                + "\n" + "operand 1: (" + array1.length + ", " + array1[0].length + ")\n" + "operand 2: ("
                + array2.length + ", " + array2[0].length + ")";
    }

    private static String ZeroDivError(double[] arrayRaw) {
        return "Division operation requires the second array to have non-zero elements \n" +
                "The array's raw: " + Arrays.toString(arrayRaw) + "\n has at least one zero entry";
    }

    private static void verifyLength(double[][] array1, double[][] array2) throws ArrayOpException{
        if (array1.length != array2.length || array1[0].length != array2[0].length) {
            throw new DimensionException2D(array1, array2);
        }
    }

    public static double[][] add(double[][] array1, double[][] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.add(array1[i], array2[i]);
        }
        return result;
    }

    public static double[][] subtract(double[][] array1, double[][] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.subtract(array1[i], array2[i]);
        }
        return result;
    }

    public static double[][] multiply(double[][] array1, double[][] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.multiply(array1[i], array2[i]);
        }
        return result;
    }

    public static double[][] divide(double[][] array1, double[][] array2) throws ArrayOpException {
        verifyLength(array1, array2);
        boolean zeroFree;
        int l = array1.length;
        for (double[] row: array2) {
            // verify whether the i-th row has a zero value
            zeroFree = Arrays.stream(row).anyMatch(x -> x == 0);
            if (zeroFree) {
                throw new ZeroDivException(row);
            }
        }

        double[][] result = new double[array1.length][array1[0].length];
        for (int i = 0; i < l; i++) {
            result[i] = ArrayOp1D.divide(array1[i], array2[i]);
        }
        return result;
    }

}



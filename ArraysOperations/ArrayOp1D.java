package ArraysOperations;

import java.util.Arrays;

public class ArrayOp1D {
    private static String DimensionsError(double[] array1, double[] array2) {
        return "Element-wise operations require operands of the same dimensions"
                + "\n" + "operand 1: " + array1.length + "\n" + "operand 2: " + array2.length;
    }

    private static String ZeroDivError(double[] array2) {
        return "Division operation requires the second array to have non-zero elements \n" +
        "The array " + Arrays.toString(array2) + "\n has at least one zero entry";
    }

    private static void verifyLength(double[] array1, double[] array2) throws ArrayOp1DError{
        if (array1.length != array2.length) {
            throw new ArrayOp1DError(DimensionsError(array1, array2));
        }
    }

    public static double[] sum(double[] array1, double[] array2) throws ArrayOp1DError{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] + array2[i];
        }
        return result;
    }

    public static double[] subtract(double[] array1, double[] array2) throws ArrayOp1DError{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] - array2[i];
        }
        return result;
    }

    public static double[] multiply(double[] array1, double[] array2) throws ArrayOp1DError{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] * array2[i];
        }
        return result;
    }

    public static double[] divide(double[] array1, double[] array2) throws ArrayOp1DError {
        verifyLength(array1, array2);
        boolean zeroFree = true;
        int l = array1.length;
        for (double value : array1) {
            zeroFree = value != 0.0;
            if (!zeroFree) {
                throw new ArrayOp1DError(ZeroDivError(array2));
            }
        }

        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] / array2[i];
        }
        return result;
    }
    
}

class ArrayOp1DError extends Exception{
    public ArrayOp1DError(String message) {
        super(message);
    }
}

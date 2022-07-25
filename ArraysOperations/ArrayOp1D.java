package ArraysOperations;

import java.util.Arrays;

public class ArrayOp1D {
    private static void verifyLength(double[] array1, double[] array2) throws ArrayOpException{
        if (array1.length != array2.length) {
            throw new DimensionException1D(array1, array2);
        }
    }

    public static double[] add(double[] array1, double[] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] + array2[i];
        }
        return result;
    }

    public static double[] subtract(double[] array1, double[] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] - array2[i];
        }
        return result;
    }

    public static double[] multiply(double[] array1, double[] array2) throws ArrayOpException{
        verifyLength(array1, array2);
        int l = array1.length;
        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] * array2[i];
        }
        return result;
    }

    public static double[] divide(double[] array1, double[] array2) throws ArrayOpException {
        verifyLength(array1, array2);
        int l = array1.length;
        if (Arrays.stream(array2).anyMatch(x -> x == 0)) {
            throw new ZeroDivException(array2);
        }

        double[] result = new double[l];
        for (int i = 0; i < l; i++) {
            result[i] = array1[i] / array2[i];
        }
        return result;
    }
}


package ArraysOperations;

/**
 * Custom exceptions built to better handle the mathematical errors
 */
public abstract class ArrayOpException extends Exception{
    public ArrayOpException(String message) {
        super(message);
    }
}

class DimensionException2D extends ArrayOpException {
    protected static String ErrorMessage(double[][] array1, double[][] array2) {
        return "Element-wise operations require arrays of the same dimensions"
                + "\n" + "operand 1: (" + array1.length + ", " + array1[0].length + ")\n" + "operand 2: ("
                + array2.length + ", " + array2[0].length + ")";
    }

    public DimensionException2D(double[][] array1, double[][] array2) {
        super(ErrorMessage(array1, array2));
    }
}

class DimensionException1D extends ArrayOpException {
    protected static String ErrorMessage(double[]array1, double[] array2) {
        return "Element-wise operations require operands of the same dimensions"
                + "\n" + "operand 1: " + array1.length + "\n" + "operand 2: " + array2.length;
    }

    public DimensionException1D(double[] array1, double[] array2) {
        super(ErrorMessage(array1, array2));
    }
}

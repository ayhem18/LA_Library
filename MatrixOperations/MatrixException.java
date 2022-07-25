package MatrixOperations;

import Matrices.Matrix;

public abstract class MatrixException extends Exception{
    public MatrixException(String message) {
        super(message);
    }
}

class DimensionMatrixException extends MatrixException {
    public DimensionMatrixException(Matrix m1, Matrix m2) {
        super(getError(m1, m2));
    }

    protected static String getError(Matrix m1, Matrix m2) {
        return "Element-wise matrix operations require the operands to be of the same dimensions \n" +
                "The shape of the first matrix is (" + m1.getRows() + ", " + m1.getColumns() +"). The second matrix "
                + "is of the shape (" + m2.getRows() + ", " + m2.getColumns() + ")";
    }
}

class MultiplicationException extends MatrixException {
    public MultiplicationException(Matrix m1, Matrix m2) {
        super(getError(m1, m2));
    }

    protected static String getError(Matrix m1, Matrix m2) {
        return "matrix multiplication requires equality between the first matrix's row and second matrix's columns\n" +
                "Operand 1: of shape (" + m1.getRows() + ", " + m1.getColumns() +"). \n" +
                "Operand 2: of shape (" + m2.getRows() + ", " + m2.getColumns() +"). \n";
    }
}

class ZeroDivException extends MatrixException {
    public ZeroDivException(Matrix m2) {
        super(getError(m2));
    }

    protected static String getError(Matrix m2) {
        return "Element-wise division requires the second matrix to have non-zero entries \n" +
                "The second matrix " + m2.toString() + "\n contains at least one zero entry";
    }

}
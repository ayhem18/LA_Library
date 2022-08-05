package MatrixOperations;

import ArraysOperations.ArrayOp2D;
import ArraysOperations.ArrayOpException;
import Matrices.Matrix;

public class ElementOperations {

    public static Matrix add(Matrix m1, Matrix m2) throws MatrixException {
        try {
            return new Matrix(ArrayOp2D.add(m1.getMatrix(), m2.getMatrix()));
        } catch (ArrayOpException e) {
            throw new DimensionMatrixException(m1, m2);
        }
    }
    public static Matrix add(Matrix m1, double x) throws MatrixException {
        return add(m1, new Matrix(m1.getRows(), m1.getColumns(), x));
    }

    public static Matrix add(double x, Matrix m1) throws MatrixException {
        return add(m1, x);
    }
    
    
    public static Matrix subtract(Matrix m1, Matrix m2) throws MatrixException {
        try {
            return new Matrix(ArrayOp2D.subtract(m1.getMatrix(), m2.getMatrix()));
        } catch (ArrayOpException e) {
            throw new DimensionMatrixException(m1, m2);
        }
    }

    public static Matrix subtract(Matrix m1, double x) throws MatrixException {
        return add(m1, -x);
    }

    public static Matrix subtract(double x, Matrix m1) throws MatrixException {
        return multiply(subtract(m1, x), -1);
    }

    public static Matrix multiply(Matrix m1, Matrix m2) throws MatrixException {
        try {
            return new Matrix(ArrayOp2D.multiply(m1.getMatrix(), m2.getMatrix()));
        } catch (ArrayOpException e) {
            throw new DimensionMatrixException(m1, m2);
        }
    }

    public static Matrix multiply(Matrix m1, double x) throws MatrixException {
        return multiply(m1, new Matrix(m1.getRows(), m1.getColumns(), x));
    }

    public static Matrix multiply(double x, Matrix m1) throws MatrixException {
        return multiply(m1, x);
    }
    

    public static Matrix divide(Matrix m1, Matrix m2) throws MatrixException{
        try {
            return new Matrix(ArrayOp2D.divide(m1.getMatrix(), m2.getMatrix()));
        } catch (ArrayOpException e1) {
            throw new DimensionMatrixException(m1, m2);
        }
    }

    public static Matrix divide(Matrix m1, double x) throws MatrixException{
        return divide(m1, new Matrix(m1.getRows(), m1.getColumns(), x));
    }
}


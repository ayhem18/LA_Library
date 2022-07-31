package MatrixOperations;

import ArraysOperations.ArrayOp1D;
import ArraysOperations.ArrayOpException;
import Matrices.*;

import java.util.Arrays;

public class BinaryMatrixOperations {
    public static Matrix matMultiplication(Matrix m1, Matrix m2) throws MatrixException {
        if (m1.getColumns() != m2.getRows()) {
            throw new MultiplicationException(m1, m2);
        }
        Matrix result = new Matrix(m1.getRows(), m2.getColumns());
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getColumns(); j++) {
                try {
                    result.setCell(i, j, Arrays.stream(ArrayOp1D.multiply(m1.getRow(i), m2.getColumn(j))).sum());
                } catch (ArrayOpException e) {
                    throw new MultiplicationException(m1, m2);
                }
            }
        }
        return result;
    }
    public static Matrix matMultiplication(Matrix m1, ElementaryMatrix m2) throws MatrixException {
        return matMultiplication(m1, m2.getMatrix());
    }

    public static Matrix matMultiplication(EliminationMatrix m1, Matrix m2) throws MatrixException{
        if (m1.getSide() != m2.getRows()) {
            throw new MultiplicationException(m1.getMatrix(), m2);
        }

        Matrix result = new Matrix(m2);
        int i = m1.getI(); //
        int j = m1.getJ(); //
        double value = m1.getEliminationValue();
        // the new i-th row in m2 will be equal to [j] * eliminationValue + [i]
        try {
            result.setRow(i, ArrayOp1D.add(ArrayOp1D.multiply(value , result.getRow(j)), result.getRow(i)));
        } catch(ArrayOpException e) {
            throw new MultiplicationException(m1.getMatrix(), m2);
        }
        return result;
    }

    public static Matrix matMultiplication(DiagonalMatrix m1, Matrix m2) throws MatrixException {
        if (m1.getSide() != m2.getRows()) {
            throw new MultiplicationException(m1.getMatrix(), m2);
        }
        Matrix result = new Matrix(m2);
        for (int i = 0; i < m1.getSide(); i++) {
            try {
                result.setRow(i, ArrayOp1D.multiply(m1.getMatrix().getCell(i, i), result.getRow(i)));
            } catch(ArrayOpException e) {
                throw new MultiplicationException(m1.getMatrix(), m2);
            }
        }
        return result;
    }

    public static Matrix matMultiplication(PermutationMatrix m1, Matrix m2) throws MatrixException {
        if (m1.getSide() != m2.getRows()) {
            throw new MultiplicationException(m1.getMatrix(), m2);
        }

        Matrix result = new Matrix(m2.getRows(), m2.getColumns());
        for (int i = 0; i < m1.getSide(); i++) {
            result.setRow(i, m2.getRow(m1.getPositions()[i]));
        }
        return result;
    }

    public static Matrix matMultiplication(ElementaryMatrix e, Matrix m2) throws MatrixException{
        if (e instanceof EliminationMatrix e1)
            return matMultiplication(e1, m2);
        if (e instanceof PermutationMatrix p1)
            return matMultiplication(p1, m2);
        return matMultiplication((DiagonalMatrix) e, m2);
    }

    public static Matrix matMultiplication(ElementaryMatrix e1, ElementaryMatrix e2) throws MatrixException {
        return matMultiplication(e1, e2.getMatrix());
    }
}

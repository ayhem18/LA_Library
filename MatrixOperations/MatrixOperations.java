package MatrixOperations;

import ArraysOperations.ArrayOp1D;
import ArraysOperations.ArrayOpException;
import Matrices.Matrix;

import java.util.Arrays;

public class MatrixOperations {
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

    public static Matrix transpose(Matrix m1){
        Matrix result = new Matrix(m1.getColumns(), m1.getRows());
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getColumns(); j++) {
                result.setCell(i, j, m1.getCell(j, i));
            }
        }
        return result;
    }
}

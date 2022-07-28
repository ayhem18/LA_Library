package MatrixOperations;

import ArraysOperations.ArrayOp1D;
import ArraysOperations.ArrayOpException;
import Matrices.*;
public class UnaryMatrixOperations {
    public static Matrix transpose(Matrix m1){
        Matrix result = new Matrix(m1.getColumns(), m1.getRows());
        for (int i = 0; i < result.getRows(); i++) {
            for (int j = 0; j < result.getColumns(); j++) {
                result.setCell(i, j, m1.getCell(j, i));
            }
        }
        return result;
    }
    public static Matrix transpose(ElementaryMatrix m) {
        return transpose(m.getMatrix());
    }

    protected static EliminationMatrix inverse(EliminationMatrix e) {
        // the inverse of an elimination matrix is the same matrix only with the opposite of the elimination value
        return new EliminationMatrix(e.getSide(), e.getI(), e.getJ(), -e.getEliminationValue());
    }

    protected static DiagonalMatrix inverse(DiagonalMatrix d) throws MatrixException {
        try {
            return new DiagonalMatrix(d.getSide(), ArrayOp1D.divide(1, d.getDiagonal()));
        } catch (ArrayOpException e) { // the only possible error is DivZeroExceptionArray
            throw new ZeroDivExceptionMatrix(d.getMatrix());
        }
    }

    protected static PermutationMatrix inverse(PermutationMatrix p) {
        int[] newPositions = new int[p.getSide()];
        for (int i = 0; i < p.getSide(); i++) {
            newPositions[p.getPositions()[i]] = i;
        }
        return new PermutationMatrix(p.getSide(), newPositions);
    }

    public static ElementaryMatrix inverse(ElementaryMatrix e) throws MatrixException{
        if (e instanceof EliminationMatrix e1)
            return inverse(e1);
        if (e instanceof PermutationMatrix p1)
            return inverse(p1);
        return inverse((DiagonalMatrix) e);
    }

    public static SquareMatrix inverse(SquareMatrix m) throws MatrixException {
        int side = m.getColumns();
        Matrix copy = new SquareMatrix(m);
        // this matrix will store all the operations of the elimination algorithms
        ElementaryMatrix e;
        // this will store the inverse of the original matrix: initialized to an identity matrix
        Matrix inverse = new DiagonalMatrix(m.getRows()).getMatrix();

        for (int k = 0; k < side; k++) {
            if (copy.getCell(k, k) == 0) {
                for (int p = k + 1; p < side; p++ ) {
                    // find the first row with a non-zero value in the k-th column
                    if (copy.getCell(p, k) != 0) {
                        // perform a row exchange
                        e = new PermutationMatrix(side, p, k);
                        // apply it on the matrix
                        copy = BinaryMatrixOperations.matMultiplication(e, copy);
                        // apply the changes directly on the inverse matrix
                        inverse = BinaryMatrixOperations.matMultiplication(e, inverse);
                        break;
                    }
                }
            }
            // if there is a zero in a pivot position, then the matrix is singular, the process should
            // terminate immediately

            if (copy.getCell(k, k) == 0) {
                throw new IllegalArgumentException("The matrix \n" + m + "\n is singular");
            }

            for (int p = k + 1; p < side; p++) {
                Matrix[] copyInverse = rowSubtraction(copy, inverse, side, p, k);
                copy = copyInverse[0];
                inverse = copyInverse[1];
            }
        }
        // by the end of this for loop the matrix should be an upper triangular
        // let's consider the second part of the elimination process:
        // converting the upper triangular matrix to a diagonal one


        for (int k = side - 1; k >= 0; k--) {
            // if a matrix is singular, then at least one row is a linear combination of the rest of the rows
            // this is revealed by the first part of the process.
            // Thus, a matrix at this point is known to be invertible: no to verify the pivots

            for (int p = k - 1; p >= 0; p--) {
                Matrix[] copyInverse = rowSubtraction(copy, inverse, side, p, k);
                copy = copyInverse[0];
                inverse = copyInverse[1];
            }
        }
        // now the matrix is diagonal and need to be converted to an identity matrix
        double[] diagonal = new double[side];
        for (int k = 0; k < side; k++) diagonal[k] = copy.getCell(k, k);
        e = new DiagonalMatrix(side, diagonal);
        inverse = BinaryMatrixOperations.matMultiplication(inverse(e), inverse);

        return inverse.toSquareMatrix();
    }

    private static Matrix[] rowSubtraction(Matrix matrix, Matrix inverse, int side, int change, int noChange)
            throws MatrixException{
        if (matrix.getCell(change, noChange) != 0) {
            // subtract the kth row from the p-th row in the matrix.
            EliminationMatrix e = new EliminationMatrix(side, change, noChange,
                    - matrix.getCell(change, noChange) / matrix.getCell(noChange, noChange));
            // apply in on the matrix
            matrix = BinaryMatrixOperations.matMultiplication(e, matrix);
            // reflect the change on the inverse matrix
            inverse = BinaryMatrixOperations.matMultiplication(e, inverse);
        }
        return new Matrix[]{matrix, inverse};
    }
}

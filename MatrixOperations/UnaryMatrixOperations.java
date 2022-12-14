package MatrixOperations;

import ArraysOperations.ArrayOp1D;
import ArraysOperations.ArrayOpException;
import Matrices.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    /**
     * All elimination matrices are invertible: changing the sign of the only non-zero, non -diagonal value
     * @param e elimination matrix
     * @return the inverse of the elimination matrix
     */
    protected static EliminationMatrix inverse(EliminationMatrix e) {
        // the inverse of an elimination matrix is the same matrix only with the opposite of the elimination value
        return new EliminationMatrix(e.getSide(), e.getI(), e.getJ(), -e.getEliminationValue());
    }

    /**
     * All diagonal matrices are invertible: a digonal matrix where each diagonal value is the inverse (recriproque)
     * of the corresponding diagonal value
     * @param d: diagonal matrix
     * @return the inverse of the matrix
     * @throws MatrixException: if the matrix has zero in its diagonal
     */
    protected static DiagonalMatrix inverse(DiagonalMatrix d) throws MatrixException {
        try {
            return new DiagonalMatrix(d.getSide(), ArrayOp1D.divide(1, d.getDiagonal()));
        } catch (ArrayOpException e) { // the only possible error is DivZeroExceptionArray
            throw new ZeroDivExceptionMatrix(d.getMatrix());
        }
    }

    /**
     *
     * All permutations matrices are invertible: the inverse of a permutation matrix is its transpose
     * @param p elimination matrix
     * @return the inverse of the elimination matrix
     */
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

    /**
     * Only Square matrices can be invertible
     * @param m: square matrix
     * @return: the matrix M such that m * M = I (identity matrix)
     * @throws MatrixException: in case the matrix is not invertible
     */
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

    /*
    helper function used to subtract one column from another in the elimination process
     */
    private static Matrix[] rowSubtraction(Matrix matrix, Matrix inverse, int side, int change, int noChange)
            throws MatrixException{
        return rowSubtraction(matrix, inverse, side, change, noChange, noChange);
//        if (matrix.getCell(change, noChange) != 0) {
//            // subtract the kth row from the p-th row in the matrix.
//            EliminationMatrix e = new EliminationMatrix(side, change, noChange,
//                    - matrix.getCell(change, noChange) / matrix.getCell(noChange, noChange));
//            // apply in on the matrix
//            matrix = BinaryMatrixOperations.matMultiplication(e, matrix);
//            // reflect the change on the inverse matrix
//            inverse = BinaryMatrixOperations.matMultiplication(e, inverse);
//        }
//        return new Matrix[]{matrix, inverse};
    }

    /**
     *
     * @param m a square matrix (invertible)
     * @return Two Square matrices L and U where L: is upper and L: is a lower matrix and m = L * U
     * @throws MatrixException: if the matrix is not invertible
     */
    public static SquareMatrix[] LUFactorization(SquareMatrix m) throws MatrixException {
        int side = m.getRows();
        Matrix upper = new SquareMatrix(m);
        // this matrix will store all the operations of the elimination algorithms
        ElementaryMatrix e;
        // this will store the inverse of the original matrix: initialized to an identity matrix
        List<ElementaryMatrix> EInverses = new ArrayList<>();

        for (int k = 0; k < side; k++) {
            if (upper.getCell(k, k) == 0) {
                for (int p = k + 1; p < side; p++ ) {
                    // find the first row with a non-zero value in the k-th column
                    if (upper.getCell(p, k) != 0) {
                        // perform a row exchange
                        e = new PermutationMatrix(side, p, k);
                        // apply it on the matrix
                        upper = BinaryMatrixOperations.matMultiplication(e, upper);
                        // apply the changes directly on the inverse matrix
                        EInverses.add(UnaryMatrixOperations.inverse(e));
                        break;
                    }
                }
            }
            // if there is a zero in a pivot position, then the matrix is singular, the process should
            // terminate immediately

            if (upper.getCell(k, k) == 0) {
                throw new IllegalArgumentException("The matrix \n" + m + "\n is singular");
            }

            for (int p = k + 1; p < side; p++) {
                if (upper.getCell(p, k) != 0) {
                    // subtract the kth row from the p-th row in the matrix.
                    e = new EliminationMatrix(side, p, k,
                            - upper.getCell(p, k) / upper.getCell(k, k));
                    // apply in on the matrix
                    upper = BinaryMatrixOperations.matMultiplication(e, upper);
                    // reflect the change on the inverse matrix
                    EInverses.add(UnaryMatrixOperations.inverse(e));
                }
            }
        }

        Matrix lower = new DiagonalMatrix(m.getRows()).getMatrix();
        for (int i = EInverses.size() - 1; i >= 0; i--) {
            lower = BinaryMatrixOperations.matMultiplication(EInverses.get(i), lower);
        }

        return new SquareMatrix[] {lower.toSquareMatrix(), upper.toSquareMatrix()};
    }

    /**
     *
     * @param A any matrix
     * @return the Reduced Row Echelon Form of matrix A.
     * @throws MatrixException: if any error in matrix calculation takes place
     */
    public static Matrix[] RREF(Matrix A) throws MatrixException {
        int rows = A.getRows();
        int columns = A.getColumns();
        int i = 0, j = 0;

        // copy to apply all the changes on
        Matrix copy = new Matrix(A);
        // list of indices of the pivot columns: list as the rank is not known in advance.
        List<Integer> pivotColumns = new ArrayList<>();

        // this matrix is used to record the elimination process: transforming A to RREF(A)
        Matrix record = new DiagonalMatrix(rows).getMatrix();
        // this stores the last elimination matrix used
        ElementaryMatrix e;
        // first let's find the pivot and free columns
        while (i < rows && j < columns) {
            if (copy.getCell(i, j) == 0) {
                // check for possible row exchange
                for (int p = i + 1; p < rows; p++) {
                    // find the first row with a non-zero value in the k-th column
                    if (copy.getCell(p, j) != 0) {
                        // perform a row exchange
                        e = new PermutationMatrix(rows, p, i);
                        // apply it on the matrix
                        copy = BinaryMatrixOperations.matMultiplication(e, copy);
                        // apply the changes directly record matrix
                        record = BinaryMatrixOperations.matMultiplication(e, record);

                        break;
                    }
                }
            }
            // if the cell is still of value zero, then there was no row exchange
            // thus the column is a non-pivot column
            if (copy.getCell(i, j) == 0) {
                // increment j: move to the next column
                j++;
                continue;
            }

            // if the code reaches this part, then there was a row exchange and the column is indeed a pivot column

            for (int k = i + 1; k < rows; k++) {
                Matrix[] rowReduction = rowSubtraction(copy, record, rows, k, i, j);
                copy = rowReduction[0];
                record = rowReduction[1];
            }
            // after performing elimination downwards, add the current column to the pivot columns
            pivotColumns.add(j);
            i++; j++;
        }
        // the matrix is now at the echelon form. It should be reduced to the row reduced echelon form: RREF
        // perform elimination upwards

        int rowPosition = 0;
        for (int col: pivotColumns) {
            for (int p = rowPosition - 1; p >= 0; p--) {
                Matrix[] rowReduction = rowSubtraction(copy, record, rows, p, rowPosition, col);
                copy = rowReduction[0];
                record = rowReduction[1];
            }
            rowPosition ++;
        }

        // the only thing left is to normalize the pivots to "1"
        // let's find the values at the pivots positions
        rowPosition = 0;
        double[] pivots = new double[rows];
        // set all values to 1
        for (int s = 0; s< rows; s++) pivots[s] = 1;
        // set only the values corresponding to pivot rows to the inverse of pivot values

        for (int index: pivotColumns) {
            pivots[rowPosition] = 1 / copy.getCell(rowPosition, index);
            rowPosition++;
        }

        e = new DiagonalMatrix(rows, pivots);

        copy = BinaryMatrixOperations.matMultiplication(e, copy);
        record = BinaryMatrixOperations.matMultiplication(e, record);

        // now the RREF form is achieved.
        return new Matrix[] {copy, record};
    }


    private static Matrix[] rowSubtraction(Matrix copy, Matrix record, int side, int change, int noChange, int col)
            throws MatrixException{
        if (copy.getCell(change, col) != 0) {
            // subtract the kth row from the p-th row in the matrix.
            EliminationMatrix e = new EliminationMatrix(side, change, noChange,
                    - copy.getCell(change, col) / copy.getCell(noChange, col));
            // apply in on the matrix
            copy = BinaryMatrixOperations.matMultiplication(e, copy);
            // reflect the change on the inverse matrix
            record = BinaryMatrixOperations.matMultiplication(e, record);
        }
        return new Matrix[]{copy, record};
    }

    /**
     * The rank is one of the most expressive values of a matrix as it represents the number of independent columns
     * as well as the basics of the matrix fundamental spaces
     * @param m: a matrix
     * @return the number of linearly independent columns
     * @throws MatrixException
     */
    public static int rank(Matrix m) throws MatrixException {
        Matrix rref = RREF(m)[0];

        int rows = m.getRows();
        int cols = m.getColumns();

        int i = 0, j = 0;
        int rank = 0;
        while (i < rows && j < cols) {
            if (rref.getCell(i, j) != 0) {
                rank++;
                i++;
            }
            j++;
        }
        return rank;
    }

    /*
    This method is analogue to the broadcast process taking place in Python numerical libraries
    where one matrix can be extended to new dimensions to apply certain operations on it.
     */
    public static Matrix broadcast(Matrix m, int newR, int newC, double cte) {
        double[][] array = m.getMatrix();
        double[][] newArray = new double[newR][newC];
        for (int i = 0; i < newR; i++) {
            for (int j = 0; j < newC; j++) {
                if (i >= m.getRows() || j >= m.getColumns()) {
                    newArray[i][j] = cte;
                }
                else {
                    newArray[i][j] = array[i][j];
                }
            }
        }
        return new Matrix(newArray);
    }

    /*
    Any matrix is associated with a projection matrix computed as : A * (A * A^T)^-1 * A^T
     */
    public static Matrix projectionMatrix(Matrix A) throws MatrixException {
        return BinaryMatrixOperations.matMultiplication(
                BinaryMatrixOperations.matMultiplication(A, // A matrix
                inverse(BinaryMatrixOperations.matMultiplication(transpose(A), A).toSquareMatrix())), //(A * A^T)^-1
                transpose(A)); // A^T
    }

    /**
     * This function executes the GrimSchmidt process as it returns a matrix B with the same column space
     * but with orthonormal columns
     * @param A any matrix
     * @return the result of the GrimSchmidt process
     * @throws MatrixException
     */
    public static Matrix grimSchmidt(Matrix A) throws MatrixException{
        int cols = A.getColumns();
        // store all the column vectors of A in a list
        List<Matrix> a = IntStream.range(0, cols).mapToObj(A::getColMatrix).toList();
        // list to store the orthogonal columns
        List<Matrix> q = new ArrayList<>();
        // list to store the projections on each vector of the list "q"
        List<Matrix> p = new ArrayList<>();

        // the first column is not modified
        q.add(a.get(0)); // q_1 = a_1
        // add its projection
        p.add(projectionMatrix(q.get(0)));
        for (int i = 1; i < cols; i++) {
            q.add(a.get(i)); //q_i = a_i
            for (int j = 0; j < i; j++) {
                Matrix pjComponent = BinaryMatrixOperations.matMultiplication(p.get(j), a.get(i));
                q.set(i, ElementOperations.subtract(q.get(i), pjComponent));
            }
            p.add(projectionMatrix(q.get(i)));
        }
        // normalize the vectors
        for (int i = 0; i < cols;i++) {
            q.set(i, ElementOperations.divide(q.get(i), vectorNorm(q.get(i))));
        }
        // now it is time to convert the separate columns into a single matrix
        Matrix result = new Matrix(A.getRows(), cols); // matrix to store the result

        for (int i = 0; i < cols; i++) {
            Matrix intermediateMatrix = new Matrix(1, cols);
            intermediateMatrix.setCell(0, i, 1);
            result = ElementOperations.add(result,
                    BinaryMatrixOperations.matMultiplication(q.get(i), intermediateMatrix));
        }
        return result;
    }
    /*
    This function returns the norm of a vector x:  ||x||.
     */
    public static double vectorNorm(Matrix vec) throws MatrixException{
        if (vec.getColumns() > 1)
            throw new IllegalArgumentException("Please pass a column vector");
        return Math.sqrt(
                BinaryMatrixOperations.matMultiplication(
                        UnaryMatrixOperations.transpose(vec), vec).getCell(0, 0));
    }
}

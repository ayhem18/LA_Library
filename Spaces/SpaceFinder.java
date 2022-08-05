package Spaces;

import Equations.VectorEquation;
import Matrices.*;
import MatrixOperations.BinaryMatrixOperations;
import MatrixOperations.MatrixException;
import MatrixOperations.UnaryMatrixOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SpaceFinder {
    public static Matrix allSpaces(Matrix A) throws MatrixException {
        int rows = A.getRows();
        int columns = A.getColumns();
        int i = 0, j = 0;

        // copy to apply all the changes on
        Matrix copy = new Matrix(A);
        // list of indices of the pivot columns: list as the rank is not known in advance.
        List<Integer> pivotColumns = new ArrayList<>();
        // list of indices of the free columns:
        List<Integer> freeColumns = new ArrayList<>();

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
                // add the index of the column to the corresponding list
                freeColumns.add(j);
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
        // add the columns left out to the free columns: (mainly if rows < columns)
        for (int k = j + 1; k < columns; k++) {
            freeColumns.add(k);
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

        // now the RREF form is achieved. The rest is to retrieve the four fundamental spaces
        return copy;
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

//    private static Matrix allSpace(Matrix A)throws MatrixException {
//        Matrix[] elimination = UnaryMatrixOperations.RREF(A);
//        Matrix rref = elimination[0];
//        Matrix record = elimination[1];
//
//        List<Integer> pivotCols = new ArrayList<>();
//
//        int j = 0, i = 0;
//        int rows = A.getRows(), cols = A.getColumns();
//        while (i < rows && j < cols) {
//            if (rref.getCell(i, j) != 0) {
//                pivotCols.add(j);
//                i++;
//            }
//            j++;
//        }
//        // the pivot columns in the original matrix
//        Matrix[] colSpace = (Matrix[])
//                pivotCols.stream().map(x->
//                        UnaryMatrixOperations.transpose(new Matrix(new double[][]{A.getColumn(x)})))
//                        .toArray();
//
//        int rank = pivotCols.size();
//        // the first rank(A) rows in the RREF matrix
//        Matrix[] rowSpace = (Matrix[]) IntStream.range(0, rank).mapToObj(
//                x-> UnaryMatrixOperations.transpose(new Matrix(new double[][]{rref.getRow(x)}))).toArray();
//
//        // the left null space N(A^T)
//        // the last row - rank rows are zeros. The corresponding rows in the record matrix represent
//        // basis for the left null space.
//        Matrix[] leftNullSpace = (Matrix[]) IntStream.range(rank, rows).mapToObj(
//                x-> UnaryMatrixOperations.transpose(new Matrix(new double[][] {record.getRow(x)}))).toArray();
//
//        // only space left is the null space.
//        Matrix[] nullSpace = VectorEquation.Equation(rref);
//
//    }

}

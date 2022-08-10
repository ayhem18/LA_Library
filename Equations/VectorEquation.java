package Equations;

import Matrices.Matrix;
import MatrixOperations.BinaryMatrixOperations;
import MatrixOperations.MatrixException;
import MatrixOperations.UnaryMatrixOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VectorEquation {
    // this function returns
    public static List<Matrix> Equation(Matrix A, Matrix b) throws MatrixException {
        if (b.getRows() != A.getRows() || b.getColumns() != 1) {
            throw new IllegalArgumentException("The right hand side must be a column vector with the corresponding dimensions");
        }
        if (b.equals(new Matrix(b.getRows(), 1))) return Arrays.asList(Equation(A));

        List<Matrix> sols = new ArrayList<>();
        Matrix[] elimination = UnaryMatrixOperations.RREF(A);

        Matrix rref = elimination[0];
        Matrix record = elimination[1];

        List<List<Integer>> columns = columns(rref);
        List<Integer> pivotCols = columns.get(0);
        int rank = pivotCols.size();

        Matrix bCopy = BinaryMatrixOperations.matMultiplication(record, b);
        // System.out.println(bCopy);
        // first check that the last (row-rank(A)) rows are zeros in vector b
        for (int i = rank; i < A.getRows(); i++) if (bCopy.getCell(i, 0) != 0) return sols;
        int rowPos = 0;

        Matrix particularSolution = new Matrix(A.getColumns(), 1);
        for (int piv: pivotCols) {
            particularSolution.setCell(piv, 0, bCopy.getCell(rowPos, 0));
            rowPos ++;
        }
        sols.add(particularSolution);
        sols.addAll(Arrays.stream(Equation(rref)).toList());
        return sols;
    }

    // this function returns the solution for the equation: Ax = 0
    public static Matrix[] Equation(Matrix A) throws MatrixException {
        Matrix rref = UnaryMatrixOperations.RREF(A)[0];

        List<List<Integer>> columns = columns(rref);
        List<Integer> pivotCols = columns.get(0);
        List<Integer> freeCols = columns.get(1);

        int free = freeCols.size();
        // create n zero vectors: n: number of free variables or 1 if n == 0
        int size = Math.max(free, 1);
        Matrix[] sols = new Matrix[size]; // it is important to include the zero matrix regardless.
        for (int k = 0; k < size; k++) sols[k] = new Matrix(rref.getColumns(), 1);

        int solOrder = 0;
        for (int col :freeCols) {
            // set the value corresponding to the free variable to 1
            sols[solOrder].setCell(col, 0, 1);
            int row = 0;

            for (int pivcol: pivotCols) {
                sols[solOrder].setCell(pivcol,0, -rref.getCell(row, col));
                row++;
            }

            // add the opposite of the free column to the solution while making sure to broadcast the free column
//            sols[solOrder] = ElementOperations.add(sols[solOrder],
//                    UnaryMatrixOperations.broadcast(
//                            ElementOperations.multiply(-1, rref.getColMatrix(col)), cols, 1, 0));
            solOrder ++;
        }
        return sols;
    }

    private static List<List<Integer>> columns(Matrix rref) {
        List<Integer> freeCols = new ArrayList<>();
        List<Integer> pivotCols = new ArrayList<>();

        int j = 0, i = 0;
        int rows = rref.getRows(), cols = rref.getColumns();
        while (i < rows && j < cols) {
            if (rref.getCell(i, j) != 0) {
                pivotCols.add(j);
                i++;
            }
            else {
                freeCols.add(j);
            }
            j++;
        }
        return Stream.of(pivotCols, freeCols).collect(Collectors.toList());
    }
}

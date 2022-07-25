package Matrices;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Matrix {
    private final int rows;
    private final int columns;

    private final double[][] matrix;

    public Matrix(int rows, int columns, double[][] matrix) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = matrix;
    }

    public Matrix(int rows, int columns) {
        // this will set the matrix field to an all-zero array with the passed dimensions
        this(rows, columns, new double[rows][columns]);
    }

    public Matrix(double[][] matrix) {
        // infer the dimensions from the passed matrix
        this(matrix.length, matrix[0].length, matrix);
    }

    public Matrix(int rows, int columns, double value) {
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            matrix[i] = IntStream.range(0, columns).mapToDouble(x-> value).toArray();
        }
        this.matrix= matrix;
        this.rows=rows;
        this.columns=columns;
    }

    public double getCell(int i, int j) {
        return this.matrix[i][j];
    }

    public void setCell(int i, int j, double value) {
        this.matrix[i][j] = value;
    }

    public double[] getRow(int i){
        return this.matrix[i];
    }

    public double[] getColumn(int j) {
        return Arrays.stream(this.matrix).mapToDouble(x-> x[j]).toArray();
    }
    public double[][] getMatrix() {
        return this.matrix;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }
}

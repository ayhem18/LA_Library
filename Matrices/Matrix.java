package Matrices;

import MatrixOperations.UnaryMatrixOperations;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Matrix {

    private static final double EPSILON = Math.pow(10, -8);
    private static final String CONSTRUCTOR_ERROR =
            "The dimensions of the array passed must match the dimensions' variables";
    private String accessingError(int i, int j) {
        return "The coordinates passed are out of the matrix's boundaries. we have " + rows +" rows and "
                + columns + " columns" + "\nThe coordinates passed are (" + i + ", " + j +").";
    }
    private final int rows;
    private final int columns;

    private final double[][] matrix;

    public Matrix(int rows, int columns, double[][] matrix) throws IllegalArgumentException{
        this.rows = rows;
        this.columns = columns;
        this.matrix = matrix;
        if (this.rows != matrix.length || this.columns != matrix[0].length) {
            throw new IllegalArgumentException(CONSTRUCTOR_ERROR);
        }
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

    public Matrix(Matrix other) {
        this.rows = other.getRows();
        this.columns = other.getColumns();
        double[][] temp = new double[rows][columns];
        System.arraycopy(other.getMatrix(), 0, temp, 0, other.getMatrix().length);
        this.matrix = temp;
    }

    public double getCell(int i, int j) throws IllegalArgumentException{
        if (i >= rows || j >= columns)
            throw new IllegalArgumentException(accessingError(i, j));
        return this.matrix[i][j];
    }

    public void setCell(int i, int j, double value) {
        if (i >= rows || j >= columns)
            throw new IllegalArgumentException(accessingError(i, j));
        this.matrix[i][j] = value;
    }

    public double[] getRow(int i){
        return this.matrix[i];
    }

    public double[] getColumn(int j) {
        return Arrays.stream(this.matrix).mapToDouble(x-> x[j]).toArray();
    }

    public void setRow(int i, double[] newRow) {
        this.matrix[i] = newRow;
    }

    public void setColumns(int j, double[] newCol) {
        for (int i = 0; i < rows; i++) {
            this.setCell(i, j, newCol[i]);
        }
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

    public String toString() {
        StringBuilder s= new StringBuilder();
        for (double [] array : this.matrix) {
            s.append(Arrays.toString(array)).append("\n");
        }
        return s.deleteCharAt(s.length() - 1).toString();
    }
    @Override
    public boolean equals(Object another) {
        if (another instanceof Matrix m) {
            if (this.rows != m.getRows() || this.columns != m.getColumns())
                return false;
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns; j++) {
                    if (Math.abs(this.getCell(i, j) - m.getCell(i, j)) >= EPSILON) {
                        return false; }
                }
            }
            return true;
        }
        else if (another instanceof ElementaryMatrix e){
            return this.equals(e.getMatrix());
        }
        return false;
    }

    public SquareMatrix toSquareMatrix() {
        if (this.rows != this.columns)
            throw new IllegalArgumentException("The matrix has different number of rows and columns");
        return new SquareMatrix(this.rows, this.matrix);
    }

    public Matrix getRowMatrix(int i) {
        return new Matrix(new double[][] {getRow(i)});
    }

    public Matrix getColMatrix(int j) {
        return UnaryMatrixOperations.transpose(new Matrix(new double[][]{getColumn(j)}));
    }

}



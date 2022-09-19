package Matrices;

import MatrixOperations.UnaryMatrixOperations;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A Matrix class representing the main object of data structure of the library: Matrix
 */

public class Matrix {

    private static final double EPSILON = Math.pow(10, -9);
    private static final String CONSTRUCTOR_ERROR =
            "The dimensions of the array passed must match the dimensions' variables";
    private String accessingError(int i, int j) {
        return "The coordinates passed are out of the matrix's boundaries. we have " + rows +" rows and "
                + columns + " columns" + "\nThe coordinates passed are (" + i + ", " + j +").";
    }
    private final int rows;
    private final int columns;

    private final double[][] matrix;

    /**
     *
     * @param rows: number of rows
     * @param columns: number of columns
     * @param matrix: actual values
     * @throws IllegalArgumentException: when the passed array does not match the passed dimensions
     */
    public Matrix(int rows, int columns, double[][] matrix) throws IllegalArgumentException{
        this.rows = rows;
        this.columns = columns;
        this.matrix = matrix;
        if (this.rows != matrix.length || this.columns != matrix[0].length) {
            throw new IllegalArgumentException(CONSTRUCTOR_ERROR);
        }
    }

    /**
     * The default behavior is a zero matrix
     * @param rows: num of rows
     * @param columns num of columns
     */
    public Matrix(int rows, int columns) {
        // this will set the matrix field to an all-zero array with the passed dimensions
        this(rows, columns, new double[rows][columns]);
    }

    /**
     * rows and columns are inferred from the array argument
     * @param matrix: the actual values
     */
    public Matrix(double[][] matrix) {
        // infer the dimensions from the passed matrix
        this(matrix.length, matrix[0].length, matrix);
    }

    /**
     * setting all cells to the passed value
     * @param rows: num rows
     * @param columns: num columns
     * @param value: the value in question
     */
    public Matrix(int rows, int columns, double value) {
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            matrix[i] = IntStream.range(0, columns).mapToDouble(x-> value).toArray();
        }
        this.matrix= matrix;
        this.rows=rows;
        this.columns=columns;
    }

    /**
     * a Copy constructor
     * @param other: the matrix to copy
     */
    public Matrix(Matrix other) {
        this.rows = other.getRows();
        this.columns = other.getColumns();

        this.matrix = new double[other.rows][other.columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j< columns; j++) {
                setCell(i, j, other.getCell(i, j));
            }
        }
    }

    // getter for a cell value
    public double getCell(int i, int j) throws IllegalArgumentException{
        if (i >= rows || j >= columns)
            throw new IllegalArgumentException(accessingError(i, j));
        return this.matrix[i][j];
    }
    // setter for a cell value
    public void setCell(int i, int j, double value) {
        if (i >= rows || j >= columns)
            throw new IllegalArgumentException(accessingError(i, j));
        this.matrix[i][j] = value;
        // if the value set is too small set it to zero
        if (Math.abs(value) <= EPSILON)
            this.matrix[i][j] = 0.0;
    }
    // get an array representing the i-th row
    public double[] getRow(int i){
        return this.matrix[i];
    }
    // get an array representing the j-th column
    public double[] getColumn(int j) {
        return Arrays.stream(this.matrix).mapToDouble(x-> x[j]).toArray();
    }

    public void setRow(int i, double[] newRow) {
        for (int j = 0; j < columns; j++)
            this.setCell(i, j, newRow[j]);
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
    // string representation of the Matrix object
    public String toString() {
        StringBuilder s= new StringBuilder();
        for (double [] array : this.matrix) {
            s.append(Arrays.toString(array)).append("\n");
        }
        return s.deleteCharAt(s.length() - 1).toString();
    }

    /**
     * This method returns a boolean value expressing whether the current object and another one are equivalent
     * Due to float-point value errors, the equality between two matrices is true when the element-wise difference
     * is less than a certain epsilon. the current Epsilon is set to 10 ^ -9
     * @param another: any other Java object
     * @return
     */
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

    // This is a function that basically casts the current matrix to a Square Matrix:
    // raises an error if the dimensions are not equal
    public SquareMatrix toSquareMatrix() {
        if (this.rows != this.columns)
            throw new IllegalArgumentException("The matrix has different number of rows and columns");
        return new SquareMatrix(this.rows, this.matrix);
    }

    // get the row as a matrix
    public Matrix getRowMatrix(int i) {
        return new Matrix(new double[][] {getRow(i)});
    }
    // get the column as a matrix
    public Matrix getColMatrix(int j) {
        return UnaryMatrixOperations.transpose(new Matrix(new double[][]{getColumn(j)}));
    }

}



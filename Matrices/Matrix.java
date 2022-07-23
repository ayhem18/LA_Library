package Matrices;

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

    public double getCell(int i, int j) {
        return this.matrix[i][j];
    }

    public void setCell(int i, int j, double value) {
        this.matrix[i][j] = value;
    }

    public double[][] getMatrix() {
        return this.matrix;
    }
}

package Matrices;

/**
 * An extension
 */

public class SquareMatrix extends Matrix{
    public SquareMatrix(int side, double[][] matrix) {
        super(side, side, matrix);
    }

    public SquareMatrix(int side) {
        super(side, side);
    }

    public SquareMatrix(double[][] matrix) {
        super(matrix);
    }

    public SquareMatrix(SquareMatrix m) {
        super(m);
    }
}

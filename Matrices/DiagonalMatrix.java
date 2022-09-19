package Matrices;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * class representing diagonal matrices
 * storing solely the values at the diagonal
 */
public class DiagonalMatrix extends ElementaryMatrix{
    double[] diagonal;

    private void verifyDiagonal(double[] diagonal) {
        if (diagonal.length != this.side) {
            throw new IllegalArgumentException("The diagonal must of the length " + side);
        }
    }

    public DiagonalMatrix(int side, double[] diagonal) throws IllegalArgumentException{
        super(side);

        verifyDiagonal(diagonal);

        this.diagonal = diagonal;
        this.matrix = new SquareMatrix(side);
        setMatrix();
    }

    // the default constructor returns the identity matrix
    public DiagonalMatrix(int side) {
        this(side, IntStream.range(0, side).mapToDouble(x -> 1.0).toArray());
    }

    public void setMatrix(){
        for (int k = 0; k < this.side; k++) {
            this.matrix.setCell(k, k, this.diagonal[k]);
        }
    }

    public int getSide() {
        return this.side;
    }

    public double[] getDiagonal() {
        return diagonal;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof DiagonalMatrix d) {
            return this.side == d.getSide() && Arrays.equals(this.diagonal, d.getDiagonal());
        }
        return super.equals(another);
    }

}

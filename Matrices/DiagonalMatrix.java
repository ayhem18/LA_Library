package Matrices;

import java.util.stream.IntStream;

public class DiagonalMatrix extends ElementaryMatrix{
    double[] diagonal;
    public DiagonalMatrix(int side, double[] diagonal) {
        super(side);
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
}

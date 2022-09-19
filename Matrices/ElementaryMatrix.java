package Matrices;

/**
 * This class is a parent class to all special matrices:
 *      Permutation
 *      Elimination
 *      Diagonal
 * matrices
 */

public abstract class ElementaryMatrix {
    protected int side;
    protected SquareMatrix matrix;

    public ElementaryMatrix(int side) {
        this.side = side;
    }

    public int getSide() {
        return side;
    }

    public SquareMatrix getMatrix() {
        return matrix;
    }
    @Override
    public String toString() {
        return matrix.toString();
    }

    @Override
    public boolean equals(Object another) {
        return matrix.equals(another);
    }
}

package Matrices;

/**
 * This class is extremely crucial for the elimination algorithm as it represents the Elimination matrix responsable
 * for subtracting a multiple of one row from another.
 */
public class EliminationMatrix extends ElementaryMatrix {
    private final int i;
    private final int j;
    private final double eliminationValue;

    // make sure elimination matrix is not diagonal
    private void verifyArgs(int i, int j, double eliminationValue) {
        if (i == j) throw new IllegalArgumentException("The elimination value cannot lie on the matrix's diagonal");
        if (eliminationValue == 0) throw new IllegalArgumentException("The elimination value cannot be zero");
    }

    /**
     *
     * @param side: length of the matrix
     * @param i: row of the elimination value
     * @param j: column of the elimination value
     * @param eliminationValue: elimination value
     * @throws IllegalArgumentException
     */
    public EliminationMatrix(int side, int i, int j, double eliminationValue) throws IllegalArgumentException{
        super(side);
        verifyArgs(i, j, eliminationValue);
        this.i = i;
        this.j = j;
        this.eliminationValue = eliminationValue;
        this.matrix = new SquareMatrix(side);
        setMatrix();
    }

    // as the matrix attribute is final, the matrix is set to all zeros, then the diagonal values (1's) as well as
    // the elimination values are set
    private void setMatrix(){
        for (int k = 0; k < side; k++) {
            this.matrix.setCell(k, k, 1.0);
        }
        this.matrix.setCell(i, j, eliminationValue);
    }
    // equal attributes == equal matrix
    @Override
    public boolean equals(Object another) {
        if (another instanceof EliminationMatrix e) {
            return this.side == e.side &&
                    this.i == e.getI() && this.j == e.getJ() &&
                    this.eliminationValue == e.getEliminationValue();
        }
        return super.equals(another);
    }

    public int getSide() {
        return side;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getEliminationValue() {
        return eliminationValue;
    }
}

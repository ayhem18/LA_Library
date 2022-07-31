package Matrices;

public class EliminationMatrix extends ElementaryMatrix {
    private final int i;
    private final int j;
    private final double eliminationValue;

    private void verifyArgs(int i, int j, double eliminationValue) {
        if (i == j) throw new IllegalArgumentException("The elimination value cannot lie on the matrix's diagonal");
        if (eliminationValue == 0) throw new IllegalArgumentException("The elimination value cannot be zero");
    }
    public EliminationMatrix(int side, int i, int j, double eliminationValue) throws IllegalArgumentException{
        super(side);
        verifyArgs(i, j, eliminationValue);
        this.i = i;
        this.j = j;
        this.eliminationValue = eliminationValue;
        this.matrix = new SquareMatrix(side);
        setMatrix();
    }

    private void setMatrix(){
        for (int k = 0; k < side; k++) {
            this.matrix.setCell(k, k, 1.0);
        }
        this.matrix.setCell(i, j, eliminationValue);
    }

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

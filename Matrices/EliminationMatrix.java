package Matrices;

import MatrixOperations.ElementOperations;

public class EliminationMatrix extends ElementaryMatrix {
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

    private final int i;
    private final int j;
    private final double eliminationValue;

    public EliminationMatrix(int side, int i, int j, double eliminationValue) {
        super(side);
        this.i = i;
        this.j = j;
        this.eliminationValue = eliminationValue;
        this.matrix = new SquareMatrix(side);
        setMatrix();
    }

    public void setMatrix(){
        for (int k = 0; k < side; k++) {
            this.matrix.setCell(k, k, 1.0);
        }
        this.matrix.setCell(i, j, eliminationValue);
    }
}

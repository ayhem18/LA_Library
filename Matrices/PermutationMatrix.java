package Matrices;

public class PermutationMatrix extends ElementaryMatrix {
    private final int[] positions;

    public PermutationMatrix(int side, int[] positions) {
        super(side);
        this.positions = positions;
        this.matrix = new SquareMatrix(side);
        this.setMatrix();
    }

    private void setMatrix() {
        for (int i = 0; i < this.side; i++) {
            this.matrix.setCell(i, this.positions[i], 1);
        }
    }

    public int[] getPositions() {
        return positions;
    }
}

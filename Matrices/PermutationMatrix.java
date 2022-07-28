package Matrices;

import Utilities.HelperFunctions;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PermutationMatrix extends ElementaryMatrix {
    private final int[] positions;

    private void verifyPositions(int[] positions) throws IllegalArgumentException{
        int[] positions_sorted = positions.clone();
        Arrays.sort(positions_sorted);
        if (! (positions.length == side &&
                Arrays.equals(IntStream.range(0, side).toArray(),positions_sorted)))
            throw new IllegalArgumentException("The positions' array must be a permutation of [1.." + this.side + "]");
    }
    public PermutationMatrix(int side, int[] positions) {
        super(side);
        verifyPositions(positions);
        this.positions = positions;
        this.matrix = new SquareMatrix(side);
        this.setMatrix();
    }

    public PermutationMatrix(int side, int pos1, int pos2) {
        this(side, HelperFunctions.specialPermutation(side, pos1, pos2));
    }
    private void setMatrix() {
        for (int i = 0; i < this.side; i++) {
            this.matrix.setCell(i, this.positions[i], 1);
        }
    }

    public int[] getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof PermutationMatrix p) {
            return this.side == p.getSide() && Arrays.equals(this.positions, p.getPositions());
        }
        return super.equals(another);
    }
}

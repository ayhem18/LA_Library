import ArraysOperations.ArrayOp2D;
import ArraysOperations.ArrayOpException;
import Matrices.*;

import MatrixOperations.*;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static Random  generator = new Random();

    public static void main(String[] args) throws Exception{
        testInversesComplete();
    }

    public static void testInversesComplete() throws Exception{
        List<Matrix> singularMats = new ArrayList<>();
        for (int i = 0; i < 300; i++){
            testInverses(singularMats);
        }

        System.out.println("########################################################################################");
        System.out.println("########################################################################################");
        if (!singularMats.isEmpty()) {
            for (int i = 0; i < singularMats.size(); i += 4) {
                System.out.println(UnaryMatrixOperations.inverse(singularMats.get(i).toSquareMatrix()));
            }
        }

    }
    public static void testInverses(List<Matrix> singularMats) throws Exception{
        int side = generator.nextInt(2, 20);
        SquareMatrix m = new SquareMatrix(side ,random2DArray(side, side));
        SquareMatrix inverse = UnaryMatrixOperations.inverse(m);
        DiagonalMatrix I = new DiagonalMatrix(side);
        System.out.println("matrix \n" + m);
        System.out.println("inverse \n" + inverse);
        Matrix mr = BinaryMatrixOperations.matMultiplication(inverse, m);
        Matrix ml = BinaryMatrixOperations.matMultiplication(m, inverse);
        System.out.println("multiplication left \n" + mr);
        System.out.println("multiplication right \n" + ml);
        if (!(I.equals(mr) && I.equals(ml))) {
            singularMats.add(m);
            throw new Exception("Fix your code buddy");
        }
    }

    public static void testElementaryInverses () throws Exception {
        ElementaryMatrix e = randomElementaryMatrix();
        int side = e.getSide();
        DiagonalMatrix I = new DiagonalMatrix(side);
        ElementaryMatrix eInv = UnaryMatrixOperations.inverse(e);
        System.out.println("matrix \n" + e);
        System.out.println("Inverse \n" + eInv);
        Matrix r ;
        System.out.println("mul result \n" + (r = BinaryMatrixOperations.matMultiplication(e, eInv)));
        if (!I.equals(r)) {
            throw new Exception();
        }
    }

    public static ElementaryMatrix randomElementaryMatrix() {
        int choice = generator.nextInt(3);
        int side = generator.nextInt(3, 50);
        if (choice == 0) {
            return new EliminationMatrix(side, generator.nextInt(side),
                    generator.nextInt(side), generator.nextInt(-10, 10));
        }
        else if (choice == 1) {
            List<Integer> l = new ArrayList<>();
            int[] a = IntStream.range(0, side).toArray();
            for (int v: a) l.add(v);
            Collections.shuffle(l);
            for (int i = 0; i < side; i++) a[i] = l.get(i);

            return new PermutationMatrix(side, a);
        }
        else {
            return new DiagonalMatrix(side,
                    IntStream.range(0,side).mapToDouble(x-> {
                            int r = generator.nextInt(-20, 20);
                                    return (r == 0) ? 1: r;}).toArray());
        }
    }


    public static void testElementaryMultiplication() throws MatrixException {
        for (int i = 0; i < 5; i++) {
//            EliminationMatrix m1 = new EliminationMatrix(3,
//                    generator.nextInt(3),
//                    generator.nextInt(3), generator.nextInt(-4, 4));
//            PermutationMatrix p1 = new PermutationMatrix(6, new int[] {1, 0, 2, 4, 5, 3});
            DiagonalMatrix d1 = new DiagonalMatrix(6,
                    IntStream.range(0, 6).mapToDouble(x-> generator.nextInt(-10, 10)).toArray());

            int col = generator.nextInt(1, 6);
            Matrix m = new Matrix(6, col, random2DArray(6, col));
            System.out.println("Permutation matrix:\n" + d1);
            System.out.println("other matrix:\n" + m);
            System.out.println("Their multiplication:\n" + BinaryMatrixOperations.matMultiplication(d1, m));
            System.out.println("###########################################");
            System.out.println("###########################################");
        }
    }

    public static void testElementWiseOp() throws Exception{
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int x = r.nextInt(500);
            System.out.println(x);
            Matrix oneNumberMat = new Matrix(5, 5, x);
            Matrix zero = new Matrix(5, 5);
            System.out.println("matrix 1 \n" + oneNumberMat);
            System.out.println("matrix 2 \n" + zero);
            System.out.println(ElementOperations.add(oneNumberMat, zero));
            System.out.println("##########################################");
            System.out.println("##########################################");
        }
    }
    public static double[][] random2DArray(int rows, int cols) {
        double[][] result = new double[rows][cols];
        Random gen = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = gen.nextInt(0, 100);
            }
        }
        return result;
    }

    public static void testArrayOp() throws ArrayOpException {
        for (int i = 0; i < 5; i++) {
            double[][] a1 = random2DArray(3, 3);
            double[][] a2 = random2DArray(3, 3);
            System.out.println("Array1");
            Arrays.stream(a1).forEach(x-> System.out.println(Arrays.toString(x)));
            System.out.println("Array2");
            Arrays.stream(a2).forEach(x-> System.out.println(Arrays.toString(x)));
            System.out.println("Their product");
            Arrays.stream(ArrayOp2D.divide(a1, a2)).forEach(x-> System.out.println(Arrays.toString(x)));

            System.out.println("##############################################");
        }
    }

    public static void testMatrixMultiplication() throws MatrixException {
        for (int i = 0; i < 6; i++) {
            Matrix m1 = new Matrix(2, 3, random2DArray(2, 3));
            Matrix m2 = new Matrix(3, 3, random2DArray(3, 3));
            System.out.println("This is the first matrix\n");
            System.out.println(m1);

            System.out.println("This is the second matrix\n");
            System.out.println(m2);

            System.out.println("This is the multiplication's result\n");
            System.out.println(BinaryMatrixOperations.matMultiplication(m1, m2));

            System.out.println("########################################################");
            System.out.println("########################################################");
        }
    }
}
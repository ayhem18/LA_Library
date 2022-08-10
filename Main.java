import ArraysOperations.ArrayOp2D;
import ArraysOperations.ArrayOpException;
import Equations.VectorEquation;
import Matrices.*;
import MatrixOperations.BinaryMatrixOperations;
import MatrixOperations.ElementOperations;
import MatrixOperations.MatrixException;
import MatrixOperations.UnaryMatrixOperations;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static Random  generator = new Random();

    public static void testRREF() throws Exception{
        for (int i = 0; i < 10000; i++) {
            Matrix a = new Matrix(random2DArray(generator.nextInt(2, 10), generator.nextInt(2, 10)));
            //System.out.println(a);
            Matrix[] elimination = UnaryMatrixOperations.RREF(a);
            Matrix rref = elimination[0];
            Matrix record = elimination[1];
//            System.out.println("AFTER RREF");
//            System.out.println(a);
//            System.out.println("#########################");
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            System.out.println(rref);
            boolean check = BinaryMatrixOperations.matMultiplication(record, a).equals(rref);
            if (!check){
                System.out.println(a);
                System.out.println("#########################");
                System.out.println(rref);
                System.out.println("##############################");
                System.out.println(BinaryMatrixOperations.matMultiplication(record, a));
                System.out.println("################################");
                throw new Exception();}
        }
    }
    public static void testAXB() throws Exception {
        for (int i = 0; i < 1000; i++) {
            Matrix a = new Matrix(random2DArray(generator.nextInt(2, 10), generator.nextInt(2, 10)));
            for (int j = 0; j < 100000; j++) {
                Matrix b = new Matrix(random2DArray(a.getRows(), 1));
                List<Matrix> sols = VectorEquation.Equation(a, b);
                boolean check=true;
                if (!sols.isEmpty()) {
                    System.out.println("NON EMPTY");check = BinaryMatrixOperations.matMultiplication(a, sols.get(0)).equals(b);}
                if (!check) {
                    System.out.println("a\n" + a);
                    System.out.println("b\n" + b);
                    System.out.println("result\n");
                    System.out.println(BinaryMatrixOperations.matMultiplication(a, sols.get(0)));
                    throw new Exception();
                }
            }
        }
    }

    public static void t() throws Exception {
//        Matrix a = new Matrix(random2DArray(generator.nextInt(2, 10), generator.nextInt(2, 10)));
        Matrix a = new Matrix(new double[][] {{14, 21, 66, 64}, {18, 27, 41, 74}});
        Matrix[] elimination = UnaryMatrixOperations.RREF(a);
        Matrix rref = elimination[0];
        Matrix record = elimination[1];
        System.out.println("A\n" + a);
        System.out.println("RREF \n" + rref);
        System.out.println("RECORD \n" + record);
    }


    public static void main(String[] args) throws Exception{
//        for (int i = 0; i < 50; i++ ) {
//            Matrix a = new Matrix(new double[][]{{1,2,3,4,5,5,8}, {1,2,3,4,5,5,8}, {1,2,3,4,5,5,8}, {1,2,3,1,5,2,20}});
//            Matrix b = new Matrix(random2DArray(4, 1));
//            System.out.println("A \n" + a + "\n");
//            System.out.println("B \n" + b + "\n");
//            List<Matrix> sols = VectorEquation.Equation(a, b);
//            System.out.println("Particular solution for Ax = b \n");
//            if (!sols.isEmpty())
//                System.out.println(BinaryMatrixOperations.matMultiplication(a, sols.get(0)).equals(b));
//        }
//        Matrix a = new Matrix(new double[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1,0}, {0, 0, 0,1}, {0, 0, 0,0},
//                {0, 0, 0,0}, {0, 0, 0,0}, {0, 0, 0, 0}});
//        Matrix[] elimination = UnaryMatrixOperations.RREF(a);
//        Matrix rref = elimination[0];
//        Matrix record = elimination[1];
//        System.out.println(a);
//        System.out.println("##############################");
//        System.out.println(rref);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        System.out.println(record);
//        System.out.println(BinaryMatrixOperations.matMultiplication(record, a).equals(rref));
        testAXB();
    }

    public static void testNullSpace() throws Exception {
        for (int z = 0; z < 100; z++) {
            for (int x = 0; x < 200; x++) {
                Matrix a = new Matrix(
                        random2DArray(generator.nextInt(2,8), generator.nextInt(2, 8)));
                for (int i = 0; i < 20; i++)
                    testNullSpace(a);
            }
        }
    }

    public static void testNullSpace(Matrix a) throws Exception {
        Matrix[] nullSpace = VectorEquation.Equation(a);
        Matrix finalSol = new Matrix(nullSpace[0].getRows(), nullSpace[0].getColumns());
        for (Matrix sol: nullSpace) {
            finalSol = ElementOperations.add(finalSol, ElementOperations.multiply(generator.nextInt(0, 50), sol));
        }
        if (!BinaryMatrixOperations.matMultiplication(a, finalSol).equals(new Matrix(a.getRows(), 1)))
            throw new Exception();
    }

    public static void testLUFac() throws Exception {
        int side = generator.nextInt(3, 10);
        SquareMatrix a = new SquareMatrix(random2DArray(side, side));
        SquareMatrix[] aLU = UnaryMatrixOperations.LUFactorization(a);
        Matrix product = BinaryMatrixOperations.matMultiplication(aLU[0], aLU[1]);
        System.out.println("matrix: \n" +  a);
        System.out.println("L: \n" + aLU[0]);
        System.out.println("U: \n" + aLU[1]);
        System.out.println("LU:\n" + product);
        // System.out.println(a.equals(product));
        if (!a.equals(product)) throw new Exception();
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
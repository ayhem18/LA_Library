import ArraysOperations.ArrayOp2D;
import ArraysOperations.ArrayOpException;
import Matrices.DiagonalMatrix;
import Matrices.EliminationMatrix;
import Matrices.Matrix;

import Matrices.PermutationMatrix;
import MatrixOperations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    static Random  generator = new Random();

    public static void main(String[] args) throws Exception{
        testElementaryMultiplication();
    }

    public static void testElementaryMultiplication() throws Exception {
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
            System.out.println("Their multiplication:\n" + MatrixOperations.matMultiplication(d1, m));
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
            System.out.println(MatrixOperations.matMultiplication(m1, m2));

            System.out.println("########################################################");
            System.out.println("########################################################");
        }
    }
}
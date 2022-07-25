import ArraysOperations.ArrayOp2D;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception{
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

}
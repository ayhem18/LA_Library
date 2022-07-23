import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double [][] array = {{1, 2, 4}, {3, 4, 2.5}, {10, 11, 12}};
        System.out.println(Arrays.toString(array[1]));
        array[1] = new double [] {2,4,5};
        for (double[] a : array) {
            System.out.println((Arrays.toString(a)));
        }
    }
}
package ArraysOperations;

import java.util.Arrays;

public class ZeroDivExceptionArray extends ArrayOpException {
    protected static String ErrorMessage(double[] row) {
        return "Division operation requires the second array to have non-zero elements \n" +
                "The array's raw: " + Arrays.toString(row) + "\n has at least one zero entry";
    }

    public ZeroDivExceptionArray(double[] row) {
        super(ErrorMessage(row));
    }
}

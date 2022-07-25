package ArraysOperations;

public class ArrayOpException extends Exception{
    public ArrayOpException(String message) {
        super(message);
    }
}

class ArrayOp1DException extends ArrayOpException{
    public ArrayOp1DException(String message) {
        super(message);
    }
}

class ArrayOp2DException extends ArrayOpException{
    public ArrayOp2DException(String message) {
        super(message);
    }
}



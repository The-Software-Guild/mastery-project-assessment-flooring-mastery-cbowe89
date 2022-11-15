package FlooringMastery.service;

public class OrderBuildException extends Exception {
    public OrderBuildException(String msg) {
        super(msg);
    }

    public OrderBuildException(String msg, Throwable cause) {
        super(msg,cause);
    }
}

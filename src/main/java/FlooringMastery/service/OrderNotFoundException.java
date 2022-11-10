package FlooringMastery.service;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String msg) {
        super(msg);
    }

    public OrderNotFoundException(String msg, Throwable cause) {
        super(msg,cause);
    }
}

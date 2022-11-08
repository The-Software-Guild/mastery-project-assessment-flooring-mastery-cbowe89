package FlooringMastery.service;

public class OrderFileNotFoundException extends Exception {
    public OrderFileNotFoundException(String msg) {
        super(msg);
    }

    public OrderFileNotFoundException(String msg, Throwable cause) {
        super(msg,cause);
    }
}

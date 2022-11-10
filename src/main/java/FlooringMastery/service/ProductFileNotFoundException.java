package FlooringMastery.service;

public class ProductFileNotFoundException extends Exception {
    public ProductFileNotFoundException(String msg) {
        super(msg);
    }

    public ProductFileNotFoundException(String msg, Throwable cause) {
        super(msg,cause);
    }
}

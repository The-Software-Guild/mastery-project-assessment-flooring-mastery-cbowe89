package FlooringMastery.service;

public class TaxFileNotFoundException extends Exception {
    public TaxFileNotFoundException(String msg) {
        super(msg);
    }

    public TaxFileNotFoundException(String msg, Throwable cause) {
        super(msg,cause);
    }
}

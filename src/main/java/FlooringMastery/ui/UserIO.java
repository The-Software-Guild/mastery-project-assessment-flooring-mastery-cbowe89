package FlooringMastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {
    void print(String message);

    void printf(String message, LocalDate date);

    void printf(String message, BigDecimal min);

    void printf(String message, int min, int max);

    void printf(String message, BigDecimal min, BigDecimal max);

    String readString(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    BigDecimal readBigDecimal(String prompt);

    BigDecimal readBigDecimal(String prompt, BigDecimal min);

    BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max);

    LocalDate readDate(String prompt);

    LocalDate readDate(String prompt, LocalDate min);
}

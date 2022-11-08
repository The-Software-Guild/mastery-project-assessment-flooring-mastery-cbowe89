package FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class State {
    private String stateAbbr;
    private String stateName;
    private BigDecimal taxRate;

    public State(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public State(String stateAbbr, String stateName, BigDecimal taxRate) {
        this.stateAbbr = stateAbbr;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return "States{" + "stateAbbr=" + stateAbbr + ", stateName="
                + stateName + ", taxRate=" + taxRate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(stateAbbr, state.stateAbbr)
                && Objects.equals(stateName, state.stateName)
                && Objects.equals(taxRate, state.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateAbbr, stateName, taxRate);
    }
}

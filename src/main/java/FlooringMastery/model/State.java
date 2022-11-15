package FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The {@code State} class is responsible for creating new State objects
 * based on the values passed to the constructor. It includes getter and
 * setter methods for all State object values. It overrides the toString,
 * equals, and hashcode methods.
 */
public class State {
    // Values for Order objects
    private String stateAbbr;
    private String stateName;
    private BigDecimal taxRate;

    /**
     * Constructor takes one parameter (stateAbbr) and
     * creates a new State object
     * @param stateAbbr state abbreviation
     */
    public State(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    /**
     * Constructor takes 3 parameters and creates a new State object
     * @param stateAbbr state abbreviation
     * @param stateName state name
     * @param taxRate state tax rate
     */
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

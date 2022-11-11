package FlooringMastery.dao;

import FlooringMastery.model.State;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StateDao {
    List<State> getStateInfoList() throws PersistenceException;

    List<String> getStateNameList() throws PersistenceException;

    List<String> getStateAbbrList() throws PersistenceException;

    Map<String, String> getStateNameAbbrMap() throws
            PersistenceException;

    Map<String, BigDecimal> getStateNameTaxMap() throws PersistenceException;

    Map<String, BigDecimal> getStateAbbrTaxMap() throws PersistenceException;
}

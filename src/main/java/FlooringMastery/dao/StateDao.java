package FlooringMastery.dao;

import FlooringMastery.model.State;

import java.util.List;

public interface StateDao {
    List<State> getStateInfoList() throws PersistenceException;

    List<String> getStateAbbrList() throws PersistenceException;
}

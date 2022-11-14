package FlooringMastery.dao;

import FlooringMastery.model.State;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateDaoImpl implements StateDao {
    private final FileDao FILE_DAO;

    /**
     * No-args construct for StateDaoImpl creates a new instance
     * of the FileDaoImpl.
     */
    public StateDaoImpl() {
        this.FILE_DAO = new FileDaoImpl();
    }

    /**
     * Gets and returns a list of all State objects
     * @return list of State objects
     * @throws PersistenceException if unable to read State file
     */
    @Override
    public List<State> getStateInfoList() throws PersistenceException {
        // Passes name of state file to method in the FILE_DAO
        return FILE_DAO.readTaxFile("Data/Taxes.txt");
    }

    /**
     * Creates and returns a list of all state names
     * @return String list of state names
     * @throws PersistenceException if unable to retrieve data
     */
    @Override
    public List<String> getStateNameList() throws PersistenceException {
        // Call getStateInfoList() to get list of all States
        List<State> stateInfoList = getStateInfoList();

        // Declare and initialize a new ArrayList to store state names
        List<String> stateNameList = new ArrayList<>();

        // Iterate over stateInfoList, add stateName of each State to
        // stateNameList
        for (State state : stateInfoList)
            stateNameList.add(state.getStateName());

        // Return stateNameList
        return stateNameList;
    }

    /**
     * Creates and returns a list of all state abbreviations
     * @return String list of state abbreviations
     * @throws PersistenceException if unable to retrieve data
     */
    @Override
    public List<String> getStateAbbrList() throws PersistenceException {
        // Call getStateInfoList() to get list of all States
        List<State> stateInfoList = getStateInfoList();

        // Declare and initialize a new ArrayList to store state abbreviations
        List<String> stateAbbrList = new ArrayList<>();

        // Iterate over stateInfoList, add stateAbbr of each State to
        // stateAbbrList
        for (State state : stateInfoList)
            stateAbbrList.add(state.getStateAbbr());

        // Return stateNameList
        return stateAbbrList;
    }

    /**
     * Creates a map to store a state abbreviation (value) with its
     * corresponding state name (key)
     * @return map of state names and abbreviations
     * @throws PersistenceException if unable to retrieve data
     */
    @Override
    public Map<String, String> getStateNameAbbrMap() throws
            PersistenceException {
        // Call getStateInfoList() to get list of all States
        List<State> stateInfoList = getStateInfoList();

        // Declare and initialize a new HashMap to store state names
        // with their abbreviations
        Map<String, String> stateNameAbbrMap = new HashMap<>();

        // Iterate over stateInfoList, put key-value pair into map
        // State names are the keys, state abbreviations are the values
        for (State state : stateInfoList)
            stateNameAbbrMap.put(state.getStateName(), state.getStateAbbr());

        // Return stateNameAbbrMap
        return stateNameAbbrMap;
    }

    /**
     * Creates a map to store a state tax rate (value) with its
     * corresponding state name (key)
     * @return map of state names and tax rates
     * @throws PersistenceException if unable to retrieve data
     */
    @Override
    public Map<String, BigDecimal> getStateNameTaxMap() throws PersistenceException {
        // Call getStateInfoList() to get list of all States
        List<State> stateInfoList = getStateInfoList();

        // Declare and initialize a new HashMap to store state names
        // with their tax rates
        Map<String, BigDecimal> stateNameTaxMap = new HashMap<>();

        // Iterate over stateInfoList, put key-value pair into map
        // State names are the keys, state tax rates are the values
        for (State state : stateInfoList)
            stateNameTaxMap.put(state.getStateName(), state.getTaxRate());

        // Return stateNameTaxMap
        return stateNameTaxMap;
    }

    /**
     * Creates a map to store a state tax rate (value) with its
     * corresponding state abbreviation (key)
     * @return map of state abbreviations and tax rates
     * @throws PersistenceException if unable to retrieve data
     */
    @Override
    public Map<String, BigDecimal> getStateAbbrTaxMap() throws PersistenceException {
        // Call getStateInfoList() to get list of all States
        List<State> stateInfoList = getStateInfoList();

        // Declare and initialize a new HashMap to store state abbreviations
        // with their tax rates
        Map<String, BigDecimal> stateAbbrTaxMap = new HashMap<>();

        // Iterate over stateInfoList, put key-value pair into map
        // State abbreviations are the keys, state tax rates are the values
        for (State state : stateInfoList)
            stateAbbrTaxMap.put(state.getStateAbbr(), state.getTaxRate());

        // Return stateAbbrTaxMap
        return stateAbbrTaxMap;
    }
}

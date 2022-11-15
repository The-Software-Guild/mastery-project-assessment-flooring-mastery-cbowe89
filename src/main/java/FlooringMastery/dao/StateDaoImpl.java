package FlooringMastery.dao;

import FlooringMastery.model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code StateDaoImpl} class is responsible for interacting
 * with the State objects (state and tax information) in the
 * Flooring Order App
 */
public class StateDaoImpl implements StateDao {
    // Declare variable for FileDao object
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
}

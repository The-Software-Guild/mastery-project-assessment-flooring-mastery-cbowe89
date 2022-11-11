package FlooringMastery.dao;

import FlooringMastery.model.State;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateDaoImpl implements StateDao {
    private final FileDao FILE_DAO;

    public StateDaoImpl() {
        this.FILE_DAO = new FileDaoImpl();
    }

    @Override
    public List<State> getStateInfoList() throws PersistenceException {
        return FILE_DAO.readTaxFile("Data/Taxes.txt");
    }

    @Override
    public List<String> getStateNameList() throws PersistenceException {
        List<State> stateInfoList = getStateInfoList();
        List<String> stateNameList = new ArrayList<>();
        for (State state : stateInfoList)
            stateNameList.add(state.getStateName());
        return stateNameList;
    }

    @Override
    public List<String> getStateAbbrList() throws PersistenceException {
        List<State> stateInfoList = getStateInfoList();
        List<String> stateAbbrList = new ArrayList<>();
        for (State state : stateInfoList)
            stateAbbrList.add(state.getStateAbbr());
        return stateAbbrList;
    }

    @Override
    public Map<String, String> getStateNameAbbrMap() throws
            PersistenceException {
        List<State> stateInfoList = getStateInfoList();
        Map<String, String> stateNameAbbrMap = new HashMap<>();
        for (State state : stateInfoList)
            stateNameAbbrMap.put(state.getStateName(), state.getStateAbbr());
        return stateNameAbbrMap;
    }

    @Override
    public Map<String, BigDecimal> getStateNameTaxMap() throws PersistenceException {
        List<State> stateInfoList = getStateInfoList();
        Map<String, BigDecimal> stateNameTaxMap = new HashMap<>();
        for (State state : stateInfoList)
            stateNameTaxMap.put(state.getStateName(), state.getTaxRate());
        return stateNameTaxMap;
    }

    @Override
    public Map<String, BigDecimal> getStateAbbrTaxMap() throws PersistenceException {
        List<State> stateInfoList = getStateInfoList();
        Map<String, BigDecimal> stateAbbrTaxMap = new HashMap<>();
        for (State state : stateInfoList)
            stateAbbrTaxMap.put(state.getStateAbbr(), state.getTaxRate());
        return stateAbbrTaxMap;
    }
}

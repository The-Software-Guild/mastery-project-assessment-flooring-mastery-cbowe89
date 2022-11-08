package FlooringMastery.service;

import FlooringMastery.dao.*;
import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceLayerImpl implements ServiceLayer {
    private final OrderDao ORDER_DAO;
    private final AuditDao AUDIT_DAO;
    private final FileDao FILE_DAO;

    public ServiceLayerImpl() {
        ORDER_DAO = new OrderDaoImpl();
        AUDIT_DAO = new AuditDaoImpl();
        FILE_DAO = new FileDaoImpl();
    }

    public ServiceLayerImpl(OrderDao orderDao, AuditDao auditDao,
                            FileDao fileDao) {
        this.ORDER_DAO = orderDao;
        this.AUDIT_DAO = auditDao;
        this.FILE_DAO = fileDao;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException {
        return ORDER_DAO.getOrder(orderNumber, orderDate);
    }

    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException, OrderFileNotFoundException {
        List<Order> ordersForDateEntered = ORDER_DAO.getAllOrders(dateEntered);

        if (ordersForDateEntered.isEmpty())
            throw new OrderFileNotFoundException("No orders for date entered.");
        else
            return ordersForDateEntered;
    }

    @Override
    public Order createNewOrder(String newCustomerName, String newOrderState,
                                String productType, BigDecimal newOrderArea)
            throws PersistenceException, InvalidStateException {
        List<String> stateNameList = getStateNameList();
        if (!stateNameList.contains(newOrderState))
            throw new InvalidStateException("State name not found.");
        else
            return ORDER_DAO.createNewOrder(newCustomerName,
                    newOrderState, productType, newOrderArea);
    }

    @Override
    public void addNewOrder(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException {
        ORDER_DAO.addNewOrderToFile(newOrderDate, newOrder);
        // Audit entry
    }

    @Override
    public void exportAllOrders() throws PersistenceException {
        String exportFile = "Backup/DataExport.txt";
        ORDER_DAO.exportAllData(exportFile);
        AUDIT_DAO.writeAuditEntry("All order data exported.");
    }

    @Override
    public List<State> getStateInfoList() throws PersistenceException {
        return FILE_DAO.readTaxFile("Data/Taxes.txt");
    }

    @Override
    public List<String> getStateNameList() throws PersistenceException{
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
    public List<Product> getProductList() throws PersistenceException {
        return FILE_DAO.readProductFile("Data/Products.txt");
    }

    @Override
    public List<String> getProductTypeList() throws PersistenceException {
        List<Product> productList = getProductList();
        List<String> productTypeList = new ArrayList<>();
        for (Product product : productList)
            productTypeList.add(product.getProductType());
        return productTypeList;
    }
}

package FlooringMastery.service;

import FlooringMastery.dao.*;
import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ServiceLayerImpl implements ServiceLayer {
    private final OrderDao ORDER_DAO;
    private final ProductDao PRODUCT_DAO;
    private final StateDao STATE_DAO;
    private final AuditDao AUDIT_DAO;
    private final FileDao FILE_DAO;

    public ServiceLayerImpl() {
        ORDER_DAO = new OrderDaoImpl();
        PRODUCT_DAO = new ProductDaoImpl();
        STATE_DAO = new StateDaoImpl();
        AUDIT_DAO = new AuditDaoImpl();
        FILE_DAO = new FileDaoImpl();
    }

    public ServiceLayerImpl(OrderDao orderDao, ProductDao productDao,
                            StateDao stateDao, AuditDao auditDao,
                            FileDao fileDao) {
        this.ORDER_DAO = orderDao;
        this.PRODUCT_DAO = productDao;
        this.STATE_DAO = stateDao;
        this.AUDIT_DAO = auditDao;
        this.FILE_DAO = fileDao;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException, OrderNotFoundException {
        try {
            return ORDER_DAO.getOrder(orderNumber, orderDate);
        } catch (PersistenceException e) {
            throw new OrderNotFoundException("No order found matching the "
                    + "date and order number entered.");
        }
    }

    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException, OrderFileNotFoundException {
        try {
            return ORDER_DAO.getAllOrders(dateEntered);
        } catch (PersistenceException e) {
            throw new OrderFileNotFoundException("No orders for date entered.");
        }
    }

    @Override
    public Order createNewOrder(String newCustomerName, String newOrderState,
                                String productType, BigDecimal newOrderArea)
            throws PersistenceException, InvalidStateException {
        try {
            List<String> stateNameList = getStateNameList();
            return ORDER_DAO.createNewOrder(newCustomerName,
                    newOrderState, productType, newOrderArea);
        } catch (PersistenceException | TaxFileNotFoundException e) {
            throw new InvalidStateException("State name not found.");
        }
    }

    @Override
    public void addNewOrder(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException {
        ORDER_DAO.addNewOrderToFile(newOrderDate, newOrder);
        // Audit entry
    }

    @Override
    public void editOrder(LocalDate orderDate, Order editedOrder)
            throws PersistenceException {
        ORDER_DAO.editOrder(orderDate, editedOrder);
        // Audit Entry
    }

    @Override
    public void removeOrder(LocalDate orderDate, Order orderToRemove)
            throws PersistenceException {
        ORDER_DAO.removeOrder(orderDate, orderToRemove);
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
        return STATE_DAO.getStateInfoList();
    }

    @Override
    public List<String> getStateNameList() throws PersistenceException,
            TaxFileNotFoundException {
        try {
            return STATE_DAO.getStateNameList();
        } catch (PersistenceException e) {
            throw new TaxFileNotFoundException("Could not retrieve State/Tax data.");
        }
    }

    @Override
    public List<String> getStateAbbrList() throws PersistenceException,
            TaxFileNotFoundException {
        try {
            return STATE_DAO.getStateAbbrList();
        } catch (PersistenceException e) {
            throw new TaxFileNotFoundException("Could not retrieve State/Tax data.");
        }
    }

    @Override
    public List<Product> getProductList() throws PersistenceException,
            ProductFileNotFoundException {
        try {
            return PRODUCT_DAO.getAllProducts();
        } catch (PersistenceException e) {
            throw new ProductFileNotFoundException("Could not retrieve Products.");
        }
    }

    @Override
    public List<String> getProductTypeList() throws PersistenceException,
            ProductFileNotFoundException {
        try {
            return PRODUCT_DAO.getProductTypeList();
        } catch (PersistenceException e) {
            throw new ProductFileNotFoundException("Could not retrieve Products.");
        }
    }
}

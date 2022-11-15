package FlooringMastery.service;

import FlooringMastery.dao.*;
import FlooringMastery.model.Order;
import FlooringMastery.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code ServiceLayerImpl} class is responsible for
 * communicating with the DAO objects (OrderDaoImpl,
 * ProductDaoImpl, and StateDaoImpl).
 */
public class ServiceLayerImpl implements ServiceLayer {
    // Declare DAO objects
    private final OrderDao ORDER_DAO;
    private final ProductDao PRODUCT_DAO;
    private final StateDao STATE_DAO;

    /**
     * No-args constructor creates a ServiceLayerImpl object
     * with new instances of each object.
     */
    public ServiceLayerImpl() {
        ORDER_DAO = new OrderDaoImpl();
        PRODUCT_DAO = new ProductDaoImpl();
        STATE_DAO = new StateDaoImpl();
    }

    /**
     * Constructor takes in 3 parameters (different Dao objects) and
     * creates a ServiceLayerImpl object
     * @param orderDao OrderDaoImpl object
     * @param productDao ProductDaoImpl object
     * @param stateDao StateDaoImpl object
     */
    public ServiceLayerImpl(OrderDao orderDao, ProductDao productDao,
                            StateDao stateDao) {
        this.ORDER_DAO = orderDao;
        this.PRODUCT_DAO = productDao;
        this.STATE_DAO = stateDao;
    }

    /**
     * Gets an Order object from the appropriate order file based on the
     * orderDate and orderNumber that are passed to this method.
     * @param orderNumber Order number
     * @param orderDate Date of Order
     * @return Order object matching the parameters
     * @throws OrderNotFoundException if unable to locate corresponding order
     */
    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws OrderNotFoundException {
        try {
            return ORDER_DAO.getOrder(orderNumber, orderDate);
        } catch (PersistenceException e) {
            throw new OrderNotFoundException("No order found matching the "
                    + "date and order number entered.", e);
        }
    }

    /**
     * Gets a list of Order objects from the order file that corresponds
     * to the date passed to this method.
     * @param dateEntered Date to retrieve orders for
     * @return List of Order objects for the date entered
     * @throws PersistenceException if no order file exists for the date
     */
    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException {
        return ORDER_DAO.getAllOrders(dateEntered);
    }

    @Override
    public Order createNewOrder(String newCustomerName, String newOrderState,
                                String productType, BigDecimal newOrderArea)
            throws OrderBuildException {
        try {
            return ORDER_DAO.createNewOrder(newCustomerName,
                    newOrderState, productType, newOrderArea);
        } catch (PersistenceException e) {
            throw new OrderBuildException("Could not create new Order", e);
        }
    }

    @Override
    public void addNewOrder(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException {
        ORDER_DAO.addNewOrderToFile(newOrderDate, newOrder);
    }

    @Override
    public Order createEditedOrder(Order orderToEdit, String newName,
                                   String newState, String newProductType,
                                   BigDecimal newArea) throws OrderBuildException {
        try {
            return ORDER_DAO.createEditedOrder(orderToEdit, newName, newState,
                    newProductType, newArea);
        } catch (PersistenceException e) {
            throw new OrderBuildException("Could not create edited order", e);
        }
    }

    @Override
    public void writeEditOrder(LocalDate orderDate, Order orderToEdit,
                          Order editedOrder) throws PersistenceException {
        ORDER_DAO.writeEditOrder(orderDate, orderToEdit, editedOrder);
    }

    @Override
    public void removeOrder(LocalDate orderDate, Order orderToRemove)
            throws PersistenceException {
        ORDER_DAO.removeOrder(orderDate, orderToRemove);
    }

    @Override
    public void exportAllOrders() throws PersistenceException {
        String exportFile = "Backup/DataExport.txt";
        ORDER_DAO.exportAllData(exportFile);
    }

    @Override
    public List<String> getStateAbbrList() throws PersistenceException {
        return STATE_DAO.getStateAbbrList();
    }

    @Override
    public List<Product> getProductList() throws PersistenceException {
        return PRODUCT_DAO.getAllProducts();
    }

    @Override
    public List<String> getProductTypeList() throws PersistenceException {
        return PRODUCT_DAO.getProductTypeList();
    }
}

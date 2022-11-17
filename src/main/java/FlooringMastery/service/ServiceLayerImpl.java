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
            throws OrderNotFoundException, PersistenceException {
        Order order =  ORDER_DAO.getOrder(orderNumber, orderDate);
        if (order == null) {
            throw new OrderNotFoundException("No order found matching the "
                    + "date and order number entered.");
        }
        return order;
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

    /**
     * Creates a new Order object passed on the parameters passed.
     * @param newCustomerName Customer name for new Order
     * @param newOrderState State for new Order
     * @param productType Product Type for new Order
     * @param newOrderArea Area (sq ft) for new Order
     * @return new Order object
     * @throws OrderBuildException if unable to create new Order object
     */
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

    /**
     * Adds a new order to the appropriate file, based on the date
     * @param newOrderDate Date of new Order object
     * @param newOrder new Order object
     * @throws PersistenceException if unable to write Order to file
     */
    @Override
    public void addNewOrder(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException {
        ORDER_DAO.addNewOrderToFile(newOrderDate, newOrder);
    }

    /**
     * Creates a new Order object passed on the parameters passed. This new
     * Order object is based on an original Order object with appropriately
     * updated values. The order number will be the same as the original Order.
     * @param orderToEdit original Order object
     * @param newName Order customer name
     * @param newState Order state
     * @param newProductType Order product type
     * @param newArea Order area (sq ft)
     * @return edited Order object
     * @throws OrderBuildException if unable to create Order object
     */
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

    /**
     * Updates an existing Order in a file to contain updated values.
     * @param orderDate Date of Order
     * @param orderToEdit Original Order object
     * @param editedOrder Edited Order object
     * @throws PersistenceException if unable to edit the Order in the file
     */
    @Override
    public void writeEditOrder(LocalDate orderDate, Order orderToEdit,
                          Order editedOrder) throws PersistenceException {
        ORDER_DAO.writeEditOrder(orderDate, orderToEdit, editedOrder);
    }

    /**
     * Removes an Order from an Order file
     * @param orderDate Date of Order
     * @param orderToRemove Order to be removed
     * @throws PersistenceException if unable to remove Order from file
     */
    @Override
    public void removeOrder(LocalDate orderDate, Order orderToRemove)
            throws PersistenceException {
        ORDER_DAO.removeOrder(orderDate, orderToRemove);
    }

    /**
     * Exports data for all Orders from all Order files to a single
     * BackUp (DataExport) file.
     * @throws PersistenceException if unable to export Order data to file
     */
    @Override
    public void exportAllOrders() throws PersistenceException {
        ORDER_DAO.exportAllData();
    }

    /**
     * Gets a list of all 2-character State abbreviations
     * @return List of State abbreviations
     * @throws PersistenceException if unable to retrieve list from file
     */
    @Override
    public List<String> getStateAbbrList() throws PersistenceException {
        return STATE_DAO.getStateAbbrList();
    }

    /**
     * Gets a list of all Product objects
     * @return List of Products
     * @throws PersistenceException if unable to retrieve list from file
     */
    @Override
    public List<Product> getProductList() throws PersistenceException {
        return PRODUCT_DAO.getAllProducts();
    }

    /**
     * Gets a list of Strings for all productTypes
     * @return List of product types
     * @throws PersistenceException if unable to retrieve list from file
     */
    @Override
    public List<String> getProductTypeList() throws PersistenceException {
        return PRODUCT_DAO.getProductTypeList();
    }
}

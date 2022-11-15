package FlooringMastery.dao;

import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The {@code OrderDaoImpl} class is responsible for interacting
 * with Order objects in the Flooring Order App
 */
public class OrderDaoImpl implements OrderDao {
    // Declare variable for FileDao object
    private final FileDao FILE_DAO;

    /**
     * No-args constructor for OrderDaoImpl creates a new instance
     * of the FileDaoImpl.
     */
    public OrderDaoImpl() {
        FILE_DAO = new FileDaoImpl();
    }

    /**
     * Retrieves Order information from an order file based on
     * the parameters that the method receives.
     * @param orderNumber number of Order to retrieve
     * @param orderDate date of Order
     * @return Order object with data from file
     * @throws PersistenceException if unable to find Order based
     * on the date and order number
     */
    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException {
        // Read order file for date entered and stored data in List
        List<Order> orderList = FILE_DAO.readOrderFile(orderDate);

        // Declare and initialize HashMap to map Orders from file
        Map<Integer, Order> orderMap = new HashMap<>();

        // Iterate through orderList putting each order into the map
        // with key = orderNumber and value = Order object
        for (Order order : orderList)
            orderMap.put(order.getOrderNumber(), order);

        // Get and return Order object from map based on order number
        return orderMap.get(orderNumber);
    }

    /**
     * Get all orders for the date passed to this method.
     * @param dateEntered date to retrieve orders for
     * @return list of Order objects
     * @throws PersistenceException if unable to retrieve data for the
     * date entered
     */
    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException {
        // Get list of orders for dateEntered from FILE_DAO and return it
        return FILE_DAO.readOrderFile(dateEntered);
    }

    /**
     * Creates a new Order object based on the parameters passed, then
     * uses those parameters to add information to the remaining values.
     * @param newCustomerName Customer name for new order
     * @param newOrderState State abbr for new order
     * @param productType Product type for new order
     * @param newOrderArea Area for new order
     * @return new Order object
     * @throws PersistenceException if unable to create new order
     */
    @Override
    public Order createNewOrder(String newCustomerName, String newOrderState,
                                String productType, BigDecimal newOrderArea)
            throws PersistenceException {
        // Declare and initialize new State object based on state abbr
        State state = getStateInfo(newOrderState);
        // Declare and initialize new Product object based on productType
        Product product = getProductInfo(productType);

        // Calculate necessary values
        BigDecimal materialCost =
                calculateMaterialCost(product.getCostPerSquareFoot(),newOrderArea);
        BigDecimal laborCost =
                calculateLaborCost(product.getLaborCostPerSquareFoot(), newOrderArea);
        BigDecimal tax = calculateTax(state.getTaxRate(), materialCost, laborCost);
        BigDecimal total = calculateTotal(tax, materialCost, laborCost);

        // Create newOrder object
        Order newOrder = new Order(newCustomerName, newOrderState,
                productType, newOrderArea);

        // Set remain Order values
        newOrder.setOrderNumber(generateNewOrderNum());
        newOrder.setTaxRate(state.getTaxRate());
        newOrder.setCostPerSquareFoot(product.getCostPerSquareFoot());
        newOrder.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        newOrder.setMaterialCost(materialCost);
        newOrder.setLaborCost(laborCost);
        newOrder.setTax(tax);
        newOrder.setTotal(total);

        // Return newOrder object
        return newOrder;
    }

    /**
     * Adds a new Order object to the appropriate Order File.
     * @param newOrderDate date of Order
     * @param newOrder Order object
     * @throws PersistenceException if unable to write Order to File
     */
    @Override
    public void addNewOrderToFile(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException {
        // Pass Order date and object to FILE_DAO to store in file
        FILE_DAO.writeNewOrder(newOrderDate, newOrder);
    }

    /**
     * Edits an Order object by creating a new Order object based on the
     * parameters passed, then uses the parameters to add information to
     * the remaining values.
     * @param order Order to be edited
     * @param newName Customer name for edited order
     * @param newStateAbbr State abbreviation for edited order
     * @param newProductType Product type for edited order
     * @param newArea Area for edited order
     * @return new Order object with the edit values
     * @throws PersistenceException if unable to edit Order
     */
    @Override
    public Order createEditedOrder(Order order, String newName,
                                   String newStateAbbr, String newProductType,
                                   BigDecimal newArea) throws PersistenceException {
        // Declare and initialize new State object based on state abbr
        State state = getStateInfo(newStateAbbr);
        // Declare and initialize new Product object based on productType
        Product product = getProductInfo(newProductType);

        // Calculate necessary values
        BigDecimal materialCost = calculateMaterialCost(product.getCostPerSquareFoot(), newArea);
        BigDecimal laborCost = calculateLaborCost(product.getLaborCostPerSquareFoot(), newArea);
        BigDecimal tax = calculateTax(state.getTaxRate(), materialCost, laborCost);
        BigDecimal total = calculateTotal(tax, materialCost, laborCost);

        // Create editedOrder object
        Order editedOrder = new Order(newName, newStateAbbr,
                newProductType, newArea);

        // Set remain Order values
        editedOrder.setOrderNumber(order.getOrderNumber());
        editedOrder.setTaxRate(state.getTaxRate());
        editedOrder.setCostPerSquareFoot(product.getCostPerSquareFoot());
        editedOrder.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        editedOrder.setMaterialCost(materialCost);
        editedOrder.setLaborCost(laborCost);
        editedOrder.setTax(tax);
        editedOrder.setTotal(total);

        // Return editedOrder object
        return editedOrder;
    }

    /**
     * Replaces an Order object with an edited Order object in the
     * appropriate Order File.
     * @param orderDate date of Order
     * @param orderToEdit Original Order object
     * @param editedOrder Edited Order object
     * @throws PersistenceException if unable to write Order to File
     */
    @Override
    public void writeEditOrder(LocalDate orderDate, Order orderToEdit, Order editedOrder)
            throws PersistenceException {
        // Pass Order date, original Order object, and edited Order object to
        // FILE_DAO to store in file
        FILE_DAO.writeEditOrder(orderDate, orderToEdit, editedOrder);
    }

    /**
     * Removed an Order object from the appropriate Order File.
     * @param orderDate date of Order
     * @param orderToRemove Order object to remove
     * @throws PersistenceException if unable to remove Order from File
     */
    @Override
    public void removeOrder(LocalDate orderDate, Order orderToRemove)
            throws PersistenceException {
        // Pass the Order date and object to the FILE_DAO to remove
        // it from the Order File
        FILE_DAO.removeOrderFromFile(orderDate, orderToRemove);
    }

    /**
     * Exports data from all Order Files into a separate DataExport file
     * for backing up information
     * @param exportFile name of backup file
     * @throws PersistenceException if unable to export data
     */
    @Override
    public void exportAllData(String exportFile) throws PersistenceException {
        // Pass the backup file name to the FILE_DAO to export all Order data
        FILE_DAO.exportAllData(exportFile);
    }

    /**
     * Fetches and returns a State object based on the stateAbbr passed
     * @param stateAbbr State abbreviation
     * @return State object
     * @throws PersistenceException if unable to retrieve State data
     */
    private State getStateInfo(String stateAbbr) throws PersistenceException {
        // List of all State objects
        List<State> stateList = FILE_DAO.readTaxFile("Data/Taxes.txt");

        // Return State object that corresponds to the state abbreviation passed
        return stateList.stream().filter(s -> s.getStateAbbr().equals(stateAbbr))
                .findFirst().orElse(null);
    }

    /**
     * Fetches and returns a Product object based on the productType passed
     * @param productType Product type (name of product)
     * @return Product object
     * @throws PersistenceException if unable to retrieve Product data
     */
    private Product getProductInfo(String productType) throws PersistenceException {
        // List of all Product objects
        List<Product> productList = FILE_DAO.readProductFile("Data/Products.txt");

        // Return Product object that corresponds to the product type passed
        return productList.stream().filter(p -> p.getProductType().equals(productType))
                .findFirst().orElse(null);
    }

    /**
     * Calculates material cost based on cost per square foot and area
     * @param costPerSqFt cost per square foot for product type
     * @param area area of product needed (sq ft)
     * @return total material cost
     */
    private BigDecimal calculateMaterialCost(BigDecimal costPerSqFt, BigDecimal area) {
        // materialCost = costPerSqFt * area
        return costPerSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates labor cost based on labor cost per square foot and area
     * @param laborCostPerSqFt labor cost per square foot for product type
     * @param area area of product needed (sq ft)
     * @return total labor cost
     */
    private BigDecimal calculateLaborCost(BigDecimal laborCostPerSqFt, BigDecimal area) {
        // laborCost = laborCostPerSqFt * area
        return laborCostPerSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates tax based on State taxRate, materialCost, and laborCost. Assumes that
     * both material and labor are taxed.
     * @param taxRate tax rate for State of Order
     * @param materialCost total material cost
     * @param laborCost total labor cost
     * @return total tax amount
     */
    private BigDecimal calculateTax(BigDecimal taxRate, BigDecimal materialCost,
                                    BigDecimal laborCost) {
        // tax = (taxRate * 0.01) * (materialCost + laborCost)
        return taxRate.multiply(BigDecimal.valueOf(0.01))
                .multiply(materialCost.add(laborCost))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates overall total of order
     * @param tax total tax amount
     * @param materialCost total material cost
     * @param laborCost total labor cost
     * @return Order total
     */
    private BigDecimal calculateTotal(BigDecimal tax, BigDecimal materialCost,
                                      BigDecimal laborCost) {
        // total = tax + materialCost + laborCost
        return tax.add(materialCost).add(laborCost);
    }

    /**
     * Private method fetches the latest order number assigned, then
     * calculates and returns a new order number to assign to a new order
     * @return int newOrderNum
     * @throws PersistenceException if error occurs reading files
     */
    private int generateNewOrderNum() throws PersistenceException {
        // Declare variables
        int newOrderNum; // To be generated
        Order lastOrder; // To store order number of last order
        List<Order> orderList; // To store list of orders in the latest Order file
        String dateString; // To store date portion of Order file name

        // Formatter to match date format in Order file names
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");

        // Create File object for directory
        File directoryPath = new File("Orders");

        try {
            // Declare and initialize list with names of all order files
            List<String> orderFileNames =
                    List.of(Objects.requireNonNull(directoryPath.list()));

            // Latest Order file will have most recently assigned Order number
            String latestOrderFile = orderFileNames.get(orderFileNames.size() - 1);

            // Set dateString to date of Order file
            dateString = latestOrderFile.substring(7, 15);

            // Create LocalDate object from dateString
            LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);

            // Read Order file, store in orderList
            orderList = FILE_DAO.readOrderFile(localDate);

            // Last Order in file will have the last Order number assigned
            lastOrder = orderList.get(orderList.size() - 1);

            // Add 1 to last Order number assigned, store as newOrderNum
            newOrderNum = lastOrder.getOrderNumber() + 1;

            return newOrderNum;
        } catch (Exception e) {
            // Throw exception if unable to read order files/generate new order number
            throw new PersistenceException("Unable to generate new order number.");
        }
    }
}

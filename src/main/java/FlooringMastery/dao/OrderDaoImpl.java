package FlooringMastery.dao;

import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderDaoImpl implements OrderDao {
    private final String ORDER_FILE;
    private final FileDao FILE_DAO;

    public OrderDaoImpl() {
        ORDER_FILE = "ordersTest.txt";
        FILE_DAO = new FileDaoImpl();
    }

    public OrderDaoImpl(String orderFileName) {
        this.ORDER_FILE = orderFileName;
        FILE_DAO = new FileDaoImpl();
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException {
        List<Order> orderList = FILE_DAO.readOrderFile(orderDate);
        Map<Integer, Order> orderMap = new HashMap<>();
        for (Order order : orderList)
            orderMap.put(order.getOrderNumber(), order);
        return orderMap.get(orderNumber);
    }

    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException {
        return FILE_DAO.readOrderFile(dateEntered);
    }

    @Override
    public Order createNewOrder(String newCustomerName, String newOrderState,
                                String productType, BigDecimal newOrderArea)
            throws PersistenceException {
        State state = getStateInfo(newOrderState);
        String stateAbbr = state.getStateAbbr();
        BigDecimal taxRate = state.getTaxRate();

        Product product = getProductInfo(productType);
        BigDecimal costPerSqFt = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSqFt = product.getLaborCostPerSquareFoot();

        BigDecimal materialCost = calculateMaterialCost(costPerSqFt, newOrderArea);
        BigDecimal laborCost = calculateLaborCost(laborCostPerSqFt, newOrderArea);
        BigDecimal tax = calculateTax(taxRate, materialCost, laborCost);
        BigDecimal total = calculateTotal(tax, materialCost, laborCost);

        Order newOrder = new Order(newCustomerName, stateAbbr, productType, newOrderArea);
        newOrder.setOrderNumber(generateNewOrderNum());
        newOrder.setTaxRate(taxRate);
        newOrder.setCostPerSquareFoot(costPerSqFt);
        newOrder.setLaborCostPerSquareFoot(laborCostPerSqFt);
        newOrder.setMaterialCost(materialCost);
        newOrder.setLaborCost(laborCost);
        newOrder.setTax(tax);
        newOrder.setTotal(total);

        return newOrder;
    }

    @Override
    public void addNewOrderToFile(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException {
        FILE_DAO.writeNewOrder(newOrderDate, newOrder);
    }

    @Override
    public Order createEditedOrder(Order order, String newName,
                                   String newState, String newProductType,
                                   BigDecimal newArea) throws PersistenceException {
        State state = getStateInfo(newState);
        String stateAbbr = state.getStateAbbr();
        BigDecimal taxRate = state.getTaxRate();

        Product product = getProductInfo(newProductType);
        BigDecimal costPerSqFt = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSqFt = product.getLaborCostPerSquareFoot();

        BigDecimal materialCost = calculateMaterialCost(costPerSqFt, newArea);
        BigDecimal laborCost = calculateLaborCost(laborCostPerSqFt, newArea);
        BigDecimal tax = calculateTax(taxRate, materialCost, laborCost);
        BigDecimal total = calculateTotal(tax, materialCost, laborCost);

        Order editedOrder = new Order(newName, stateAbbr, newProductType, newArea);
        editedOrder.setOrderNumber(order.getOrderNumber());
        editedOrder.setTaxRate(taxRate);
        editedOrder.setCostPerSquareFoot(costPerSqFt);
        editedOrder.setLaborCostPerSquareFoot(laborCostPerSqFt);
        editedOrder.setMaterialCost(materialCost);
        editedOrder.setLaborCost(laborCost);
        editedOrder.setTax(tax);
        editedOrder.setTotal(total);

        return editedOrder;
    }

    @Override
    public void editOrder(LocalDate orderDate, Order orderToEdit, Order editedOrder)
            throws PersistenceException {
        FILE_DAO.writeEditOrder(orderToEdit,editedOrder, orderDate);
    }

    @Override
    public void removeOrder(LocalDate orderDate, Order order)
            throws PersistenceException {

    }

    @Override
    public void exportAllData(String exportFile) throws PersistenceException {
        FILE_DAO.exportAllData(exportFile);
    }

    private State getStateInfo(String stateName) throws PersistenceException {
        List<State> stateList = FILE_DAO.readTaxFile("Data/Taxes.txt");
        return stateList.stream().filter(s -> s.getStateName().equals(stateName))
                .findFirst().orElse(null);
    }

    private Product getProductInfo(String productType) throws PersistenceException {
        List<Product> productList = FILE_DAO.readProductFile("Data/Products.txt");
        return productList.stream().filter(p -> p.getProductType().equals(productType))
                .findFirst().orElse(null);
    }

    private BigDecimal calculateMaterialCost(BigDecimal costPerSqFt, BigDecimal area) {
        return costPerSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateLaborCost(BigDecimal laborCostPerSqFt, BigDecimal area) {
        return laborCostPerSqFt.multiply(area).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTax(BigDecimal taxRate, BigDecimal materialCost,
                                    BigDecimal laborCost) {
        return taxRate.multiply(BigDecimal.valueOf(0.01))
                .multiply(materialCost.add(laborCost))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotal(BigDecimal tax, BigDecimal materialCost,
                                      BigDecimal laborCost) {
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
        Order lastOrder; // To store number of last order
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
            throw new PersistenceException("Unable to generate new order number.");
        }
    }
}

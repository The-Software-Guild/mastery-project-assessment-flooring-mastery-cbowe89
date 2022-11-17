package FlooringMastery.dao;

import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code DaoImplTest} class is responsible for testing
 * the OrderDaoImpl, ProductDaoImpl, and StateDaoImpl classes.
 */
class DaoImplTest {
    // Declare Dao object
    public static OrderDao testOrderDao;
    public static ProductDao testProductDao;
    public static StateDao testStateDao;

    /**
     * No-args constructor for DaoImplTest class
     */
    public DaoImplTest() {
    }

    @BeforeEach
    void setUp() throws IOException, PersistenceException {
        String testOrderFile = "TestClassFiles/Orders_01012050.txt";

        // Blank the test file and write the header line
        PrintWriter out = new PrintWriter(new FileWriter(testOrderFile, false));
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType," +
                "Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost," +
                "LaborCost,Tax,Total");

        // Initialize Dao objects
        testOrderDao = new OrderDaoImpl("TestClassFiles");
        testProductDao = new ProductDaoImpl();
        testStateDao = new StateDaoImpl();

        // Create Order, add to testOrderDao
        Order testOrder = testOrderDao.createNewOrder("John Doe",
                "DC", "Wood", BigDecimal.valueOf(750));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);
        testOrderDao.addNewOrderToFile(testOrderDate,testOrder);

        // Flush and close the PrintWriter
        out.flush();
        out.close();
    }

    /**
     * Tests createNewOrder, addNewOrderToFile, getAllOrders, and all
     * private methods from the OrderDaoImpl class.
     * @throws PersistenceException if unable to write order to file
     */
    @Test
    void testCreateAndAddNewOrder() throws PersistenceException {
        Order newOrder = testOrderDao.createNewOrder("John Smith",
                "FL", "Tile", BigDecimal.valueOf(1000));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);
        testOrderDao.addNewOrderToFile(testOrderDate, newOrder);

        List<Order> testOrderList = testOrderDao.getAllOrders(testOrderDate);
        int numOfTestOrders = testOrderList.size();

        assertNotNull(newOrder, "New Order should not be null");
        assertEquals(2, numOfTestOrders, "There should be 2 orders" +
                " in the test order file");
        assertEquals(2, newOrder.getOrderNumber(), "Order number should" +
                " be 2.");
        assertEquals("John Smith", newOrder.getCustomerName(),
                "Customer name should be John Smith");
        assertEquals("FL", newOrder.getState(), "State should be FL.");
        assertEquals(BigDecimal.valueOf(6.000).setScale(3, RoundingMode.HALF_UP),
                newOrder.getTaxRate(), "Tax rate should be 6.000%.");
        assertEquals("Tile", newOrder.getProductType(),
                "Product type should be Tile.");
        assertEquals(BigDecimal.valueOf(1000), newOrder.getArea(),
                "Order area should be 1000 sq ft.");
        assertEquals(BigDecimal.valueOf(3.50).setScale(2, RoundingMode.HALF_UP),
                newOrder.getCostPerSquareFoot(), "Cost per square foot should be 3.50.");
        assertEquals(BigDecimal.valueOf(4.15).setScale(2, RoundingMode.HALF_UP),
                newOrder.getLaborCostPerSquareFoot(), "Labor cost per square foot should be 4.15.");
        assertEquals(BigDecimal.valueOf(3500).setScale(2, RoundingMode.HALF_UP),
                newOrder.getMaterialCost(), "Material cost should be $3500.");
        assertEquals(BigDecimal.valueOf(4150).setScale(2, RoundingMode.HALF_UP),
                newOrder.getLaborCost(), "Labor cost should be $4150");
        assertEquals(BigDecimal.valueOf(459).setScale(2, RoundingMode.HALF_UP),
                newOrder.getTax(), "Tax should be $459.");
        assertEquals(BigDecimal.valueOf(8109).setScale(2, RoundingMode.HALF_UP),
                newOrder.getTotal(), "Total should be $8109.");
    }

    /**
     * Tests createEditedOrder, writeEditOrder, getAllOrders, and all
     * private methods from the OrderDaoImpl class.
     * @throws PersistenceException if unable to write order to file
     */
    @Test
    void testEditAndWriteEditedOrder() throws PersistenceException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);
        Order order = testOrderDao.getOrder(1, testOrderDate);
        Order editedOrder = testOrderDao.createEditedOrder(order, "Jim Bob", "TX",
                "Laminate", BigDecimal.valueOf(1000.5));
        testOrderDao.writeEditOrder(testOrderDate, order, editedOrder);

        List<Order> testOrderList = testOrderDao.getAllOrders(testOrderDate);
        int numOfTestOrders = testOrderList.size();

        assertEquals(1, numOfTestOrders, "There should be 1 order in the file");
        assertEquals(order.getOrderNumber(), editedOrder.getOrderNumber(),
                "Order number should be 1");
        assertEquals("Jim Bob", editedOrder.getCustomerName(),
                "Customer name should be Jim Bob");
        assertEquals("TX", editedOrder.getState(), "State should be TX.");
        assertEquals(BigDecimal.valueOf(6.250).setScale(3, RoundingMode.HALF_UP),
                editedOrder.getTaxRate(), "Tax rate should be 6.250%.");
        assertEquals("Laminate", editedOrder.getProductType(),
                "Product type should be Laminate.");
        assertEquals(BigDecimal.valueOf(1000.5), editedOrder.getArea(),
                "Order area should be 1000.5 sq ft.");
        assertEquals(BigDecimal.valueOf(1.75).setScale(2, RoundingMode.HALF_UP),
                editedOrder.getCostPerSquareFoot(), "Cost per square foot should be 1.75.");
        assertEquals(BigDecimal.valueOf(2.10).setScale(2, RoundingMode.HALF_UP),
                editedOrder.getLaborCostPerSquareFoot(), "Labor cost per square foot should be 2.10.");
        assertEquals(BigDecimal.valueOf(1750.88).setScale(2, RoundingMode.HALF_UP),
                editedOrder.getMaterialCost(), "Material cost should be $1750.88.");
        assertEquals(BigDecimal.valueOf(2101.05).setScale(2, RoundingMode.HALF_UP),
                editedOrder.getLaborCost(), "Labor cost should be $2101.05");
        assertEquals(BigDecimal.valueOf(240.75).setScale(2, RoundingMode.HALF_UP),
                editedOrder.getTax(), "Tax should be $240.75.");
        assertEquals(BigDecimal.valueOf(4092.68).setScale(2, RoundingMode.HALF_UP),
                editedOrder.getTotal(), "Total should be $4092.68.");
    }

    /**
     * Tests getOrder, removeOrder, and getAllOrders from the OrderDaoImpl class.
     * @throws PersistenceException if unable to write order to file
     */
    @Test
    void testRemoveOrder() throws PersistenceException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);
        Order order = testOrderDao.getOrder(1, testOrderDate);
        testOrderDao.removeOrder(testOrderDate, order);

        int numOfRemainingOrders = testOrderDao.getAllOrders(testOrderDate).size();

        assertEquals(0, numOfRemainingOrders, "There should be no orders left");
    }

    /**
     * Tests getAllProducts and getProductTypeList from the ProductDaoImpl class.
     */
    @Test
    void testGetProducts() throws PersistenceException {
        List<Product> productList = testProductDao.getAllProducts();
        List<String> productTypeList = testProductDao.getProductTypeList();

        assertNotNull(productList, "Product list should not be null.");
        assertNotNull(productTypeList, "Product type list should not be null.");
        assertEquals(productList.size(), productTypeList.size(),
                "The product list and product type list should be equal in size.");
        assertEquals("Carpet", productList.get(0).getProductType(),
                "The first product type should be Carpet");
        assertEquals("Carpet", productTypeList.get(0),
                "The first product type should be Carpet");
    }

    /**
     * Tests getStateInfoList and getStateAbbrList from the StateDaoImpl class.
     */
    @Test
    void testGetStates() throws PersistenceException {
        List<State> stateList = testStateDao.getStateInfoList();
        List<String> stateAbbrs = testStateDao.getStateAbbrList();

        assertNotNull(stateList, "State list should not be null.");
        assertNotNull(stateAbbrs, "State list should not be null.");
        assertEquals(stateList.size(), stateAbbrs.size(),
                "State list and state abbreviation list should be equal in size.");
        assertEquals("TX", stateAbbrs.get(43), "The 44th state abbr " +
                "(43rd index) should be TX.");
    }
}

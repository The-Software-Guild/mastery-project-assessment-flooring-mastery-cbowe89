package FlooringMastery.dao;

import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        newOrder.setOrderNumber(FILE_DAO.generateNewOrderNum());
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
}

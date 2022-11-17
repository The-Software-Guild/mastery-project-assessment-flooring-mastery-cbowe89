package FlooringMastery.service;

import FlooringMastery.dao.OrderDao;
import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DaoStubImpl implements OrderDao {
    public Order onlyOrder;
    public LocalDate testOrderDate;

    public DaoStubImpl() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);

        onlyOrder = new Order(1, "Johnny B. Goode", "TN",
                BigDecimal.valueOf(7.000).setScale(2, RoundingMode.HALF_UP),
                "Wood", BigDecimal.valueOf(500),
                BigDecimal.valueOf(5.15).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(4.75).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(2575).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(2375).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(346.5).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(5296.5).setScale(2, RoundingMode.HALF_UP));
    }

    public DaoStubImpl(Order testOrder) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);

        this.onlyOrder = testOrder;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException {
        if (orderNumber == onlyOrder.getOrderNumber()
                && orderDate.equals(testOrderDate))
            return onlyOrder;
        else
            return null;
    }

    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order createNewOrder(String newCustomerName, String newOrderState,
                                String productType, BigDecimal newOrderArea)
            throws PersistenceException {
        if (newCustomerName.equals(onlyOrder.getCustomerName())
                && newOrderState.equals(onlyOrder.getState())
                && productType.equals(onlyOrder.getProductType())
                && newOrderArea.equals(onlyOrder.getArea()))
            return onlyOrder;
        else
            return null;
    }

    @Override
    public void addNewOrderToFile(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException {
    }

    @Override
    public Order createEditedOrder(Order order, String newName, String newState,
                                   String newProductType, BigDecimal newArea)
            throws PersistenceException {
        if (order.equals(onlyOrder)) {
            Order editedOrder = new Order(order.getOrderNumber(), newName,
                    newState, newProductType, newArea);
            editedOrder.setTaxRate(BigDecimal.valueOf(6.250)
                    .setScale(3, RoundingMode.HALF_UP));
            editedOrder.setCostPerSquareFoot(BigDecimal.valueOf(3.50)
                    .setScale(2, RoundingMode.HALF_UP));
            editedOrder.setLaborCostPerSquareFoot(BigDecimal.valueOf(4.15)
                    .setScale(2, RoundingMode.HALF_UP));
            editedOrder.setMaterialCost(BigDecimal.valueOf(1225)
                    .setScale(2, RoundingMode.HALF_UP));
            editedOrder.setLaborCost(BigDecimal.valueOf(1452.5)
                    .setScale(2, RoundingMode.HALF_UP));
            editedOrder.setTax(BigDecimal.valueOf(167.34)
                    .setScale(2, RoundingMode.HALF_UP));
            editedOrder.setTotal(BigDecimal.valueOf(2844.84)
                    .setScale(2, RoundingMode.HALF_UP));
            return editedOrder;
        }
        else
            return null;
    }

    @Override
    public void writeEditOrder(LocalDate orderDate, Order orderToEdit,
                               Order editedOrder) throws PersistenceException {
    }

    @Override
    public void removeOrder(LocalDate orderDate, Order orderToRemove)
            throws PersistenceException {
    }

    @Override
    public void exportAllData() throws PersistenceException {
    }
}

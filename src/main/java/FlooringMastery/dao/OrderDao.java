package FlooringMastery.dao;

import FlooringMastery.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException;

    List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException;

    Order createNewOrder(String newCustomerName, String newOrderState,
                         String productType, BigDecimal newOrderArea)
            throws PersistenceException;

    void addNewOrderToFile(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException;

    void exportAllData(String exportFile) throws PersistenceException;
}

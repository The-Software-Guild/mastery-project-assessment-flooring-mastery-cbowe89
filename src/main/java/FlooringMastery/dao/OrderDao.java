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

    Order createEditedOrder(Order order, String newName,
                            String newState, String newProductType,
                            BigDecimal newArea) throws PersistenceException;

    void writeEditOrder(LocalDate orderDate, Order orderToEdit, Order editedOrder)
            throws PersistenceException;

    void removeOrder(LocalDate orderDate, Order orderToRemove)
            throws PersistenceException;

    void exportAllData() throws PersistenceException;
}

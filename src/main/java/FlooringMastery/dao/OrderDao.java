package FlooringMastery.dao;

import FlooringMastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException;

    List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException;

    void exportAllData(String exportFile) throws PersistenceException;
}

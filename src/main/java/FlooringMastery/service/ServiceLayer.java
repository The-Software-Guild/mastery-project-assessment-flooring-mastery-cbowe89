package FlooringMastery.service;

import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {
    Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException;

    List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException, OrderFileNotFoundException;

    void exportAllOrders() throws PersistenceException;
}

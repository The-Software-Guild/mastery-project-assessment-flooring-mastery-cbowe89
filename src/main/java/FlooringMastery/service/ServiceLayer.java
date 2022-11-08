package FlooringMastery.service;

import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {
    Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException;

    List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException, OrderFileNotFoundException;

    Order createNewOrder(String newCustomerName, String newOrderState,
                         String productType, BigDecimal newOrderArea)
            throws PersistenceException;

    void addNewOrder(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException;

    void exportAllOrders() throws PersistenceException;

    List<State> getStateInfoList() throws PersistenceException;

    List<String> getStateNameList() throws PersistenceException;

    List<String> getStateAbbrList() throws PersistenceException;

    List<Product> getProductList() throws PersistenceException;

    List<String> getProductTypeList() throws PersistenceException;
}

package FlooringMastery.service;

import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;
import FlooringMastery.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {
    Order getOrder(int orderNumber, LocalDate orderDate)
            throws OrderNotFoundException, PersistenceException;

    List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException;

    Order createNewOrder(String newCustomerName, String newOrderState,
                         String productType, BigDecimal newOrderArea)
            throws OrderBuildException;

    void addNewOrder(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException;

    Order createEditedOrder(Order orderToEdit, String newName,
                            String newState, String newProductType,
                            BigDecimal newArea) throws OrderBuildException;

    void writeEditOrder(LocalDate orderDate, Order orderToEdit, Order editedOrder)
            throws PersistenceException;

    void removeOrder(LocalDate orderDate, Order orderToRemove)
            throws PersistenceException;

    void exportAllOrders() throws PersistenceException;

    List<String> getStateAbbrList() throws PersistenceException;

    List<Product> getProductList() throws PersistenceException;

    List<String> getProductTypeList() throws PersistenceException;
}

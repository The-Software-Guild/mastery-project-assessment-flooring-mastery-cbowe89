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
            throws PersistenceException, OrderNotFoundException;

    List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException, OrderFileNotFoundException;

    Order createNewOrder(String newCustomerName, String newOrderState,
                         String productType, BigDecimal newOrderArea)
            throws PersistenceException, TaxFileNotFoundException;

    void addNewOrder(LocalDate newOrderDate, Order newOrder)
            throws PersistenceException;

    Order createEditedOrder(Order orderToEdit, String newName,
                            String newState, String newProductType,
                            BigDecimal newArea) throws PersistenceException;

    void editOrder(LocalDate orderDate, Order orderToEdit, Order editedOrder)
            throws PersistenceException;

    void removeOrder(LocalDate orderDate, Order orderToRemove) throws PersistenceException;

    void exportAllOrders() throws PersistenceException;

    List<State> getStateInfoList() throws PersistenceException;

    List<String> getStateNameList() throws PersistenceException, TaxFileNotFoundException;

    List<String> getStateAbbrList() throws PersistenceException, TaxFileNotFoundException;

    List<Product> getProductList() throws PersistenceException, ProductFileNotFoundException;

    List<String> getProductTypeList() throws PersistenceException, ProductFileNotFoundException;
}

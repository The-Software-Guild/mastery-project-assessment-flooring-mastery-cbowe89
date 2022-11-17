package FlooringMastery.dao;

import FlooringMastery.model.Product;
import FlooringMastery.model.Order;
import FlooringMastery.model.State;

import java.time.LocalDate;
import java.util.List;

public interface FileDao {
    Product unmarshallProduct(String line);

    List<Product> readProductFile(String fileName) throws
            PersistenceException;

    State unmarshallTaxes(String line) throws PersistenceException;

    List<State> readTaxFile(String fileName) throws
            PersistenceException;

    Order unmarshallOrder(String line);

    List<Order> readOrderFile(LocalDate date) throws
            PersistenceException;

    String marshallOrder(Order order);

    void writeNewOrder(LocalDate date, Order order) throws
            PersistenceException;

    void writeEditOrder(LocalDate date, Order orderToEdit, Order editedOrder)
            throws PersistenceException;

    void removeOrderFromFile(LocalDate date, Order orderToRemove) throws PersistenceException;

    String marshallOrderForExport(Order order, String date);

    void exportAllData(String exportFile)
            throws PersistenceException;

    int generateNewOrderNum() throws PersistenceException;
}

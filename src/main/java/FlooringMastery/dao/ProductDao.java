package FlooringMastery.dao;

import FlooringMastery.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts() throws PersistenceException;

    Product getProduct() throws PersistenceException;
}

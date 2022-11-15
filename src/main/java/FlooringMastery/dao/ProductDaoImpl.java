package FlooringMastery.dao;

import FlooringMastery.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ProductDaoImpl} class is responsible for interacting
 * with Product objects in the Flooring Order App
 */
public class ProductDaoImpl implements ProductDao {
    // Declare variable for FileDao object
    private final FileDao FILE_DAO;

    /**
     * No-args construct for ProductDaoImpl creates a new instance
     * of the FileDaoImpl.
     */
    public ProductDaoImpl() {
        this.FILE_DAO = new FileDaoImpl();
    }

    /**
     * Gets and returns a list of all Product objects
     * @return list of Product objects
     * @throws PersistenceException if unable to read Product file
     */
    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        // Passes name of product file to method in the FILE_DAO
        return FILE_DAO.readProductFile("Data/Products.txt");
    }

    /**
     * Creates and returns a list of all product types
     * @return String list of product types
     * @throws PersistenceException if unable to retrieve data
     */
    @Override
    public List<String> getProductTypeList() throws PersistenceException {
        // Call getAllProducts() to get list of all Products
        List<Product> productList = getAllProducts();

        // Declare and initialize a new ArrayList to store product types
        List<String> productTypeList = new ArrayList<>();

        // Iterate over productList, add productType of each Product to
        // productTypeList
        for (Product p : productList)
            productTypeList.add(p.getProductType());

        // Return productTypeList
        return productTypeList;
    }
}

package FlooringMastery.dao;

import FlooringMastery.model.Product;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private final FileDao FILE_DAO;

    public ProductDaoImpl() {
        this.FILE_DAO = new FileDaoImpl();
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        return FILE_DAO.readProductFile("Data/Products.txt");
    }

    @Override
    public Product getProduct() throws PersistenceException {
        return null;
    }
}

package FlooringMastery.dao;

import FlooringMastery.model.Product;

import java.util.ArrayList;
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
    public List<String> getProductTypeList() throws PersistenceException {
        List<Product> productList = getAllProducts();
        List<String> productTypeList = new ArrayList<>();
        for (Product p : productList)
            productTypeList.add(p.getProductType());
        return productTypeList;
    }
}

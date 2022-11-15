package FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The {@code Product} class is responsible for creating new Product objects
 * based on the values passed to the constructor. It includes getter and
 * setter methods for all Product object values. It overrides the toString,
 * equals, and hashcode methods.
 */
public class Product {
    // Values for Order objects
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;


    /**
     * Constructor takes one parameter (productType) and
     * creates a new Product object
     * @param productType product type
     */
    public Product(String productType) {
        this.productType = productType;
    }

    /**
     * Constructor takes 3 parameters and creates a new Product object
     * @param productType product type
     * @param costPerSquareFoot cost per square foot of Product
     * @param laborCostPerSquareFoot labor cost per square foot of Product
     */
    public Product(String productType, BigDecimal costPerSquareFoot,
                   BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return "Product{" + "productType='" + productType +
                ", costPerSquareFoot=" + costPerSquareFoot +
                ", laborCostPerSquareFoot=" + laborCostPerSquareFoot + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productType, product.productType)
                && Objects.equals(costPerSquareFoot, product.costPerSquareFoot)
                && Objects.equals(laborCostPerSquareFoot, product.laborCostPerSquareFoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productType, costPerSquareFoot, laborCostPerSquareFoot);
    }
}

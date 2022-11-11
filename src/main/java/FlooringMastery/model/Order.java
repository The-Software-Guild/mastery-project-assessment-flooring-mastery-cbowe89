package FlooringMastery.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
    int orderNumber;
    String customerName;
    String state;
    BigDecimal taxRate;
    String productType;
    BigDecimal area;
    BigDecimal costPerSquareFoot;
    BigDecimal laborCostPerSquareFoot;
    BigDecimal materialCost;
    BigDecimal laborCost;
    BigDecimal tax;
    BigDecimal total;

    public Order(int newOrderNumber) {
        this.orderNumber = newOrderNumber;
    }

    public Order(String newCustomerName, String newOrderState,
                 String productType, BigDecimal newOrderArea) {
        this.customerName = newCustomerName;
        this.state = newOrderState;
        this.productType = productType;
        this.area = newOrderArea;
    }

    public Order(int orderNumber, String customerName, String state,
                 String productType, BigDecimal area) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
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

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order #: " + orderNumber + ", CustomerName: " + customerName +
                ", State: " + state + ", TaxRate: " + taxRate +
                ", ProductType: " + productType + ", Area: " + area +
                ", CostPerSqFt: " + costPerSquareFoot +
                ", LaborCostPerSqFt: " + laborCostPerSquareFoot +
                ", MaterialCost: " + materialCost + ", LaborCost: " + laborCost +
                ", Tax: " + tax + ", Total: " + total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber)
                && Objects.equals(customerName, order.customerName)
                && Objects.equals(state, order.state)
                && Objects.equals(taxRate, order.taxRate)
                && Objects.equals(productType, order.productType)
                && Objects.equals(area, order.area)
                && Objects.equals(costPerSquareFoot, order.costPerSquareFoot)
                && Objects.equals(laborCostPerSquareFoot, order.laborCostPerSquareFoot)
                && Objects.equals(materialCost, order.materialCost)
                && Objects.equals(laborCost, order.laborCost)
                && Objects.equals(tax, order.tax)
                && Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, customerName, state, taxRate,
                productType, area, costPerSquareFoot, laborCostPerSquareFoot,
                materialCost, laborCost, tax, total);
    }
}

package FlooringMastery.dao;

import FlooringMastery.model.Order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    private final String ORDER_FILE;
    private final FileDao FILE_DAO;

    public OrderDaoImpl() {
        ORDER_FILE = "ordersTest.txt";
        FILE_DAO = new FileDaoImpl();
    }

    public OrderDaoImpl(String orderFileName) {
        this.ORDER_FILE = orderFileName;
        FILE_DAO = new FileDaoImpl();
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException {
        List<Order> orderList = FILE_DAO.readOrderFile(orderDate);
        Map<Integer, Order> orderMap = new HashMap<>();
        for (Order order : orderList)
            orderMap.put(order.getOrderNumber(), order);
        return orderMap.get(orderNumber);
    }

    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException {
        return FILE_DAO.readOrderFile(dateEntered);
    }

    @Override
    public void exportAllData(String exportFile) throws PersistenceException {
        FILE_DAO.exportAllData(exportFile);
    }
}

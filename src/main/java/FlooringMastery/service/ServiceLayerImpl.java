package FlooringMastery.service;

import FlooringMastery.dao.*;
import FlooringMastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public class ServiceLayerImpl implements ServiceLayer {
    private final OrderDao ORDER_DAO;
    private final AuditDao AUDIT_DAO;
    private final FileDao FILE_DAO;

    public ServiceLayerImpl() {
        ORDER_DAO = new OrderDaoImpl();
        AUDIT_DAO = new AuditDaoImpl();
        FILE_DAO = new FileDaoImpl();
    }

    public ServiceLayerImpl(OrderDao orderDao, AuditDao auditDao,
                            FileDao fileDao) {
        this.ORDER_DAO = orderDao;
        this.AUDIT_DAO = auditDao;
        this.FILE_DAO = fileDao;
    }

    @Override
    public Order getOrder(int orderNumber, LocalDate orderDate)
            throws PersistenceException {
        return ORDER_DAO.getOrder(orderNumber, orderDate);
    }

    @Override
    public List<Order> getAllOrders(LocalDate dateEntered)
            throws PersistenceException, OrderFileNotFoundException {
        List<Order> ordersForDateEntered = ORDER_DAO.getAllOrders(dateEntered);

        if (ordersForDateEntered.isEmpty())
            throw new OrderFileNotFoundException("No orders for date entered.");
        else
            return ordersForDateEntered;
    }

    @Override
    public void exportAllOrders() throws PersistenceException {
        String exportFile = "Backup/DataExport.txt";
        ORDER_DAO.exportAllData(exportFile);
        AUDIT_DAO.writeAuditEntry("All order data exported.");
    }
}

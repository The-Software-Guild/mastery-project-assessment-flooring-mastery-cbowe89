package FlooringMastery.service;

import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code ServiceLayerImplTest} class is responsible for
 * testing the ServiceLayerImpl class.
 */
class ServiceLayerImplTest {
    // Declare ServiceLayer object
    public static ServiceLayer serviceLayer;

    public ServiceLayerImplTest() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        serviceLayer = context.getBean("serviceLayer", ServiceLayer.class);
    }

    /**
     * Tests the createNewOrder and getOrder methods.
     * @throws OrderNotFoundException if order is not found
     * @throws PersistenceException if unable to persist data
     * @throws OrderBuildException if unable to create order
     */
    @Test
    void testCreateAndGetOrder() throws OrderNotFoundException, PersistenceException, OrderBuildException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);

        Order testClone = serviceLayer.createNewOrder("Johnny B. Goode",
                "TN", "Wood", BigDecimal.valueOf(500));

        Order shouldBeJohnny = serviceLayer.getOrder(1, testOrderDate);
        assertNotNull(shouldBeJohnny,
                "Getting order 'shouldBeJohnny' should not be null.");
        assertEquals(testClone,shouldBeJohnny,
                "Order stored in 'shouldBeJohnny' should equal 'testClone'");
        assertEquals(testClone.getOrderNumber(), shouldBeJohnny.getOrderNumber(),
                "Order numbers should be equal.");
    }

    /**
     * Tests the getOrder and createEditedOrder methods.
     * @throws OrderNotFoundException if order is not found
     * @throws PersistenceException if unable to persist data
     * @throws OrderBuildException if unable to create order
     */
    @Test
    void testEditAndGetOrder() throws OrderNotFoundException, PersistenceException, OrderBuildException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate testOrderDate = LocalDate.parse("01012050", dateTimeFormatter);

        Order testClone = serviceLayer.getOrder(1, testOrderDate);
        Order testEdit = serviceLayer.createEditedOrder(testClone, "John Goode",
                "TX", "Tile", BigDecimal.valueOf(350));

        assertNotNull(testClone, "testClone order should not be null");
        assertNotNull(testEdit, "testEdit order should not be null");
        assertEquals(testClone.getOrderNumber(), testEdit.getOrderNumber(),
                "Order numbers should be the same.");
        assertEquals("John Goode", testEdit.getCustomerName(),
                "testEdit name should be John Goode");
        assertNotEquals(testEdit.getState(), testClone.getState(),
                "States should not be the same.");
        assertNotEquals(testEdit.getProductType(), testClone.getProductType(),
                "Product types should not be the same.");
        assertNotEquals(testEdit.getArea(), testClone.getArea(),
                "Areas should not be the same.");
        assertNotEquals(testEdit.getTotal(), testClone.getTotal(),
                "Totals should not be the same.");
    }
}

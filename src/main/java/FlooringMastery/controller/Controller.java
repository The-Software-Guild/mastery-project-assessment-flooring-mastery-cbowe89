package FlooringMastery.controller;

import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;
import FlooringMastery.service.InvalidStateException;
import FlooringMastery.service.OrderFileNotFoundException;
import FlooringMastery.service.ServiceLayer;
import FlooringMastery.service.ServiceLayerImpl;
import FlooringMastery.ui.UserIO;
import FlooringMastery.ui.UserIOConsoleImpl;
import FlooringMastery.ui.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Controller {
    private final View view;
    private final ServiceLayer serviceLayer;

    public Controller() {
        UserIO io = new UserIOConsoleImpl();
        view = new View(io);
        serviceLayer = new ServiceLayerImpl();
    }

    public Controller(View view, ServiceLayer serviceLayer) {
        this.view = view;
        this.serviceLayer = serviceLayer;
    }

    public void run() {
        boolean runApplication = true;
        int mainMenuSelection;

        view.displayTitleBanner();

        try {
            while (runApplication) {
                mainMenuSelection = view.getMainMenuSelection();

                switch (mainMenuSelection) {
                    case 1 -> displayOrders();
                    case 2 -> addOrder();
                    case 3 -> editOrder();
                    case 4 -> removeOrder();
                    case 5 -> exportAllData();
                    case 6 -> {
                        runApplication = false;
                        quit();
                    }
                    default -> view.displayUnknownCommand();
                }
            }
        } catch (PersistenceException | InvalidStateException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void displayOrders() throws PersistenceException {
        view.displayAllBanner();
        LocalDate dateEntered = view.getDateToDisplay();

        try {
            List<Order> ordersForDateEntered = serviceLayer.getAllOrders(dateEntered);
            view.displayOrdersForDate(ordersForDateEntered);
            view.continueMessage();
        } catch (OrderFileNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() throws PersistenceException, InvalidStateException {
        view.displayAddOrderBanner();
        LocalDate newOrderDate = view.getNewOrderDate();
        String newCustomerName = view.getNewCustomerName();
        String newOrderState = view.getNewOrderState();
        String productType = view.getProductType(serviceLayer.getProductList());
        BigDecimal newOrderArea = view.getNewOrderArea();

        Order newOrder = serviceLayer.createNewOrder(newCustomerName,
                newOrderState, productType, newOrderArea);

        int confirmAddOrder = view.confirmPlaceNewOrder(newOrder);

        switch (confirmAddOrder) {
            case 1 -> {
                serviceLayer.addNewOrder(newOrderDate, newOrder);
                view.orderPlacedSuccessMsg();
            }
            case 2 -> view.orderDiscardedMsg();
        }
    }

    private void editOrder() {
    }

    private void removeOrder() {
    }

    private void exportAllData() throws PersistenceException {
        view.displayExportBanner();
        int exportYorN = view.getExportConfirmation();
        switch (exportYorN) {
            case 1 -> {
                serviceLayer.exportAllOrders();
                view.displayExportSuccessMsg();
            }
            case 2 -> view.skipExportMessage();
        }
    }

    private void quit() {
        view.displayExitMessage();
    }
}

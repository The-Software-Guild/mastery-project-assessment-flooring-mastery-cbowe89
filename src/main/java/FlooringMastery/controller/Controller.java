package FlooringMastery.controller;

import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;
import FlooringMastery.service.*;
import FlooringMastery.ui.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Controller {
    private final View view;
    private final ServiceLayer serviceLayer;

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
        } catch (PersistenceException | InvalidStateException |
                 ProductFileNotFoundException | TaxFileNotFoundException |
                 OrderNotFoundException | OrderFileNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void displayOrders() throws PersistenceException {
        try {
            LocalDate dateEntered = view.getDateToDisplay();
            List<Order> ordersForDateEntered = serviceLayer.getAllOrders(dateEntered);
            view.displayOrdersForDate(ordersForDateEntered);
        } catch (OrderFileNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() throws PersistenceException, InvalidStateException,
            ProductFileNotFoundException, TaxFileNotFoundException {
        try {
            view.displayAddOrderBanner();
            LocalDate newOrderDate = view.getNewOrderDate();
            String newCustomerName = view.getNewCustomerName();
            String newOrderState = view.getNewOrderState(serviceLayer.getStateNameList());
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
        } catch (PersistenceException | InvalidStateException |
                 ProductFileNotFoundException | TaxFileNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void editOrder() throws PersistenceException, OrderFileNotFoundException {
        try {
            LocalDate orderDate = view.getDateToEdit();
            int orderNum = view.readOrderNumToEdit();
            Order orderToEdit = serviceLayer.getOrder(orderNum, orderDate);

            String newCustomerName =
                    view.editOrderCustomerName(orderToEdit.getCustomerName());

            String newState = view.editOrderState(orderToEdit.getState(),
                    serviceLayer.getStateNameList());

            String newProductType = view.editOrderProductType(
                    orderToEdit.getProductType(), serviceLayer.getProductList(),
                    serviceLayer.getProductTypeList());

            BigDecimal newOrderArea = view.editOrderArea(orderToEdit.getArea());

            Order editedOrder = new Order(newCustomerName, newState,
                    newProductType, newOrderArea);

            if (orderToEdit.getCustomerName().equals(editedOrder.getCustomerName())
                    && orderToEdit.getState().equals(editedOrder.getState())
                    && orderToEdit.getProductType().equals(editedOrder.getProductType())
                    && orderToEdit.getArea().equals(editedOrder.getArea())) {
                view.displayNoInfoChangedMsg();
                return;
            }

            int confirmEditOrder = view.confirmEditOrder(editedOrder);

            switch (confirmEditOrder) {
                case 1 -> {
                    serviceLayer.editOrder(orderDate, editedOrder);
                    view.editSuccessMsg();
                }
                case 2 -> view.editDiscardedMsg();
            }

        } catch (PersistenceException | OrderNotFoundException |
                 TaxFileNotFoundException | ProductFileNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void removeOrder() throws PersistenceException, OrderNotFoundException {
        try {
            LocalDate orderDate = view.getDateToRemove();
            int orderNum = view.readOrderNumToRemoved();
            Order orderToRemove = serviceLayer.getOrder(orderNum, orderDate);

            int confirmRemove = view.confirmRemoveOrder(orderToRemove);
            switch (confirmRemove) {
                case 1 -> {
                    serviceLayer.removeOrder(orderDate, orderToRemove);
                    view.orderRemovedSuccessMsg();
                }
                case 2 -> view.removeOrderDisregardMsg();
            }
        } catch (PersistenceException | OrderNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exportAllData() throws PersistenceException {
        try {
            view.displayExportBanner();
            int exportYorN = view.getExportConfirmation();
            switch (exportYorN) {
                case 1 -> {
                    serviceLayer.exportAllOrders();
                    view.displayExportSuccessMsg();
                }
                case 2 -> view.skipExportMessage();
            }
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void quit() {
        view.displayExitMessage();
    }
}

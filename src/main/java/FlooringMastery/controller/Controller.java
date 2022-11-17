package FlooringMastery.controller;

import FlooringMastery.dao.PersistenceException;
import FlooringMastery.model.Order;
import FlooringMastery.service.*;
import FlooringMastery.ui.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code Controller} class orchestrates the actions of the other
 * components in the application to accomplish the application's goals.
 * It controls the flow of the application, allows for navigation via
 * the menu, and provides methods to interact with the flooring order
 * system.
 */
public class Controller {
    // Declare View and ServiceLayer objects
    private final View view;
    private final ServiceLayer serviceLayer;

    /**
     * Controller constructor initializes the View and ServiceLayer
     * objects via dependency injection.
     * @param view View object
     * @param serviceLayer ServiceLayer object
     */
    public Controller(View view, ServiceLayer serviceLayer) {
        // Initialize View and ServiceLayer objects
        this.view = view;
        this.serviceLayer = serviceLayer;
    }

    /**
     * The run() method controls the application
     */
    public void run() {
        // Declare and initialize variables
        boolean runApplication = true;
        int mainMenuSelection;

        // Display banner with application title
        view.displayTitleBanner();

        // Run Flooring Order App while runApplication is true
        while (runApplication) {
            // Display menu, update mainMenuSelection from user input
            mainMenuSelection = view.getMainMenuSelection();

            switch (mainMenuSelection) {
                case 1 -> displayOrders(); // Display all order for a date
                case 2 -> addOrder(); // Add a new order
                case 3 -> editOrder(); // Edit an existing order
                case 4 -> removeOrder(); // Remove an existing order
                case 5 -> exportAllData(); // Export all orders to a backup file
                case 6 -> { // Quit Flooring Order App
                    runApplication = false;
                    quit();
                }
                default -> view.displayUnknownCommand(); // Invalid menu selection
            }
        }
    }

    /**
     * Displays all orders for the date entered.
     */
    private void displayOrders() {
        try {
            // Get date to display orders for
            LocalDate dateEntered = view.getDateToDisplay();
            // Get list of orders for date entered
            List<Order> ordersForDateEntered = serviceLayer.getAllOrders(dateEntered);
            // Display all orders from the list
            view.displayOrdersForDate(ordersForDateEntered);
        } catch (PersistenceException e) {
            // No order file located for date entered
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Prompts user for information to create a new order and writes
     * the new order to the appropriate file if the user confirms to do so.
     */
    private void addOrder() {
        try {
            view.displayAddOrderBanner();

            // Prompt user for date, customerName, orderState, productType
            // and area (sq ft) of new order and store the information in
            // the appropriate variables
            LocalDate newOrderDate = view.getNewOrderDate();
            String newCustomerName = view.getNewCustomerName();
            String newOrderState = view.getNewOrderState(serviceLayer.getStateAbbrList());
            String productType = view.getProductType(serviceLayer.getProductList());
            BigDecimal newOrderArea = view.getNewOrderArea();

            // Create new order object with the data provided. Remaining values
            // are calculated.
            Order newOrder = serviceLayer.createNewOrder(newCustomerName,
                    newOrderState, productType, newOrderArea);

            // Display new order and prompt user to confirm if they want to place
            // the order (save the order to the appropriate Order file)
            int confirmAddOrder = view.confirmPlaceNewOrder(newOrder);

            switch (confirmAddOrder) {
                // Save new order
                case 1 -> {
                    // Add new order to Order file
                    serviceLayer.addNewOrder(newOrderDate, newOrder);
                    // Display success message
                    view.orderPlacedSuccessMsg();
                }
                // Discard new order
                case 2 -> view.orderDiscardedMsg(); // Display discard message
            }
        } catch (PersistenceException | OrderBuildException e) {
            // Display message for correct exception
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Prompts user for information to edit an existing order and writes
     * the new order to the appropriate file if the user confirms to do so.
     */
    private void editOrder() {
        try {
            // Prompt user for Order date and Order number
            LocalDate orderDate = view.getDateToEdit();
            int orderNum = view.readOrderNumToEdit();

            // Get order that matches the entered number and date, store
            // in new Order object
            Order orderToEdit = serviceLayer.getOrder(orderNum, orderDate);

            // Get updated customer name for order
            String newCustomerName =
                    view.editOrderCustomerName(orderToEdit.getCustomerName());

            // Get updated state for order
            String newState = view.editOrderState(orderToEdit.getState(),
                    serviceLayer.getStateAbbrList());

            // Get updated productType for order
            String newProductType = view.editOrderProductType(
                    orderToEdit.getProductType(), serviceLayer.getProductList(),
                    serviceLayer.getProductTypeList());

            // Get updated area for order
            BigDecimal newOrderArea = view.editOrderArea(orderToEdit.getArea());

            // Create new Order object based on the edited information
            Order editedOrder = serviceLayer.createEditedOrder(orderToEdit,
                    newCustomerName, newState, newProductType, newOrderArea);

            // If order was not edited, display no information changed message
            // and return from the method
            if (orderToEdit.equals(editedOrder)) {
                view.displayNoInfoChangedMsg();
                return;
            }

            // Prompt user to confirm if they wish to save the edited order
            int confirmEditOrder = view.confirmEditOrder(orderToEdit, editedOrder);

            switch (confirmEditOrder) {
                case 1 -> { // Save edited order
                    serviceLayer.writeEditOrder(orderDate, orderToEdit, editedOrder);
                    view.editSuccessMsg(); // Display editSuccessMsg
                }
                case 2 -> view.editDiscardedMsg(); // Display edit discarded message
            }

        } catch (PersistenceException | OrderNotFoundException |
                 OrderBuildException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Prompts user for information to remove an existing order, displays the
     * order information if it exists, and removes the order from the order file
     * if the user confirms to do so.
     */
    private void removeOrder() {
        try {
            // Prompt user for Order date and Order number
            LocalDate orderDate = view.getDateToRemove();
            int orderNum = view.readOrderNumToRemove();

            // Get order that matches the entered number and date, store
            // in new Order object
            Order orderToRemove = serviceLayer.getOrder(orderNum, orderDate);

            // Prompt user to confirm if they wish to remove the order
            int confirmRemove = view.confirmRemoveOrder(orderToRemove);
            switch (confirmRemove) {
                case 1 -> { // Remove order from file
                    serviceLayer.removeOrder(orderDate, orderToRemove);
                    view.orderRemovedSuccessMsg();
                }
                case 2 -> view.removeOrderDisregardMsg(); // Display disregard msg
            }
        } catch (PersistenceException | OrderNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Prompts user to confirm if they wish to export all order data. If so,
     * all orders are written to the export file with the date of the order
     * appended to the end of the order string.
     */
    private void exportAllData() {
        try {
            view.displayExportBanner();

            // Prompt user to confirm if they wish to export all order data
            int exportYorN = view.getExportConfirmation();

            switch (exportYorN) {
                case 1 -> { // Export all order data
                    serviceLayer.exportAllOrders();
                    view.displayExportSuccessMsg();
                }
                case 2 -> view.skipExportMessage(); // Display skip export message
            }
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Displays an exit message when the user wishes to exit the application.
     */
    private void quit() {
        view.displayExitMessage();
    }
}

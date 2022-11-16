package FlooringMastery.ui;

import FlooringMastery.model.Order;
import FlooringMastery.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code View} class is responsible for all information,
 * banners, etc. displayed to the user throughout the application
 */
public class View {
    // Declare UserIO object
    private final UserIO io;

    /**
     * Constructor for View object
     * @param io UserIO object
     */
    public View(UserIO io) {
        this.io = io;
    }

    /**
     * Displays the application's Title
     */
    public void displayTitleBanner() {
        io.print("\n******************************");
        io.print("***   Flooring Order App   ***");
        io.print("******************************\n");
    }

    /**
     * Prints the main menu and prompts the user to select
     * which action they would like to do
     * @return user selection (int)
     */
    public int getMainMenuSelection() {
        io.print("********** MAIN MENU **********");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("*******************************");

        return io.readInt("Please selection an option.", 1, 6);
    }

    /**
     * Prompts the user to enter a date for which they would like
     * to view the Orders for
     * @return date entered by user
     */
    public LocalDate getDateToDisplay() {
        io.print("\n----- Display All Orders For A Date -----");
        return io.readDate("Enter a date to display orders. (MMDDYYYY)");
    }

    /**
     * Displays the Order objects as strings that were
     * passed to the method in a List
     * @param allOrders Orders from file
     */
    public void displayOrdersForDate(List<Order> allOrders) {
        for (Order order : allOrders) {
            io.print(order.toString());
        }
        continueMessage();
    }

    /**
     * Displays banner for adding a new Order
     */
    public void displayAddOrderBanner() {
        io.print("\n----- Add A New Order -----");
    }

    /**
     * Prompts the user to enter a date in the future for which
     * they would like to add an order to
     * @return date entered by the user
     */
    public LocalDate getNewOrderDate() {
        return io.readDate("Enter a date in the future for the new order.",
                LocalDate.now());
    }

    /**
     * Prompts the user to enter the customer name for a new order
     * @return customer name entered by the user
     */
    public String getNewCustomerName() {
        return io.readString("Enter a Customer Name for the new order.");
    }

    /**
     * Prompts the user to enter the state for a new order
     * @param stateAbbrList List of all state abbreviations
     * @return state abbreviation entered by the user
     */
    public String getNewOrderState(List<String> stateAbbrList) {
        String stateAbbr =  io.readString("Enter a State Abbreviation for " +
                "the new order. (Ex: Enter TX for Texas, FL for Florida").toUpperCase();

        // Continually prompt the user for a state abbreviation until
        // a valid entry is received
        while (!stateAbbrList.contains(stateAbbr)) {
            stateAbbr = io.readString("Invalid entry! Enter a valid " +
                    "State Abbreviation.").toUpperCase();
        }

        return stateAbbr;
    }

    /**
     * Prompts the user to select a product type for a new order
     * @param productList list of all products
     * @return product type selected by the user
     */
    public String getProductType(List<Product> productList) {
        int productNum = 1;

        io.print("--- Available Products ---");
        for (Product product : productList) {
            io.print(productNum + ": " + product.toString());
            productNum++;
        }

        int productSelection = io.readInt("Enter number for Product Type of new order.",
                1, productList.size());

        Product product = productList.get(productSelection - 1);

        return product.getProductType();
    }

    /**
     * Prompts the user to enter the area (sq ft) for a new order
     * @return area entered by the user
     */
    public BigDecimal getNewOrderArea() {
        return io.readBigDecimal("Enter area (square feet) for new order.",
                BigDecimal.valueOf(100));
    }

    /**
     * Prompts the user to confirm if they want to place the new order
     * @param newOrder new Order object created from user input
     * @return user selection to confirm or discard saving the new order
     */
    public int confirmPlaceNewOrder(Order newOrder) {
        io.print(newOrder.toString());
        return io.readInt("Would you like to place this order?" +
                " Enter 1 for YES or 2 for NO.", 1, 2);
    }

    /**
     * Displays a message to confirm that the new order was saved
     */
    public void orderPlacedSuccessMsg() {
        io.print("Order successfully placed!");
        continueMessage();
    }

    /**
     * Displays a message that the new order was discarded
     */
    public void orderDiscardedMsg() {
        io.print("Order cancelled! New order was discarded and not saved.");
        continueMessage();
    }

    /**
     * Prompts the user for the date of the order they want to edit
     * @return date entered by the user
     */
    public LocalDate getDateToEdit() {
        return io.readDate("Enter a date for the Order to be edited. (MMDDYYYY)");
    }

    /**
     * Prompts the user for the order number they want to edit
     * @return order number (int) entered by the user
     */
    public int readOrderNumToEdit() {
        return io.readInt("Enter the Order # for the order to be edited.");
    }

    /**
     * Displays the current order customer name and allows the user to
     * leave the customer name as-is or enter a new customer name
     * @param oldCustomerName customer name from original Order
     * @return customer name for edited order (regardless of whether it was changed)
     */
    public String editOrderCustomerName(String oldCustomerName) {
        io.print("Current customer name: " + oldCustomerName);
        String newCustomerName = io.readString("Type new Customer Name " +
                "or press Enter to leave unchanged.");
        if (newCustomerName.equals(""))
            return oldCustomerName;
        else
            return newCustomerName;
    }

    /**
     * Displays the current order state abbreviation and allows the user to leave
     * the state abbreviation as-is or enter a new state abbreviation
     * @param oldStateName state abbreviation from original Order
     * @param stateAbbrList List of all state abbreviations
     * @return state abbreviation for edited order (regardless of whether it was changed)
     */
    public String editOrderState(String oldStateName, List<String> stateAbbrList) {
        io.print("Current State: " + oldStateName);

        String newStateAbbr = io.readString("Type new State Abbreviation or " +
                "press Enter to leave unchanged.").toUpperCase();

        while (!stateAbbrList.contains(newStateAbbr)) {
            if (newStateAbbr.equals(""))
                return oldStateName;

            newStateAbbr = io.readString("Invalid entry! Please enter a " +
                    "valid State Abbreviation or press Enter to leave unchanged.")
                    .toUpperCase();
        }

        return newStateAbbr;
    }

    /**
     * Displays the current order product type and allows the user to leave
     * the product type as-is or select a new product type
     * @param oldProductType product type from original order
     * @param productList List of all Product objects
     * @param productTypeList List of all product types
     * @return product type for edited order (regardless of whether it was changed)
     */
    public String editOrderProductType(String oldProductType, List<Product> productList,
                                       List<String> productTypeList) {
        io.print("Current Product Type: " + oldProductType);

        // Display product list
        int productNum = 1;
        io.print("--- Available Products ---");
        for (Product product : productList) {
            io.print(productNum + ": " + product.toString());
            productNum++;
        }

        // Input must be a String to account for user being allowed
        // to press Enter to leave the product type unchanged
        String newProductTypeInt = io.readString("Type the number for the new " +
                "Product Type or press Enter to leave unchanged.");

        boolean isInt, isValid = false;

        while (!isValid) {
            // Test if the user input is an int
            try {
                Integer.parseInt(newProductTypeInt);
                isInt = true;
            } catch (NumberFormatException e) {
                isInt = false;
            }

            // If user pressed enter, return the original product type
            if (newProductTypeInt.equals("")) {
                return oldProductType;
            }
            // If the user entered an int within the correct range for the
            // product types, set isValid to true to escape the while loop
            else if (isInt && Integer.parseInt(newProductTypeInt) >= 1
                    && Integer.parseInt(newProductTypeInt) <= productTypeList.size()) {
                isValid = true;
            }
            // If user didn't press enter (leave product type unchanged) and
            // did not enter a valid int, display invalid entry and re-prompt
            else {
                newProductTypeInt = io.readString("Invalid entry! Enter " +
                        "a valid Product Type or press Enter to leave unchanged.");
            }
        }

        // Return the new product type selection
        return productTypeList.get(Integer.parseInt(newProductTypeInt) - 1);
    }

    /**
     * Displays the current order area (sq ft) and allows the user to leave
     * the area as-is or enter a new area
     * @param oldArea area from original order
     * @return area for edited order (regardless of whether it was changed)
     */
    public BigDecimal editOrderArea(BigDecimal oldArea) {
        io.print("Current Area: " + oldArea);

        // Input must be a String to account for user being allowed
        // to press Enter to leave the area unchanged
        String newArea = io.readString("Type new Area or press " +
                "Enter to leave unchanged.");
        if (newArea.equals(""))
            return oldArea;

        while (new BigDecimal(newArea).compareTo(BigDecimal.valueOf(100)) < 0) {
            newArea = io.readString("Invalid entry! Enter a sq ft amount "
                    + "of at least 100 or press Enter to leave unchanged.");

            if (newArea.equals(""))
                return oldArea;
        }

        return new BigDecimal(newArea);
    }

    /**
     * Display message indicating that no changes were made when editing the order
     */
    public void displayNoInfoChangedMsg() {
        io.print("All order information is unchanged. Order will not be edited.");
        continueMessage();
    }

    /**
     * Displays the original and edited orders and prompts user to confirm if
     * they want to save the edited order information
     * @param orderToEdit original order
     * @param editedOrder edited order
     * @return user selection to confirm or discard editing the order
     */
    public int confirmEditOrder(Order orderToEdit, Order editedOrder) {
        io.print("Original Order:\n" + orderToEdit.toString());
        io.print("Edited Order:\n" + editedOrder.toString());
        return io.readInt("Do you want to save the edited Order?" +
                " Enter 1 for Yes or 2 for No.", 1, 2);
    }

    /**
     * Displays a message to confirm that the order edit was saved
     */
    public void editSuccessMsg() {
        io.print("Order edited successfully!");
        continueMessage();
    }

    /**
     * Displays a message that the edited order information was discarded
     */
    public void editDiscardedMsg() {
        io.print("Edit cancelled! Order was not updated.");
        continueMessage();
    }

    /**
     * Prompts the user for the date of the order they want to remove
     * @return date of order
     */
    public LocalDate getDateToRemove() {
        return io.readDate("Enter a date for the Order to be removed. (MMDDYYYY)");
    }

    /**
     * Prompts the user for the order number of the order they want to remove
     * @return order number (int)
     */
    public int readOrderNumToRemoved() {
        return io.readInt("Enter the Order # you wish to remove.");
    }

    /**
     * Prompts user to confirm if they want to remove the order
     * @param order order selected to remove
     * @return user selection to confirm or disregard removing the order
     */
    public int confirmRemoveOrder(Order order) {
        io.print(order.toString());
        return io.readInt("Would you like to delete this order?" +
                " Enter 1 for YES or 2 for NO.", 1, 2);
    }

    /**
     * Displays a message to confirm that the order was removed
     */
    public void orderRemovedSuccessMsg() {
        io.print("Order removed successfully!");
        continueMessage();
    }

    /**
     * Displays a message that the order was not removed
     */
    public void removeOrderDisregardMsg() {
        io.print("Remove cancelled! Order was not removed.");
        continueMessage();
    }

    /**
     * Displays banner for exporting order data
     */
    public void displayExportBanner() {
        io.print("\n----- Export Order Data -----");
    }

    /**
     * Prompts user to confirm if they want to export all order data
     * @return user selection to confirm or disregard exporting order data
     */
    public int getExportConfirmation() {
        return io.readInt("Do you want to export all order data?" +
                " Enter 1 for Yes or 2 for No.", 1, 2);
    }

    /**
     * Displays a message to confirm that all order data was exported
     */
    public void displayExportSuccessMsg() {
        io.print("All order data exported successfully!");
        continueMessage();
    }

    /**
     * Displays a message that the export was skipped and order data
     * was not exported
     */
    public void skipExportMessage() {
        io.print("Orders will not be exported at this time.");
        continueMessage();
    }

    /**
     * Displays message that the application is exiting
     */
    public void displayExitMessage() {
        io.print("Exiting Flooring Order App... Goodbye!!!");
    }

    /**
     * Displays unknown command message
     */
    public void displayUnknownCommand() {
        io.print("\n===== UNKNOWN COMMAND =====");
        continueMessage();
    }

    /**
     * Displays an error message
     * @param errorMsg error message
     */
    public void displayErrorMessage(String errorMsg) {
        io.print("\n===== ERROR =====");
        io.print(errorMsg);
        continueMessage();
    }

    /**
     * Prompts the user to press enter to continue. Used
     * within other methods in the View
     */
    private void continueMessage() {
        io.readString("Press Enter to Continue...");
    }
}

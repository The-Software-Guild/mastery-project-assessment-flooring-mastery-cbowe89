package FlooringMastery.ui;

import FlooringMastery.model.Order;
import FlooringMastery.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class View {
    private final UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public void displayTitleBanner() {
        io.print("\n******************************");
        io.print("***   Flooring Order App   ***");
        io.print("******************************\n");
    }

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

    public LocalDate getDateToDisplay() {
        io.print("\n----- Display All Orders For A Date -----");
        return io.readDate("Enter a date to display orders. (MMDDYYYY)");
    }

    public void displayOrdersForDate(List<Order> allOrders) {
        for (Order order : allOrders) {
            io.print(order.toString());
        }
        continueMessage();
    }

    public void displayAddOrderBanner() {
        io.print("\n----- Add A New Order -----");
    }

    public LocalDate getNewOrderDate() {
        return io.readDate("Enter a date in the future for the new order.",
                LocalDate.now());
    }

    public String getNewCustomerName() {
        return io.readString("Enter a Customer Name for the new order.");
    }

    public String getNewOrderState(List<String> stateAbbrList) {
        String stateAbbr =  io.readString("Enter a State Abbreviation for " +
                "the new order. (Ex: Enter TX for Texas, FL for Florida").toUpperCase();

        while (!stateAbbrList.contains(stateAbbr)) {
            stateAbbr = io.readString("Invalid entry! Enter a valid " +
                    "State Abbreviation.").toUpperCase();
        }

        return stateAbbr;
    }

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

    public BigDecimal getNewOrderArea() {
        return io.readBigDecimal("Enter area (square feet) for new order.",
                BigDecimal.valueOf(100));
    }

    public int confirmPlaceNewOrder(Order newOrder) {
        io.print(newOrder.toString());
        return io.readInt("Would you like to place this order?" +
                " Enter 1 for YES or 2 for NO.", 1, 2);
    }

    public void orderPlacedSuccessMsg() {
        io.print("Order successfully placed!");
        continueMessage();
    }

    public void orderDiscardedMsg() {
        io.print("Order cancelled!");
        continueMessage();
    }

    public LocalDate getDateToEdit() {
        return io.readDate("Enter a date for the Order to be edited. (MMDDYYYY)");
    }

    public int readOrderNumToEdit() {
        return io.readInt("Enter the Order # for the order to be edited.");
    }

    public String editOrderCustomerName(String oldCustomerName) {
        io.print("Current customer name: " + oldCustomerName);
        String newCustomerName = io.readString("Type new Customer Name " +
                "or press Enter to leave unchanged.");
        if (newCustomerName.equals(""))
            return oldCustomerName;
        else
            return newCustomerName;
    }

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

    public String editOrderProductType(String oldProductType, List<Product> productList,
                                       List<String> productTypeList) {
        io.print("Current Product Type: " + oldProductType);

        int productNum = 1;
        io.print("--- Available Products ---");
        for (Product product : productList) {
            io.print(productNum + ": " + product.toString());
            productNum++;
        }

        String newProductTypeInt = io.readString("Type the number for the new " +
                "Product Type or press Enter to leave unchanged.");

        boolean isInt, isValid = false;

        while (!isValid) {
            try {
                Integer.parseInt(newProductTypeInt);
                isInt = true;
            } catch (NumberFormatException e) {
                isInt = false;
            }

            if (newProductTypeInt.equals("")) {
                return oldProductType;
            }
            else if (isInt && Integer.parseInt(newProductTypeInt) >= 1
                    && Integer.parseInt(newProductTypeInt) <= productTypeList.size()) {
                isValid = true;
            }
            else {
                newProductTypeInt = io.readString("Invalid entry! Enter " +
                        "a valid Product Type or press Enter to leave unchanged.");
            }
        }

        return productTypeList.get(Integer.parseInt(newProductTypeInt) - 1);
    }

    public BigDecimal editOrderArea(BigDecimal oldArea) {
        io.print("Current Area: " + oldArea);

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

    public void displayNoInfoChangedMsg() {
        io.print("All order information is unchanged. Order will not be edited.");
        continueMessage();
    }

    public int confirmEditOrder(Order orderToEdit, Order editedOrder) {
        io.print("Original Order:\n" + orderToEdit.toString());
        io.print("Edited Order:\n" + editedOrder.toString());
        return io.readInt("Do you want to save the edited Order?" +
                " Enter 1 for Yes or 2 for No.", 1, 2);
    }

    public void editSuccessMsg() {
        io.print("Order edited successfully!");
        continueMessage();
    }

    public void editDiscardedMsg() {
        io.print("Edit cancelled! Order was not updated.");
        continueMessage();
    }

    public LocalDate getDateToRemove() {
        return io.readDate("Enter a date for the Order to be removed. (MMDDYYYY)");
    }

    public int readOrderNumToRemoved() {
        return io.readInt("Enter the Order # you wish to remove.");
    }

    public int confirmRemoveOrder(Order order) {
        io.print(order.toString());
        return io.readInt("Would you like to delete this order?" +
                " Enter 1 for YES or 2 for NO.", 1, 2);
    }

    public void orderRemovedSuccessMsg() {
        io.print("Order removed successfully!");
        continueMessage();
    }

    public void removeOrderDisregardMsg() {
        io.print("Remove cancelled! Order was not removed.");
        continueMessage();
    }

    public void displayExportBanner() {
        io.print("\n----- Export Order Data -----");
    }

    public int getExportConfirmation() {
        return io.readInt("Do you want to export all order data?" +
                " Enter 1 for Yes or 2 for No.", 1, 2);
    }

    public void displayExportSuccessMsg() {
        io.print("All order data exported successfully!");
        continueMessage();
    }

    public void skipExportMessage() {
        io.print("Orders will not be exported at this time.");
        continueMessage();
    }

    public void displayExitMessage() {
        io.print("Exiting Flooring Order App... Goodbye!!!");
    }

    public void displayUnknownCommand() {
        io.print("\n===== UNKNOWN COMMAND =====");
        continueMessage();
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("\n===== ERROR =====");
        io.print(errorMsg);
        continueMessage();
    }

    public void continueMessage() {
        io.readString("Press Enter to Continue...");
    }
}

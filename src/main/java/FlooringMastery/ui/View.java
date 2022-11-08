package FlooringMastery.ui;

import FlooringMastery.model.Order;

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
        io.print("******************************");
    }

    public int getMainMenuSelection() {
        io.print("\n********** MAIN MENU **********");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("*******************************");

        return io.readInt("Please selection an option.", 1, 6);
    }

    public void displayAllBanner() {
        io.print("\n----- Display All Orders For A Date -----");
    }

    public LocalDate getDateToDisplay() {
        return io.readDate("Enter a date to display orders. (MMDDYYYY)");
    }

    public void displayOrdersForDate(List<Order> allOrders) {
        for (Order order : allOrders) {
            io.print(order.toString());
        }
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
        io.readString("Please hit enter to continue.");
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

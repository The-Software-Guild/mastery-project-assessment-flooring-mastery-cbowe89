package FlooringMastery.dao;

import FlooringMastery.model.Order;
import FlooringMastery.model.Product;
import FlooringMastery.model.State;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The {@code FileDaoImpl} class is responsible for unmarshalling
 * and reading Product and State (tax) files. It is also responsible
 * for unmarshalling/marshalling and reading/writing from/to Order files.
 */
public class FileDaoImpl implements FileDao {
    // Declare and initialize delimiter user in files
    private static final String DELIMITER = ",";
    private final String ORDER_DIRECTORY;
    private final String EXPORT_FILE;

    public FileDaoImpl() {
        this.ORDER_DIRECTORY = "Orders";
        this.EXPORT_FILE = "Backup/DataExport.txt";
    }

    /**
     * No args constructor for FileDaoImpl
     */
    public FileDaoImpl(String orderDirectory, String exportFile) {
        this.ORDER_DIRECTORY = orderDirectory;
        this.EXPORT_FILE = exportFile;
    }

    /**
     * Reads a line of Product information and unmarshalls the information
     * to create a new Product object
     * @param line product information
     * @return new Product object
     */
    @Override
    public Product unmarshallProduct(String line) {
        // Split line at DELIMITER, store tokens in array
        String[] productTokens = line.split(DELIMITER);

        // First token (index 0) is productType
        String productType = productTokens[0];

        // Create new Product object
        Product productFromFile = new Product(productType);

        // Set costPerSquareFoot from second token (index 1)
        productFromFile.setCostPerSquareFoot(new BigDecimal(productTokens[1])
                .setScale(2, RoundingMode.DOWN));

        // Set laborCostPerSquareFoot from third token (index 2)
        productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2])
                .setScale(2, RoundingMode.DOWN));

        // Return new Product object
        return productFromFile;
    }

    /**
     * Reads a file of Product information, creates Product objects
     * based on the information in each line, and creates a list of
     * Product objects to return
     * @param fileName Product information file
     * @return list of Product objects
     * @throws PersistenceException if product file not found
     */
    @Override
    public List<Product> readProductFile(String fileName)
            throws PersistenceException {
        // Declare and initialize variables
        List<Product> productList = new ArrayList<>();
        boolean skipFirstLine = true;
        Scanner sc;

        try {
            // Initialize Scanner object
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));

            String currentLine; // To hold currentLine while reading file
            Product currentProduct; // To hold Product object from currentLine

            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                if (skipFirstLine)
                    // Skip first line which is header row
                    skipFirstLine = false;
                else {
                    // Unmarshall currentLine, create new Product object
                    currentProduct = unmarshallProduct(currentLine);
                    // Add new Product object to productList
                    productList.add(currentProduct);
                }
            }

            // Close Scanner object
            sc.close();

            // Return list of Product objects
            return productList;
        } catch (FileNotFoundException e) {
            // Throw exception if unable to find product file
            throw new PersistenceException("Product file not found.", e);
        }
    }

    /**
     * Reads a line of Tax information and unmarshalls the information
     * to create a new State object
     * @param line state/tax information
     * @return new State object
     */
    @Override
    public State unmarshallTaxes(String line) {
        // Split line at DELIMITER, store tokens in array
        String[] taxTokens = line.split(DELIMITER);

        // First token (index 0) is State abbreviation
        String stateAbbr = taxTokens[0];

        // Create new State object
        State stateFromFile = new State(stateAbbr);

        // Set state name from second token (index 1)
        stateFromFile.setStateName(taxTokens[1]);

        // Set state tax rate from third token (index 2)
        stateFromFile.setTaxRate(new BigDecimal(taxTokens[2])
                .setScale(3, RoundingMode.HALF_UP));

        // Return new State object
        return stateFromFile;
    }

    /**
     * Reads a file of State/Tax information, creates State objects
     * based on the information in each line, and creates a list of
     * State objects to return
     * @param fileName State information file
     * @return list of State objects
     * @throws PersistenceException if state/tax file not found
     */
    @Override
    public List<State> readTaxFile(String fileName)
            throws PersistenceException {
        // Declare and initialize variables
        List<State> stateList = new ArrayList<>();
        boolean skipFirstLine = true;
        Scanner sc;

        try {
            // Initialize Scanner object
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));

            String currentLine; // To hold currentLine while reading file
            State currentState; // To hold State object from currentLine

            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                if (skipFirstLine)
                    // Skip first line which is header row
                    skipFirstLine = false;
                else {
                    // Unmarshall currentLine, create new State object
                    currentState = unmarshallTaxes(currentLine);
                    // Add new State object to stateList
                    stateList.add(currentState);
                }
            }

            // Close Scanner object
            sc.close();

            // Return list of State objects
            return stateList;
        } catch (FileNotFoundException e) {
            // Throw exception if unable to find state/tax file
            throw new PersistenceException("Tax file not found.", e);
        }
    }

    /**
     * Reads a line of Order information and unmarshalls the information
     * to create a new Order object
     * @param line Order information
     * @return new Order object
     */
    @Override
    public Order unmarshallOrder(String line) {
        // Split line at DELIMITER, store tokens in array
        String[] orderTokens = line.split(DELIMITER);

        // First token (index 0) is Order number
        String orderNumber = orderTokens[0];

        // Create new Order object
        Order orderFromFile = new Order(Integer.parseInt(orderNumber));

        // Set remaining Order attributes based on remaining tokens
        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        orderFromFile.setProductType(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        orderFromFile.setCostPerSquareFoot(new BigDecimal(orderTokens[6]));
        orderFromFile.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]));
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));

        // Return new Order object
        return orderFromFile;
    }

    /**
     * Reads a file of Order information, creates Order objects
     * based on the information in each line, and creates a list of
     * Order objects to return
     * @param date Date of orders
     * @return List of Orders objects
     * @throws PersistenceException if Order file not found
     */
    @Override
    public List<Order> readOrderFile(LocalDate date) throws
            PersistenceException {
        // Declare variables
        List<Order> orderList = new ArrayList<>();
        boolean skipFirstLine = true;
        Scanner sc;

        // Ensure date is formatted properly, store as string
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        // Create new File instance
        File file = new File(String.format(ORDER_DIRECTORY + "/Orders_%s.txt", formattedDate));

        try {
            // Initialize Scanner object
            sc = new Scanner(new BufferedReader(new FileReader(file)));

            String currentLine; // To hold currentLine while reading file
            Order currentOrder; // To hold Order object from currentLine

            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                if (skipFirstLine) {
                    // Skip first line which is header row
                    skipFirstLine = false;
                } else {
                    // Unmarshall currentLine, create new Order object
                    currentOrder = unmarshallOrder(currentLine);
                    // Add new Order object to orderList
                    orderList.add(currentOrder);
                }
            }

            // Close Scanner object
            sc.close();

            // Return list of Order objects
            return orderList;
        } catch (FileNotFoundException e) {
            // Throw exception if unable to find order file
            throw new PersistenceException("Order file for date not found.", e);
        }
    }

    /**
     * Converts an order to the appropriate String with delimiters needed
     * to store in Order file
     * @param order order to store in file
     * @return Order as string, formatted for storing in an Order File
     */
    @Override
    public String marshallOrder(Order order) {
        // Create and return a string of all order properties, with each property
        // separated with delimiters
        return order.getOrderNumber() + DELIMITER + order.getCustomerName()
                + DELIMITER + order.getState() + DELIMITER + order.getTaxRate()
                + DELIMITER + order.getProductType() + DELIMITER + order.getArea()
                + DELIMITER + order.getCostPerSquareFoot() + DELIMITER
                + order.getLaborCostPerSquareFoot() + DELIMITER + order.getMaterialCost()
                + DELIMITER + order.getLaborCost() + DELIMITER + order.getTax()
                + DELIMITER + order.getTotal();
    }

    /**
     * Writes order to appropriate file based on the Order date. If no
     * file exists for the date, a new file is created in the Orders directory.
     * @param date date of Order
     * @param order Order to write to file
     * @throws PersistenceException if unable to write Order to file
     */
    @Override
    public void writeNewOrder(LocalDate date, Order order)
            throws PersistenceException {
        // Declare PrintWriter object
        PrintWriter out;

        // Create string to ensure date is properly formatted
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        // Create new instance of File
        File file = new File(String.format(ORDER_DIRECTORY + "/Orders_%s.txt",
                formattedDate));

        try {
            // Initialize PrintWriter object
            out = new PrintWriter(new FileWriter((file), true));

            // If File length is 0, there was no previous file for the date
            // Add header row to file
            if (file.length() == 0) {
                out.println("OrderNumber,CustomerName,State,TaxRate,ProductType," +
                        "Area,CostPerSquareFoot,LaborCostPerSquareFoot," +
                        "MaterialCost,LaborCost,Tax,Total");
            }

            // Write Order to file (appended to end of file)
            out.println(marshallOrder(order));
            // Flush stream
            out.flush();
            // Close PrintWriter object
            out.close();
        } catch (IOException e) {
            // Throw exception if unable to write to Order file
            throw new PersistenceException("Could not write Order information.", e);
        }
    }

    /**
     * Edits order information in the appropriate file based on Order date.
     * @param date date of Order
     * @param orderToEdit Order that was edited
     * @param editedOrder Edited version of Order
     * @throws PersistenceException if unable to write Order to file
     */
    @Override
    public void writeEditOrder(LocalDate date, Order orderToEdit, Order editedOrder)
            throws PersistenceException {
        // Declare Scanner, PrintWriter, and StringBuilder objects
        Scanner sc;
        PrintWriter out;
        StringBuilder builder;

        // Ensure correct format of date, save as String, and create fileName String
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        String fileName = String.format(ORDER_DIRECTORY + "/Orders_%s.txt",
                formattedDate);

        try {
            // Initialize PrintWriter object
            sc = new Scanner(new File(fileName));

            // Initialize new StringBuilder object
            builder = new StringBuilder();

            // Append all lines from Order file to StringBuilder
            while (sc.hasNextLine())
                builder.append(sc.nextLine()).append(System.lineSeparator());

            // Convert StringBuilder to String
            String fileContents = builder.toString();

            // Close Scanner object
            sc.close();

            // Replace original order information with the edited information
            // in the fileContents String
            fileContents = fileContents.replaceAll(marshallOrder(orderToEdit),
                    marshallOrder(editedOrder));

            // Initialize PrintWriter object
            out = new PrintWriter(new FileWriter(fileName));

            // Write fileContents String to the File
            // Will replace all contents of the file
            out.print(fileContents);

            // Flush stream
            out.flush();
            // Close PrintWriter object
            out.close();
        } catch (IOException e) {
            // Throw exception if unable to edit Order in file
            throw new PersistenceException("Could not edit Order in file.", e);
        }
    }

    /**
     * Removes an Order from the appropriate Order File based on Order Date
     * @param date date of Order
     * @param orderToRemove Order object to remove from file
     * @throws PersistenceException if unable to remove Order
     */
    @Override
    public void removeOrderFromFile(LocalDate date, Order orderToRemove)
            throws PersistenceException {
        // Declare Scanner, PrintWriter, and StringBuilder objects
        Scanner sc;
        PrintWriter out;
        StringBuilder stringBuilder;

        // Ensure correct format of date, save as String, and create fileName String
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        String fileName = String.format(ORDER_DIRECTORY + "/Orders_%s.txt",
                formattedDate);

        try {
            // Initialize Scanner and StringBuilder objects
            sc = new Scanner(new File(fileName));
            stringBuilder = new StringBuilder();

            // Append all lines of file to stringBuilder
            while (sc.hasNextLine())
                stringBuilder.append(sc.nextLine()).append(System.lineSeparator());

            // Create String from stringBuilder
            String fileContents = stringBuilder.toString();

            // Scanner no longer used - close Scanner
            sc.close();

            // Remove order from fileContents String by replacing it with ""
            fileContents = fileContents.replaceAll(marshallOrder(orderToRemove)
                    .concat(System.lineSeparator()), "");

            // Initialize PrintWriter object
            out = new PrintWriter(new FileWriter(fileName));

            // Use PrintWriter to write edited fileContents to file
            out.print(fileContents);

            // Flush stream
            out.flush();
            // Close PrintWriter object
            out.close();
        } catch (IOException e) {
            // Throw exception if unable to remove Order from file
            throw new PersistenceException("Could not remove Order from file.", e);
        }
    }

    /**
     * Converts an order to the appropriate String with delimiters, including
     * Order date, to store in Export file
     * @param order Order to write in export file
     * @param date date of Order
     * @return Order as string, formatted for storing in Export File
     */
    @Override
    public String marshallOrderForExport(Order order, String date) {
        return order.getOrderNumber() + DELIMITER + order.getCustomerName()
                + DELIMITER + order.getState() + DELIMITER + order.getTaxRate()
                + DELIMITER + order.getProductType() + DELIMITER + order.getArea()
                + DELIMITER + order.getCostPerSquareFoot() + DELIMITER
                + order.getLaborCostPerSquareFoot() + DELIMITER + order.getMaterialCost()
                + DELIMITER + order.getLaborCost() + DELIMITER + order.getTax()
                + DELIMITER + order.getTotal() + DELIMITER + date;
    }

    /**
     * Reads all Order Files, writes all Order information with the Order date
     * to the Export File
     * @throws PersistenceException if unable to Export all Order data
     */
    @Override
    public void exportAllData()
            throws PersistenceException {
        // Declare PrintWriter object
        PrintWriter out;

        // Declare and initialize DateTimeFormatter object
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");

        // Create File object for directory where Order files are located
        File directoryPath = new File(ORDER_DIRECTORY);

        // Declare and initialize list with names of all order files
        List<String> orderFileNames =
                List.of(Objects.requireNonNull(directoryPath.list()));

        // Declare list to store Orders
        List<Order> orderList;

        try {
            // Initialize PrintWriter object
            out = new PrintWriter(new FileWriter(EXPORT_FILE));

            // Declare variables
            String orderAsText, dateString;

            // Iterate through List of orderFileNames
            for (String fileName : orderFileNames) {
                // Set dateString to date of file
                dateString = fileName.substring(7, 15);

                // Declare and initialize LocalDate object from dateString and formatter
                LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);

                // Read order file, store orders in list
                orderList = readOrderFile(localDate);

                // Iterate through individual order files
                for (Order currentOrder : orderList) {
                    // Marshall the current order file
                    orderAsText = marshallOrderForExport(currentOrder, dateString);
                    // Write current file to DataExport file
                    out.println(orderAsText);
                    // Flush stream
                    out.flush();
                }
            }
            // Close PrintWriter object
            out.close();
        }catch (IOException e) {
            // Throw exception if unable to export all orders
            throw new PersistenceException("Could not export order data.", e);
        }
    }

    /**
     * Fetches the latest order number assigned, then calculates and
     * returns a new order number to assign to a new order
     * @return int newOrderNum
     * @throws PersistenceException if error occurs reading files
     */
    @Override
    public int generateNewOrderNum() throws PersistenceException {
        try {
            // Declare and initialize DateTimeFormatter object
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");

            // Create File object for directory where Order files are located
            File directoryPath = new File(ORDER_DIRECTORY);

            // Declare and initialize list with names of all order files
            List<String> orderFileNames =
                    List.of(Objects.requireNonNull(directoryPath.list()));
            List<Order> orderList;
            List<Integer> orderNumList = new ArrayList<>();
            String dateString;

            for (String fileName : orderFileNames) {
                // Set dateString to date of file
                dateString = fileName.substring(7, 15);

                // Declare and initialize LocalDate object from dateString and formatter
                LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);

                orderList = readOrderFile(localDate);

                for (Order order : orderList) {
                    orderNumList.add(order.getOrderNumber());
                }
            }

            if (orderNumList.isEmpty())
                return 1;
            else
                return orderNumList.get(orderNumList.size() - 1) + 1;
        } catch (Exception e) {
            // Throw exception if unable to read order files/generate new order number
            throw new PersistenceException("Unable to generate new order number.");
        }
    }
}

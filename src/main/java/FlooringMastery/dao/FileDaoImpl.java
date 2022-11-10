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

public class FileDaoImpl implements FileDao {
    private static final String DELIMITER = ",";

    public FileDaoImpl() {
    }

    @Override
    public Product unmarshallProduct(String line) {
        String[] productTokens = line.split(DELIMITER);
        String productType = productTokens[0];
        Product productFromFile = new Product(productType);
        productFromFile.setCostPerSquareFoot(new BigDecimal(productTokens[1])
                .setScale(2, RoundingMode.DOWN));
        productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2])
                .setScale(2, RoundingMode.DOWN));
        return productFromFile;
    }

    @Override
    public List<Product> readProductFile(String fileName)
            throws PersistenceException {
        List<Product> productList = new ArrayList<>();

        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));

            String currentLine;
            Product currentProduct;

            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentProduct = unmarshallProduct(currentLine);
                productList.add(currentProduct);
            }
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Product file not found.", e);
        }

        sc.close();

        return productList;
    }

    @Override
    public State unmarshallTaxes(String line) {
        String[] taxTokens = line.split(DELIMITER);
        String stateAbbr = taxTokens[0];
        State stateFromFile = new State(stateAbbr);
        stateFromFile.setStateName(taxTokens[1]);
        stateFromFile.setTaxRate(new BigDecimal(taxTokens[2]));
        return stateFromFile;
    }

    @Override
    public List<State> readTaxFile(String fileName)
            throws PersistenceException {
        List<State> stateList = new ArrayList<>();

        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Tax file not found.", e);
        }

        String currentLine;
        State currentState;

        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentState = unmarshallTaxes(currentLine);
            stateList.add(currentState);
        }

        sc.close();

        return stateList;
    }

    @Override
    public Order unmarshallOrder(String line) {
        String[] orderTokens = line.split(DELIMITER);
        String orderNumber = orderTokens[0];
        Order orderFromFile = new Order(Integer.parseInt(orderNumber));
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
        return orderFromFile;
    }

    @Override
    public List<Order> readOrderFile(LocalDate date) throws
            PersistenceException {
        Scanner sc;
        boolean skipFirstLine = true;
        List<Order> orderList = new ArrayList<>();

        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        File file = new File(String.format("Orders/Orders_%s.txt", formattedDate));

        if(file.isFile()) {
            try {
                sc = new Scanner(new BufferedReader(new FileReader(file)));

                String currentLine;
                Order currentOrder;

                while (sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                    if (skipFirstLine) {
                        skipFirstLine = false;
                    }
                    else {
                        currentOrder = unmarshallOrder(currentLine);
                        orderList.add(currentOrder);
                    }
                }
                sc.close();
            } catch (FileNotFoundException e) {
                throw new PersistenceException("Order file for date not found.", e);
            }
        }

        return orderList;
    }

    @Override
    public String marshallOrder(Order order) {
        return order.getOrderNumber() + DELIMITER + order.getCustomerName()
                + DELIMITER + order.getState() + DELIMITER + order.getTaxRate()
                + DELIMITER + order.getProductType() + DELIMITER + order.getArea()
                + DELIMITER + order.getCostPerSquareFoot() + DELIMITER
                + order.getLaborCostPerSquareFoot() + DELIMITER + order.getMaterialCost()
                + DELIMITER + order.getLaborCost() + DELIMITER + order.getTax()
                + DELIMITER + order.getTotal();
    }

    @Override
    public void writeNewOrder(LocalDate date, Order order)
            throws PersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(("Orders/Order_" +
                    date + ".txt"), true));

            // Write Order to file (appended to end of file)
            out.println(marshallOrder(order));
            // Flush stream
            out.flush();

        } catch (IOException e) {
            throw new PersistenceException(
                    "Could not persist order information.", e);
        }
    }

    @Override
    public void writeEditOrder(Order originalOrder, Order editedOrder,
                               LocalDate date) throws PersistenceException {
        String originalOrderAsString = marshallOrder(originalOrder);
        String editedOrderAsString = marshallOrder(editedOrder);
        String filePath = "Orders/Orders_" + date + ".txt";

        File fileToEdit = new File(filePath);
        StringBuilder oldContent = new StringBuilder();
        BufferedReader bufferedReader;
        FileWriter fileWriter;

        try {
            bufferedReader = new BufferedReader(new FileReader(fileToEdit));

            String line = bufferedReader.readLine();

            while (line != null) {
                oldContent.append(line).append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            String newContent = oldContent.toString().replaceAll(originalOrderAsString,
                    editedOrderAsString);

            fileWriter = new FileWriter(filePath);

            fileWriter.write(newContent);

            bufferedReader.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new PersistenceException("Could not persist order edit.", e);
        }

    }

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

    @Override
    public void exportAllData(String exportFile)
            throws PersistenceException {
        PrintWriter out; // Declare PrintWriter object

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");

        // Create File object for directory
        File directoryPath = new File("Orders");

        // Declare and initialize list with names of all order files
        List<String> orderFileNames =
                List.of(Objects.requireNonNull(directoryPath.list()));

        List<Order> orderList;

        try {
            // Initialize PrintWriter object
            out = new PrintWriter(new FileWriter(exportFile));

            // Declare variables
            String orderAsText, dateString;

            // Iterate through List of orderFileNames
            for (String fileName : orderFileNames) {
                // Set dateString to date of file
                dateString = fileName.substring(7, 15);

                LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);

                // Read order file
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

        }catch (IOException e) {
            throw new PersistenceException("Could not export data.", e);
        }
    }
}

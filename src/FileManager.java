import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;

/***
 * This class contains the FileManager class and its instance methods.
 * This class handles input/output methods with the databases.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */


public class FileManager {
    /**
     * Reads from the items.txt file, populates a LinkedHashSet of Tool objects, and returns that LinkedHashSet.
     * @param supplierList the SupList object to be added to the list of Tool objects.
     * @return the list of Tool objects.
     */
    public LinkedHashSet<Tool> loadToolListFromDB(SupList supplierList) {
        LinkedHashSet<Tool> toolList = new LinkedHashSet<Tool>();
        BufferedReader objReader = null;
        try {
            objReader = getReader("\\src\\items.txt");           //TODO: remove \\src when submitting
            appendToolToToolList(supplierList, toolList, objReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toolList;
    }

    /**
     * Adds the supplier to the corresponding tool with the supplier's ID.
     * @param supplierList the list of suppliers.
     * @param supplierId the supplier Id.
     * @return the Supplier object if the Id's match.  Otherwise returns null.
     */
    private Supplier connectSupplierIDToSupplier(SupList supplierList, int supplierId) {
        for (Supplier supplier : supplierList.getSupplierList()) {
            if (supplier.getSupplierId() == supplierId) {
                return supplier;
            }
        }
        return null;
    }

    /**
     * Reads from the suppliers.txt file, populates a LinkedHashSet of Supplier objects, and returns that LinkedHashSet.
     * @return the list of Supplier objects.
     */
    public LinkedHashSet<Supplier> loadSupplierListFromDB() {
        LinkedHashSet<Supplier> supplierList = new LinkedHashSet<Supplier>();
        BufferedReader objReader = null;
        try {
            objReader = getReader("\\src\\suppliers.txt");         //TODO: remove \\src when submitting
            appendSupplierToSupplierList(supplierList, objReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    /**
     * Creates a new BufferedReader object with the directory of the file to be opened
     * @param fileName the file name.
     * @return the BufferedReader object.
     * @throws FileNotFoundException throws exception if it can't read the text file.
     */
    private BufferedReader getReader(String fileName) throws FileNotFoundException {
        BufferedReader objReader;
        String currentDir = System.getProperty("user.dir");
        objReader = new BufferedReader(new FileReader(currentDir + fileName));
        return objReader;
    }

    /**
     * Creates and writes orders to orders.txt in the same folder.
     * @param orderList the list of Orders to be written.
     * @return true if successfully written to the text file.  Otherwise, returns false.
     */
    public boolean writeOrdersToDB(OrderRep orderList) {
        try {
            PrintWriter writer = new PrintWriter("orders.txt", StandardCharsets.UTF_8);
            for (Order order : orderList.getOrderList()) {
                writer.println(order);
            }
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Appends Supplier Object read from the database to the list of suppliers.
     * @param supplierList the list of Supplier objects.
     * @param objReader the BufferedReader object used to read from the text file.
     * @throws IOException throws exception if it can't read the supplier list text file.
     */
    private void appendSupplierToSupplierList(LinkedHashSet<Supplier> supplierList, BufferedReader objReader)
            throws IOException {
        String strCurrentLine;
        String[] values;
        while ((strCurrentLine = objReader.readLine()) != null) {
            values = strCurrentLine.split(";");
            Supplier tempSupplier = new Supplier(Integer.parseInt(values[0]), values[1], values[2], values[3]);
            supplierList.add(tempSupplier);
        }
    }

    /**
     * Appends Tool Object read from the database to the list of tools.
     * @param supplierList the list of Supplier objects.
     * @param toolList the list of Tool objects.
     * @param objReader the BufferedReader object used to read from the text file.
     * @throws IOException throws exception if it can't read the tool list text file.
     */
    private void appendToolToToolList(SupList supplierList, LinkedHashSet<Tool> toolList, BufferedReader objReader)
            throws IOException {
        String strCurrentLine;
        String[] values;
        while ((strCurrentLine = objReader.readLine()) != null) {
            values = strCurrentLine.split(";");
            Tool tempTool = new Tool(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]),
                    Double.parseDouble(values[3]), Integer.parseInt(values[4]),
                    connectSupplierIDToSupplier(supplierList, Integer.parseInt(values[4])));
            toolList.add(tempTool);
        }
    }
}

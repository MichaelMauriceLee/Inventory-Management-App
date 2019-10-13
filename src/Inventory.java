import java.util.ArrayList;
import java.util.LinkedHashSet;

/***
 * This class contains the Inventory class and its instance methods.
 * This class contains a list of Tool objects.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */


public class Inventory {
    ////////Instance Variables
    /**
     * The minimum stock needed for the system to automatically order new quantity of tools.
     */
    private final int minStock = 40;
    /**
     * The maximum quantity to be ordered when the system automatically order new quantity.
     */
    private final int defaultOrderQuantityMax = 50;
    /**
     * A list of tools.
     */
    private LinkedHashSet<Tool> toolList;

    ////////Constructor
    /**
     * Constructs a Inventory object with the specified values for toolList.
     * The values of the data fields are supplier by the given parameters.
     * @param toolList the list of Tool objects.
     */
    public Inventory(LinkedHashSet<Tool> toolList) {
        this.toolList = toolList;
    }

    ////////Instance Methods
    /**
     * Creates an order or appends an order depending on the date of the last order.
     * @param shop the Shop object which contains a list of orders and tools in the Inventory object.
     * @param toolList the list of Tools to be added to the order.
     * @param quantityList the list of quantities to be added to the order.
     * @param fileManager the FileManager object to write or append orders to the database.
     * @return true if the order was created or appended.  Otherwise, returns false.
     */
    public boolean createOrderOrAppendOrder(Shop shop, ArrayList<Tool> toolList,
                                            ArrayList<Integer> quantityList, FileManager fileManager) {
        if (shop.getOrderRep().checkTodayOrder()) {
            createOrder(shop, toolList, quantityList);
        } else {
            replaceDuplicateOrderLines(shop, toolList, quantityList);
            addNewOrderLines(shop, toolList, quantityList);
        }
        return fileManager.writeOrdersToDB(shop.getOrderRep());
    }

    /**
     * Calls the searchTheInventory function.
     * @param toolName the name of the Tool.
     * @return true if the Tool can be found.  Otherwise, returns false.
     */
    public boolean searchInventory(String toolName) {
        return searchTheInventory(toolName) != -1;
    }

    /**
     * Calls the searchTheInventory function.
     * @param itemId the ID of the tool.
     * @return true if the Tool can be found.  Otherwise, returns false.
     */
    public boolean searchInventory(int itemId) {
        return searchTheInventory(itemId) != -1;
    }

    /**
     * Calls the getAToolInfo function.
     * @param itemId the ID of the tool.
     * @return the Tool object.
     */
    public Tool getToolInfo(int itemId) {
        return getAToolInfo(itemId);
    }

    /**
     * Calls the getAToolInfo function to retrieve the price of a tool.
     * @param itemId the ID of the Tool.
     * @return the price of the Tool.
     */
    public double getToolPrice(int itemId) {
        return getAToolInfo(itemId).getPrice();
    }

    /**
     * Calls the checkAToolStock function.
     * @param itemId the ID of the tool.
     * @return the quantity of the tool.
     */
    public int checkToolStock(int itemId) {
        return checkAToolStock(itemId);
    }

    /**
     * Checks the quantity of all tools in toolList and returns a list of tools that need an order to be made for.
     * @return a list of tools that require an order to be made for.
     */
    public ArrayList<Tool> checkAllStock() {
        ArrayList<Tool> requiredTools = new ArrayList<Tool>();
        for (Tool tool : toolList) {
            if (tool.getQuantityInStock() < (minStock)) {
                requiredTools.add(tool);
            }
        }
        return requiredTools;
    }

    /**
     * Checks the quantity of all tools in toolList and returns a list of quantities that are needed for each item.
     * @return the list of quantities needed for each item needed.
     */
    public ArrayList<Integer> getRequiredOrderAmt() {
        ArrayList<Integer> quantityList = new ArrayList<Integer>();
        for (Tool tool : toolList) {
            if (tool.getQuantityInStock() < (minStock)) {
                quantityList.add(defaultOrderQuantityMax - tool.getQuantityInStock());
            }
        }
        return quantityList;
    }

    /**
     * Decreases a Tool's quantityInStock by the amount entered.
     * @param itemId the ID of the Tool object.
     * @param amountSold the amount to be decreased by.
     * @return true if the tool's stock was successfully decreased.  Otherwise, returns false.
     */
    public boolean decreaseToolStock(int itemId, int amountSold) {
        if (checkSaleQuantity(itemId, amountSold)) {
            for (Tool tool : toolList) {
                if (tool.getItemId() == itemId) {
                    tool.setQuantityInStock(tool.getQuantityInStock() - amountSold);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String st = "";
        for (Tool tool : toolList) {
            System.out.println(tool);
        }
        return st;
    }

    ////////Helper Methods
    /**
     * Adds new OrderLines to an existing Order.
     * @param shop the Shop Object which houses the list of orders.
     * @param toolList the list of tools to be added to the order.
     * @param quantityList the list of quantities of tools to be added to the order.
     */
    private void addNewOrderLines(Shop shop, ArrayList<Tool> toolList, ArrayList<Integer> quantityList) {
        for (int i = 0; i < toolList.size(); i++) { //add new tools
            OrderLine orderLine = new OrderLine(quantityList.get(i), toolList.get(i));
            shop.getOrderRep().getOrderList().
                    get(shop.getOrderRep().getOrderList().size() - 1).getOrderLineList().add(orderLine);
        }
    }

    /**
     * Updates existing OrderLine objects in an existing Order.
     * @param shop the Shop Object which houses the list of orders.
     * @param toolList the list of tools to be changed the order.
     * @param quantityList the list of quantities of tools to be changed to in the order.
     */
    private void replaceDuplicateOrderLines(Shop shop, ArrayList<Tool> toolList, ArrayList<Integer> quantityList) {
        for (int i = 0; i < toolList.size(); i++) { //replace duplicates
            for (OrderLine orderLine : shop.getOrderRep().getOrderList().
                    get(shop.getOrderRep().getOrderList().size() - 1).getOrderLineList()) {
                if (toolList.size() == 0){
                    break;
                }
                if (toolList.get(i) == orderLine.getTool()) {
                    orderLine.setQuantityOrdered(quantityList.get(i));
                    toolList.remove(toolList.get(i));
                    quantityList.remove(quantityList.get(i));
                }
            }
        }
    }

    /**
     * Creates a new order and adds the order to the list of orders.
     * @param shop the Shop Object which houses the list of orders.
     * @param toolList the list of tools to be changed the order.
     * @param quantityList the list of quantities of tools to be changed to in the order.
     */
    private void createOrder(Shop shop, ArrayList<Tool> toolList, ArrayList<Integer> quantityList) {
        int orderId = shop.getOrderRep().generateOrderId();
        ArrayList<OrderLine> orderLineList = new ArrayList<OrderLine>();
        for (int i = 0; i < toolList.size(); i++) {
            OrderLine orderLine = new OrderLine(quantityList.get(i), toolList.get(i));
            orderLineList.add(orderLine);
        }
        Order order = new Order(orderId, orderLineList);
        shop.getOrderRep().addOrderToList(order);
    }

    /**
     * Retrieves a Tool Object from the itemId found in the argument.
     * @param itemId the itemId of the tool.
     * @return the Tool object if it finds the corresponding ID.  Otherwise, returns null.
     */
    private Tool getAToolInfo(int itemId) {
        for (Tool tool : toolList) {
            if (tool.getItemId() == itemId) {
                return tool;
            }
        }
        return null;
    }

    /**
     * Retrieves a Tool Object's itemId from the toolName found in the argument.
     * @param toolName the toolName of the tool.
     * @return the Tool object's itemId if it finds the corresponding name.  Otherwise, returns null.
     */
    private int searchTheInventory(String toolName) {
        for (Tool tool : toolList) {
            if (tool.getToolName().toLowerCase().contentEquals(toolName)) {
                return tool.getItemId();
            }
        }
        return -1;
    }

    /**
     * Retrieves a Tool Object's itemId from the itemId found in the argument.
     * @param itemId the toolId of the tool.
     * @return the Tool object's itemId if it finds the corresponding Id.  Otherwise, returns null.
     */
    private int searchTheInventory(int itemId) {
        for (Tool tool : toolList) {
            if (itemId == tool.getItemId()) {
                return tool.getItemId();
            }
        }
        return -1;
    }

    /**
     * Retrieves a Tool Object's quantityInStock from the itemId found in the argument.
     * @param itemId the toolId of the tool.
     * @return the Tool object's itemId if it finds the corresponding tool stock.  Otherwise, returns -1.
     */
    private int checkAToolStock(int itemId) {
        for (Tool tool : toolList) {
            if (tool.getItemId() == itemId) {
                return tool.getQuantityInStock();
            }
        }
        return -1;
    }

    /**
     * Checks if the quantity to be sold is a valid quantity.
     * @param itemId the itemId of the Tool to be sold.
     * @param amountSold the quantity of the Tool to be sold.
     * @return true if item quantity to be sold is less or equal to the quantity in stock.  Otherwise, returns false.
     */
    private boolean checkSaleQuantity(int itemId, int amountSold) {
        return checkAToolStock(itemId) >= amountSold;
    }

    //Getter Method
    /**
     * Returns the list of minStock variable to the caller of this function.
     * @return the minStock instance variable.
     */
    public int getMinStock() {
        return minStock;
    }
}


import java.util.ArrayList;

/***
 * This class contains the Shop class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */


public class Shop {
    //Instance variables
    /**
     * The Inventory object that belongs to the Shop object.
     */
    private Inventory inventory;
    /**
     * The OrderRep object that belongs to the Shop object.
     */
    private OrderRep orderRep;
    /**
     * The SupList object that belongs to the Shop object.
     */
    private SupList supplierList;


    //Constructor
    /**
     * Constructs a Shop object with the specified values for inventory, orderRep, and supplierList.
     * The values of the data fields are supplier by the given parameters.
     * @param inventory the Inventory object that belongs to the Shop object.
     * @param orderRep the OrderRep object that belongs to the Shop object.
     * @param supplierList the SupList object that belongs to the Shop object.
     */
    public Shop(Inventory inventory, OrderRep orderRep, SupList supplierList) {
        this.inventory = inventory;
        this.orderRep = orderRep;
        this.supplierList = supplierList;
    }

    //Instance methods
    /**
     * Calls the createOrderOrAppendOrder function in the Inventory object that belongs to the Shop object.
     * @param shop the Shop object
     * @param toolList A list of tools to be added or appended in the order.
     * @param quantityList A list of quantities for the tools to be added or appended in the order.
     * @param fileManager A FileManager object to
     * @return true if the order was created or appended and the order was written to Orders.txt.
     */
    public boolean createOrderOrAppendOrder(Shop shop, ArrayList<Tool> toolList, ArrayList<Integer> quantityList, FileManager fileManager) {
        return inventory.createOrderOrAppendOrder(shop, toolList, quantityList, fileManager);
    }

    /**
     * Calls the getToolPrice function in the Inventory object that belongs to the Shop object.
     * @param itemId the itemId of the Tool to be searched for.
     * @return the price of the Tool.
     */
    public double getToolPrice(int itemId) {
        return inventory.getToolPrice(itemId);
    }

    /**
     * Calls the getToolInfo function in the Inventory object that belongs to the Shop object.
     * @param itemId the itemId of the Tool to be searched for.
     * @return the Tool object.
     */
    public Tool getToolInfo(int itemId) {
        return inventory.getToolInfo(itemId);
    }

    /**
     * Calls the decreaseToolStock function in the Inventory object that belongs to the Shop object.
     * @param itemId the itemId of the Tool to be searched for.
     * @param amountSold the quantity of Tool to be sold for.
     * @return true if the quantity of the item decreased.  Otherwise returns false.
     */
    public boolean decreaseToolStock(int itemId, int amountSold) {
        return inventory.decreaseToolStock(itemId, amountSold);
    }

    /**
     * Calls the getRequiredOrderAmt function in the Inventory object that belongs to the Shop object.
     * @return the quantities needed to be ordered.
     */
    public ArrayList<Integer> getRequiredOrderAmt() {
        return inventory.getRequiredOrderAmt();
    }

    /**
     * Creates a string that contains information on all the suppliers in the supplier list.
     * @return String that contains information on all the suppliers in the supplier list.
     */
    public String supplierListToString() {
        String st = "";
        st += supplierList;
        return st;
    }

    /**
     * Creates a string that contains information on all the orders in the order rep.
     * @return String that contains information on all the orders in the order rep.
     */
    public String orderListToString() {
        String st = "";
        st += orderRep;
        return st;
    }

    /**
     * Calls the checkAllStock function in the Inventory object that belongs to the Shop object.
     * @return the Tool objects that require more quantity.
     */
    public ArrayList<Tool> checkAllStock() {
        return inventory.checkAllStock();
    }

    /**
     * Calls the checkToolStock function in the Inventory object that belongs to the Shop object.
     * @param itemId the itemId of the Tool.
     * @return the quantity of the Tool.
     */
    public int checkToolStock(int itemId) {
        return inventory.checkToolStock(itemId);
    }

    /**
     * Calls the searchInventory function in the Inventory object that belongs to the Shop object.
     * @param toolName the name of the Tool.
     * @return true if the Tool can be found.  Otherwise, returns false.
     */
    public boolean searchInventory(String toolName) {
        return inventory.searchInventory(toolName);
    }

    /**
     * Calls the searchInventory function in the Inventory object that belongs to the Shop object.
     * @param toolId the id of the Tool.
     * @return true if the Tool can be found.  Otherwise, returns false.
     */
    public boolean searchInventory(int toolId) {
        return inventory.searchInventory(toolId);
    }


    //Getters
    /**
     * Returns the Inventory object of the Shop object to the caller of this function.
     * @return the Inventory object that belongs to the Shop object.
     */
    public Inventory getInventory() {
        return inventory;
    }


    /**
     * Returns the orderRep of the Shop object to the caller of this function.
     * @return the OrderRep object that belongs to the Shop object.
     */
    public OrderRep getOrderRep() {
        return orderRep;
    }

}

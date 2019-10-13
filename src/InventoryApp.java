import java.util.ArrayList;
import java.util.Scanner;

/***
 * This class contains the InventoryApp class and its instance methods.
 * This class serves as the frontend of the Inventory Management App.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */


public class InventoryApp {
    /***
     * Scanner object used to get user input during app use.
     */
    private Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        InventoryApp inventoryApp = new InventoryApp();
        FileManager fileManager = new FileManager();
        SupList supList = new SupList(fileManager.loadSupplierListFromDB());
        Inventory inventory = new Inventory(fileManager.loadToolListFromDB(supList));
        OrderRep orderList = new OrderRep();
        Shop shop = new Shop(inventory, orderList, supList);

        inventoryApp.printWelcomeMenu();
        while (true) {
            String selection = inventoryApp.printMainMenu();
            switch (selection) {
                case "1":
                    inventoryApp.printToolListMenu(shop);
                    break;
                case "2":
                    inventoryApp.printSearchToolNameMenu(shop);
                    break;
                case "3":
                    inventoryApp.printSearchToolIDMenu(shop);
                    break;
                case "4":
                    inventoryApp.printCheckItemQuantityMenu(shop);
                    break;
                case "5":
                    inventoryApp.printDecreaseItemQuantityMenu(shop, fileManager);
                    break;
                case "6":
                    inventoryApp.printMakeOrderMenu(shop, fileManager);
                    break;
                case "7":
                    inventoryApp.printCurrentOrderMenu(shop);
                    break;
                case "8":
                    inventoryApp.printSupplierList(shop);
                    break;
                case "9":
                    inventoryApp.printExitMenu();
                    System.exit(0);
                default:
                    inventoryApp.printErrorMenu();
                    break;
            }
        }
    }

    /**
     * Prints out a welcome message.
     */
    private void printWelcomeMenu() {
        System.out.println("Welcome to Michael's Retail Shop Inventory Management App!");
    }

    /**
     * Prints out selection options, prompts for user selection, and returns the user input as a string
     * @return user input as a String
     */
    private String printMainMenu() {
        System.out.println("Please make a selection:");
        System.out.println("1: List all tools");
        System.out.println("2: Search for tool by toolName");
        System.out.println("3: Search for tool by toolID");
        System.out.println("4: Check item quantity");
        System.out.println("5: Decrease item quantity");
        System.out.println("6: Make New Order/Append Today's Order");
        System.out.println("7: View Current Orders");
        System.out.println("8: View Supplier List");
        System.out.println("9: Quit");
        String selection = this.userInput.nextLine();
        return selection;
    }

    /**
     * Prints list of tools in the Inventory object that belongs to the Shop object.
     * @param shop the Shop object.
     */
    private void printToolListMenu(Shop shop) {
        System.out.println(shop.getInventory());
    }

    /**
     * Receives user input and tries to find the tool that corresponds to the user input.
     * @param shop the Shop object.
     */
    private void printSearchToolNameMenu(Shop shop) {
        System.out.println("What is the name of the tool you would like to look up?");
        String toolName = this.userInput.nextLine();
        if (shop.searchInventory(toolName.toLowerCase())) {
            System.out.println(toolName + " is in the shop.");
        } else {
            System.out.println("Tool does not exist or invalid input, please check spelling and try again.");
        }
    }

    /**
     * Receives user input and tries to find the tool that corresponds to the user input.
     * @param shop the Shop object.
     */
    private void printSearchToolIDMenu(Shop shop) {
        System.out.println("What is the ID of the tool you would like to look up?");
        int itemId = receiveNumberInput();
        if (shop.searchInventory(itemId) == true) {
            System.out.println(itemId + " is in the shop.");
        } else {
            System.out.println("Tool does not exist or invalid input, please check spelling and try again.");
        }
    }

    /**
     * Receives user input and tries to find the tool stock corresponding to the user inputted toolId.
     * @param shop the Shop object.
     */
    private void printCheckItemQuantityMenu(Shop shop) {
        System.out.println("What is the ID of the tool you would like to look up?");
        int itemId = receiveNumberInput();
        if (shop.searchInventory(itemId)) {
            System.out.println("Quantity: " + shop.checkToolStock(itemId));
        } else {
            System.out.println("Tool does not exist or invalid input, please check spelling and try again.");
        }
    }

    /**
     * Decreases the inputted item's quantities and calls for the an order to be made when the tool stock goes below
     * a certain value.
     * @param shop the Shop object.
     * @param fileManager the FileManager object used to read/write files.
     */
    private void printDecreaseItemQuantityMenu(Shop shop, FileManager fileManager) {
        ArrayList<Tool> toolList = new ArrayList<Tool>();
        String selection = "Y";
        toolList = sellTools(shop, toolList, selection);
        createAutoOrder(shop, fileManager, toolList);
    }

    /**
     * Creates order automatically when decreasing a item's quantity.
     * @param shop the Shop object.
     * @param fileManager the FileManager object used to read/write files.
     * @param toolList the list of Tool objects to be added to the order.
     */
    private void createAutoOrder(Shop shop, FileManager fileManager, ArrayList<Tool> toolList) {
        ArrayList<Integer> quantityList;
        if (toolList.size() != 0) {
            System.out.println("An item(s) quantity is less than " + shop.getInventory().getMinStock() + " items," +
                    " automatically creating new order lines and/or order items...");
            quantityList = shop.getRequiredOrderAmt();
            if (shop.createOrderOrAppendOrder(shop, toolList, quantityList, fileManager)) {
                System.out.println("Successfully added and/or appended order to orders.txt!");
            } else {
                System.out.println("ERROR: Could not edit orders.txt.");
            }
        }
    }

    /**
     * Receives input from the user and tries to sell some tools that correspond to the inputted tool's Id and quantity.
     * @param shop the Shop object.
     * @param toolList the list of Tool objects to be sold (initially empty).
     * @param selection the user input used to determine if the user would like to continue selling tools.
     * @return returns the list of tools to be sold.
     */
    private ArrayList<Tool> sellTools(Shop shop, ArrayList<Tool> toolList, String selection) {
        while (selection.contentEquals("Y")) {
            System.out.println("What is the tool ID you would like to order?");
            int toolId = receiveNumberInput();
            System.out.println("How many?");
            int quantity = receiveNumberInput();
            if (checkValidQuantity(quantity)){
                CheckToolSale(shop, toolId, quantity);
                toolList = shop.checkAllStock();
            }
            else {
                System.out.println("Quantity entered can not be a negative number.");
            }
            System.out.println("Would you like to add more? " +
                    "Press Y or hit any other key then press enter to continue.");
            selection = userInput.nextLine();
        }
        return toolList;
    }

    /**
     * Checks to ensure that the user does not try to sell or make an order of item quantity less than 0.
     * @param quantity the inputted quantity of a item.
     * @return true if it is a positive number.  Otherwise, returns false.
     */
    private boolean checkValidQuantity (int quantity){
        if (quantity >= 0){
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the tool can be sold for the quantity entered.
     * @param shop the Shop object.
     * @param toolId the Id of the Tool object.
     * @param quantity the quantity of Tool object to be decreased by.
     */
    private void CheckToolSale(Shop shop, int toolId, int quantity) {
        if (shop.decreaseToolStock(toolId, quantity)) {
            System.out.println("Successfully sold item for: $" + shop.getToolPrice(toolId)
                    * quantity);
        } else if (shop.searchInventory(toolId)) {
            System.out.println("Cannot sell more than what is currently in stock.");
        } else {
            System.out.println("Invalid toolId inputted.");
        }
    }

    /**
     * Prints the list of suppliers to the console.
     * @param shop the Shop object.
     */
    private void printSupplierList(Shop shop) {
        System.out.println(shop.supplierListToString());
    }

    /**
     * Receives input from the user for tools and their quantities to be ordered and creates the order
     * or append the order depending on the date of the last order, and then writes the order to order.txt.
     * @param shop the Shop object.
     * @param fileManager the FileManager object used to read/write files.
     */
    private void printMakeOrderMenu(Shop shop, FileManager fileManager) {
        ArrayList<Tool> toolList = new ArrayList<Tool>();
        ArrayList<Integer> quantityList = new ArrayList<Integer>();
        String selection = "Y";
        getOrderInputs(shop, toolList, quantityList, selection);
        createOrder(shop, fileManager, toolList, quantityList);
    }

    /**
     * Creates order using the inputted parameters.
     * @param shop the Shop object.
     * @param fileManager the FileManager object used to read/write files.
     * @param toolList the list of tools to be added to the order.
     * @param quantityList the list of quantities of the tools to be added to the order.
     */
    private void createOrder(Shop shop, FileManager fileManager, ArrayList<Tool> toolList,
                             ArrayList<Integer> quantityList) {
        if (toolList.size() != 0) {
            if (shop.createOrderOrAppendOrder(shop, toolList, quantityList, fileManager)) {
                System.out.println("Successfully added and/or appended order to orders.txt!");
            } else {
                System.out.println("ERROR: Could not edit orders.txt.");
            }
        }
    }

    /**
     * Receives input from the user for tools and their quantities to be ordered and creates the order
     * or append the order depending on the date of the last order, and then writes the order to order.txt.
     * @param shop the Shop object.
     * @param toolList the list of tools to be added to the order.
     * @param quantityList the list of quantities of the tools to be added to the order.
     * @param selection the user input used to determine if the user would like to continue ordering tools.
     */
    private void getOrderInputs(Shop shop, ArrayList<Tool> toolList, ArrayList<Integer> quantityList, String selection) {
        while (selection.contentEquals("Y")) {
            System.out.println("What is the tool ID you would like to order?");
            int toolId = receiveNumberInput();
            System.out.println("How many?");
            int quantity = receiveNumberInput();
            if (shop.searchInventory(toolId) && checkValidQuantity(quantity)) {
                toolList.add(shop.getToolInfo(toolId));
                quantityList.add(quantity);
            } else {
                System.out.println("Invalid toolId inputted or invalid quantity entered.");
            }
            System.out.println("Would you like to add more? Press Y or hit any other key then press enter to continue.");
            selection = userInput.nextLine();
        }
    }

    /**
     * Prints the order list to the console.
     * @param shop the Shop object.
     */
    private void printCurrentOrderMenu(Shop shop) {
        System.out.println(shop.orderListToString());
    }

    /**
     * Prints exit message to console.
     */
    private void printExitMenu() {
        System.out.println("Goodbye!  Thanks for using the app!");
    }

    /**
     * Prints error message to the console.
     */
    private void printErrorMenu() {
        System.out.println("Invalid input.  Please check inputs and try again.");
    }

    /**
     * Prompts the user to enter a number and continue prompting if the user does not enter a valid number.
     * @return the number entered by the user.
     */
    private int receiveNumberInput() {
        int number;
        while (true) {
            try {
                String temp = userInput.nextLine();
                number = Integer.parseInt(temp);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input.  Please enter a valid number.");
                continue;
            }

        }
        return number;
    }
}

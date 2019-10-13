import java.lang.reflect.Field;

/***
 * This class contains the Tool class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */


public class Tool {
    ////////Instance Variables
    private int itemId;
    private String toolName;
    private int quantityInStock;
    private double price;
    private int supplierId;
    private Supplier supplier;

    ////////Constructors
    public Tool(int itemId, String toolName, int quantityInStock, double price, int supplierId, Supplier supplier) {
        this.itemId = itemId;
        this.toolName = toolName;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.supplierId = supplierId;
        this.supplier = supplier;
    }

    ////////Instance Methods
    @Override
    public String toString() {
        String st = "";
        for (Field f : getClass().getDeclaredFields()) {
            st += f.getName() + ": ";
            try {
                st += f.get(this) + "\n";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return st;
    }

    ////////Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}

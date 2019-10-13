/***
 * This class contains the OrderLine class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */

public class OrderLine {
    ////////Instance Variables
    /**
     * The quantity of the Tool to be ordered.
     */
    private int quantityOrdered;
    /**
     * The Tool object to be ordered.
     */
    private Tool tool;

    ////////Constructors
    /**
     * Constructs a OrderLine object with the specified values for quantityOrdered and tool.
     * The values of the data fields are supplier by the given parameters.
     * @param quantityOrdered the quantity of the Tool to be ordered.
     * @param tool the Tool object to be ordered.
     */
    public OrderLine(int quantityOrdered, Tool tool) {
        this.quantityOrdered = quantityOrdered;
        this.tool = tool;
    }

    ////////Instance Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String st = "Item Description:\t" + tool.getToolName() + "\n";
        st += "Amount ordered:  \t" + quantityOrdered + "\n";
        st += "Supplier:        \t" + tool.getSupplier().getCompanyName() + "\n";
        return st;
    }

    ////////Getters and Setters
    /**
     * Sets the quantityOrdered variable of the orderLine object.
     * @param quantityOrdered the quantity of Tool to be ordered.
     */
    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    /**
     * Returns the Tool object to the caller of this function.
     * @return the Tool object.
     */
    public Tool getTool() {
        return tool;
    }

}

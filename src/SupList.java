import java.util.LinkedHashSet;

/***
 * This class contains the SupList class and its instance methods.
 * This class contains an LinkedHashSet of all the suppliers.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */

public class SupList {
    /**
     * A list of all the suppliers.
     */
    private LinkedHashSet<Supplier> supplierList;

    /**
     * Constructs a SupList object with the specified values for supplierList.
     * The values of the data fields are supplier by the given parameters.
     * @param supplierList A list of all the suppliers.
     */
    public SupList(LinkedHashSet<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String st = "";
        for (Supplier supplier : supplierList) {
            st += supplier + "\n";
        }
        return st;
    }

    /**
     * Returns the supplierList of the SupList object to the caller of this function.
     * @return A list of all the suppliers.
     */
    public LinkedHashSet<Supplier> getSupplierList() {
        return supplierList;
    }
}

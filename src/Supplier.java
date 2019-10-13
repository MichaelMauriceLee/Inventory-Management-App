import java.lang.reflect.Field;

/***
 * This class contains the Supplier class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */

public class Supplier {
    ////////Instance Variables
    /**
     * The unique ID assigned to the Supplier object.
     */
    private int supplierId;
    /**
     * The name of the company that belongs to the Supplier object.
     */
    private String companyName;
    /**
     * The address that belongs to the Supplier object.
     */
    private String address;
    /**
     * The name of the sales contact of the Supplier object.
     */
    private String salesContact;

    ////////Constructors
    /**
     * Constructs a Supplier object with the specified values for supplierId, companyName, address, and salesContact.
     * The values of the data fields are supplier by the given parameters.
     * @param supplierId the Supplier object's unique ID.
     * @param companyName the Supplier object's company name.
     * @param address the real world address of the Supplier object.
     * @param salesContact the name of the sales contact of the Supplier object.
     */
    public Supplier(int supplierId, String companyName, String address, String salesContact) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.address = address;
        this.salesContact = salesContact;
    }

    //Instance Methods
    /**
     * {@inheritDoc}
     */
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
    /**
     * Returns the supplierId of the Supplier object to the caller of this function.
     * @return the Supplier object's unique ID.
     */
    public int getSupplierId() {
        return supplierId;
    }

    /**
     * Returns the companyName of the Supplier object to the caller of this function.
     * @return the Supplier object's company name.
     */
    public String getCompanyName() {
        return companyName;
    }

}

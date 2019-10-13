import java.time.LocalDate;
import java.util.ArrayList;

/***
 * This class contains the Order class and its instance methods.
 * This contains a list of OrderLine objects.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
*/

public class Order {
    ////////Instance Variables
    /**
     * The unique ID of the order.
     */
    private int orderId;
    /**
     * The date when the order was created.
     */
    private LocalDate date;
    /**
     * A list of orderLine objects.
     */
    private ArrayList<OrderLine> orderLineList;

    ////////Constructors

    /**
     * Constructs a OrderLine object with the specified values for id and orderLineList.
     * The values of the data fields are supplier by the given parameters.
     * @param id the unique Id of the order.
     * @param orderLineList a list of order items.
     */
    public Order(int id, ArrayList<OrderLine> orderLineList){
        this.orderId = id;
        this.date = LocalDate.now(); // Create a date object
        this.orderLineList = orderLineList;
    }

    //Instance Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        String st = "********************************************************************\n";
        st +="ORDER ID:\t" + orderId+ "\n";
        st +="Date Ordered:  \t" + date + "\n\n";
        for (OrderLine item: orderLineList){
            st += (item + "\n");
        }
        st += "********************************************************************\n";
        return st;
    }


    ////////Getters and Setters
    /**
     * Returns the orderId variable to the caller of this function.
     * @return the orderId that belongs to the Order object.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Returns the LocalDate object to the caller of this function.
     * @return the LocalDate that belongs to the Order object.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the list of OrderLine object to the caller of this function.
     * @return the list of OrderLine objects.
     */
    public ArrayList<OrderLine> getOrderLineList() {
        return orderLineList;
    }
}

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/***
 * This class contains the OrderRep class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/3/2019
 */

public class OrderRep {
    //Instance Variables
    /**
     * A list of all orders.
     */
    private ArrayList<Order> orderList;

    /**
     * Constructs a OrderRep object.
     * This is the default constructor (There are no orders made before the app is launched).
     */
    public OrderRep() {
        this.orderList = new ArrayList<>();
    }

    //Instance Methods
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String st = "";
        if (orderList.size() != 0) {
            for (Order order : orderList) {
                st += order + "\n";
            }
        } else {
            st += "No current orders\n";
        }
        return st;
    }


    /**
     * Checks if the last order made was made today.
     * If there are no orders, this returns true.
     * @return true if the last order was made today or there are no orders.  Otherwise return false.
     */
    public boolean checkTodayOrder() {
        if (orderList.size() == 0)
            return true;
        return orderList.get(orderList.size() - 1).getDate() == LocalDate.now();
    }

    /**
     * Generates a unique ID for an Order object.
     * @return a unique int.
     */
    public int generateOrderId() {
        int id = ThreadLocalRandom.current().nextInt(10000, 99999);
        while (checkNonUniqueId(id, orderList)) {
            id = ThreadLocalRandom.current().nextInt(10000, 99999);
        }
        return id;
    }

    /**
     * Adds an order to the orderList.
     * @param order Object order to be added to the list.
     */
    public void addOrderToList(Order order) {
        orderList.add(order);
    }

    /**
     * Returns the orderList variable to the caller of this function.
     * @return the list of orders.
     */
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    //Helper Methods
    /**
     * Checks to see if an order ID is unique.
     * @param id the ID generated for an order.
     * @param orderList the list of Order objects.
     * @return true if the ID is unique.  Otherwise returns false.
     */
    private boolean checkNonUniqueId(int id, ArrayList<Order> orderList) {
        for (Order order : orderList) {
            if (id == order.getOrderId()) {
                return true;
            }
        }
        return false;
    }
}


package cz2002.entity;

import java.util.ArrayList;
/**
 * Customer class
 * @author Tran Trung Dung
 * @version 1.0
 * @since 2020-11-02
 */
public class SaleRevenue{
    /**
     * List of Order included in this Sale Revenue
     */
    private ArrayList<Order> orderList;
    /**
     * Total Price of included orders
     */
    private double totalPrice;

    /**
     * Constructor of this class
     * @param orderList List of Order included in this Sale Revenue
     * @param totalPrice Total price of included orders
     */
    public SaleRevenue(ArrayList<Order> orderList,double totalPrice) {
        this.orderList = orderList;
        this.totalPrice = totalPrice;
    }

    /**
     * This method is to get List of Order included in this Sale Revenue
     * @return List of Order included in this Sale Revenue
     */
    public ArrayList<Order> getOrderList(){
        return orderList;
    }

    /**
     * This method is to get total price of included orders
     * @return Total price of included orders
     */
    public double getTotalPrice() {
        return totalPrice;
    }
}

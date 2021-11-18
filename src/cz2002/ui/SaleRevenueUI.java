package cz2002.ui;

import cz2002.entity.MenuItem;
import cz2002.entity.Order;
import cz2002.entity.SaleRevenue;
import cz2002.system.SaleRevenueSystem;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Scanner;

/**
 *     User Interface for SaleRevenueSystem
 *     @author Abdul Siddiq
 *     @version 1.0
 *     @since 2021-11-08
 */

public class SaleRevenueUI {
    /**
        Scanner
     */
    private Scanner sc;

    /**
         Sale Revenue System
     */
    private SaleRevenueSystem saleRevenueSystem;

    /**
     * Creates a SaleRevenueUI that interfaces with SaleRevenueSystem to perform its functionality.
     * @param saleRevenueSystem Sale Revenue System
     * @param scanner Scanner object.
     */
    public SaleRevenueUI(SaleRevenueSystem saleRevenueSystem, Scanner scanner) {
        this.saleRevenueSystem = saleRevenueSystem;
        sc = scanner;
    }

    /**
     * Runs 'Print Sale Revenue' routine
     */
    public void printSaleRevenueReport() {
        LocalDate startDate = promptDate("Please enter start period");
        LocalDate endDate = promptDate("Please enter end period");

        if(startDate.isAfter(endDate)) {
            System.out.println("Start Date cannot be later than End Date");
            return;
        }

        System.out.println("=".repeat(30));
        System.out.println("\nSale Revenue Report\n");
        System.out.printf("Start Period: %s\n", startDate.toString());
        System.out.printf("End Period: %s\n", endDate.toString());

        printSaleRevenue(saleRevenueSystem.generateSaleRevenueRep(startDate, endDate));
    }

    /**
     * Prints Sale Revenue
     * @param saleRevenue SaleRevenue object to print
     */
    public void printSaleRevenue(SaleRevenue saleRevenue) {
        var orderList = saleRevenue.getOrderList();

        if (saleRevenue.getTotalPrice() != 0)
        {
        	for(Order order : orderList) {
                printOrder(order);
            }

            System.out.printf("\nTotal Revenue: $%.2f", saleRevenue.getTotalPrice());
        }
        else
        {
        	System.out.println("No orders have been completed within this period");
        }
    }

    /**
     * Prints Order
     * @param order Order object to print
     */
    private void printOrder(Order order) {
        System.out.printf("[%s] Order #%-2d - $%.2f\n", order.getStart(), order.getID(), order.getTotalPayable());
        for(MenuItem menuItem : order.getPackItems())
            printMenuItem(menuItem);

        for(MenuItem menuItem : order.getDishItems())
            printMenuItem(menuItem);
    }

    /**
     * Prints Menu Item
     * @param menuItem MenuItem object to print
     */
    private void printMenuItem(MenuItem menuItem) {
        System.out.printf(" - %-10s\n", menuItem.getName());
    }

    /**
     * Request user to key in Date
     * @param prompt Prompt to print out when requesting
     * @return Date entered
     */
    private LocalDate promptDate(String prompt) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        while(true) {
            try {
                System.out.printf("%s (dd/MM/yyyy): ", prompt);
                return LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.SMART));
            } catch (Exception e) {
                System.out.println("You have entered an invalid date");
            }
        }
    }
}

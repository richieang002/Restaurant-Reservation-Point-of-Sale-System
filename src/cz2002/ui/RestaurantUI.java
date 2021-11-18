package cz2002.ui;

import cz2002.system.ReservationSystem;
import cz2002.system.TableSystem;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *     User Interface for Restaurant
 *     @author Abdul Siddiq
 *     @version 1.0
 *     @since 2021-11-08
 */
public class RestaurantUI {

    /**
     * Scanner
     */
    private Scanner sc;

    /**
     * Reservation System
     */
    private ReservationSystem reservationSystem;

    /**
     * Table System
     */
    private TableSystem tableSystem;

    /**
     * Creates a RestaurantUI that interfaces with Reservation System and Table System to perform its functionality.
     * @param reservationSystem Reservation System
     * @param tableSystem Table System
     * @param scanner Scanner
     */
    public RestaurantUI(ReservationSystem reservationSystem, TableSystem tableSystem, Scanner scanner) {
        this.reservationSystem = reservationSystem;
        this.tableSystem = tableSystem;
        sc = scanner;
    }

    /**
     * Checks for vacant table and prints out the upcoming reservation time for the day if any
     */
    public void checkTableAvailability() {
        var vacantTables = tableSystem.getAvailableTables();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("The current time is %s\n", LocalDateTime.now().format(formatter));
        System.out.println("The following tables are currently available:");

        for(var table : vacantTables) {
            LocalTime upcomingReservation = reservationSystem.getUpcomingReservation(table.getTableNo());

            System.out.printf("  - Table #%-2d for %d pax", table.getTableNo(), table.getCapacity());

            if(upcomingReservation != null) {
                System.out.printf(" Available until %s", upcomingReservation.toString());
            }

            System.out.println();
        }

        System.out.println();
    }
}

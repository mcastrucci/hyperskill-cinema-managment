import java.util.Scanner;

public class CinemaFifth {

    private static int rows = 0;
    private static int seats = 0;
    private static char[][] cinemaDraw;
    private static Scanner scanner = new Scanner(System.in);
    private static final String[] MENU = {"1. Show the seats", "2. Buy a ticket", "3. Statistics", "0. Exit"};
    private static int totalIncome = 0;
    private static int currentIncome = 0;
    private static int numberOfPurchasedTickets = 0;
    private static double persantajeOfTicketsSold = 0;


    public static void main(String[] args) {

        // we call configure cinema to configure number of columns and rows
        configureCinema();

        // a loop that will check every time for user input
        int option = 0;
        while((option = showMenu()) != 0) {
            switch(option) {
                case 1: // show default cinema configuration
                    displayCinemaSeats(cinemaDraw);
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    showStatistics();
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    /*
        it ask the user for a seat and a row to buy a ticket
        it also display the total value of the seat
     */
    private static void buyTicket() {
        System.out.println("Enter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();
        seat = seat; // we dont change seat to -- since first column is row number

        // if input is higher than max rows / columns or less than 1
        if ((seat > seats || row -1 >= rows)
                || seat < 1 || row < 1) {
            System.out.println("Wrong input!");
            buyTicket();
        // if seat is already purchased, we need to display an error
        } else if (cinemaDraw[row - 1][seat] == 'B') {
            System.out.println("That ticket has already been purchased!");
            buyTicket();
        } else {
            // first half of rows, price is 10, else is 8
            int seatValue = rows * seats <= 60 ? 10 : row <= (int)Math.floor((double)(rows)/2) ? 10 : 8;
            updateStatistics(seatValue);
            System.out.println("Ticket price: $" + seatValue);

            /*
            //now we create a copy of cinemaDraw
            char[][] boughtSeat = new char[rows][seats];
            System.arraycopy(cinemaDraw, 0, boughtSeat, 0, boughtSeat.length);

            boughtSeat[row - 1][seat] = 'B'; // seat index is not changed since first column is row numbers

            displayCinemaSeats(boughtSeat);
            */
                cinemaDraw[row - 1][seat] = 'B'; // seat index is not changed since first column is row numbers
                displayCinemaSeats(cinemaDraw);
        }
    }

    private static int showMenu() {
        for (String option : MENU) {
            System.out.println(option);
        }
        return scanner.nextInt();
    }

    /*
        It configures the cinema by reading from user the number of rows and columns.
        It also set the total income of the cinema if all seats are sold
    */
    private static void configureCinema() {
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

        // this is just to use an array of chars, maybe not the best solution
        cinemaDraw = new char[rows][seats + 1]; // +1 since we show row number in first column

        for (int j = 0; j < cinemaDraw.length; j++) {
            for (int q = 0; q < cinemaDraw[j].length; q++) {
                if (q == 0) {
                    cinemaDraw[j][q]= Integer.toString(j + 1).charAt(0); // hack to convert int to char keeping the number
                } else {
                    cinemaDraw[j][q]= 'S';
                    // first half of rows, price is 10, else is 8
                    int seatValue = rows * seats <= 60 ? 10 : j + 1 <= (int)Math.floor((double)(rows)/2) ? 10 : 8;
                    setTotalIncome(getTotalIncome() + seatValue);
                }
                // first half of rows, price is 10, else is 8
                int seatValue = rows * seats <= 60 ? 10 : j <= (int)Math.floor((double)(rows)/2) ? 10 : 8;
                setTotalIncome(getTotalIncome() + seatValue);
            }
        }
    }

    /*
        It returns a printout of the cinema seats
        @Param output : Is the matrix of chars that represent the cinema seats
    */
    private static void displayCinemaSeats(char[][] output) {
        // we loop through seats to show the first line that represent the columns
        String seatsString = "";
        for (int i = 0; i < seats; i++) {
            seatsString += i+1 + " ";
        }
        // after that, we remove the last space from the string
        seatsString = seatsString.trim();

        System.out.println("Cinema:");
        System.out.println("  " + seatsString); // add the leading space that requires the program

        for (char [] line: output) {
            String outputString = "";
            for (char c: line) {
                outputString += Character.toString(c) + " "; // some formating
            }
            System.out.println(outputString);
        }
    }

    /*
        It show the current statistics to the user
     */
    private static void showStatistics() {
        System.out.printf("Number of purchased tickets: %d", getNumberOfPurchasedTickets());
        String percentage = String.format("%.2f", getPersantajeOfTicketsSold());
        System.out.println("\nPercentage: " + percentage + "%");
        System.out.printf("Current income: $%d", getCurrentIncome());
        System.out.printf("\nTotal income: $%d\n", getTotalIncome());
    }

    /*
        It updates the statistics after any cinema event
     */
    private static void updateStatistics (int price) {
        setNumberOfPurchasedTickets(getNumberOfPurchasedTickets() + 1);
        setCurrentIncome(getCurrentIncome() + price);
        double totalSeats = rows * seats;

        setPersantajeOfTicketsSold(((double)(getNumberOfPurchasedTickets() * 100) / totalSeats));
    }


    public static void setCurrentIncome(int currentIncome) {
        CinemaFifth.currentIncome = currentIncome;
    }

    public static void setPersantajeOfTicketsSold(double persantajeOfTicketsSold) {
        CinemaFifth.persantajeOfTicketsSold = persantajeOfTicketsSold;
    }

    public static void setTotalIncome(int totalIncome) {
        CinemaFifth.totalIncome = totalIncome;
    }

    public static void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        CinemaFifth.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }

    public static double getPersantajeOfTicketsSold() {
        return persantajeOfTicketsSold;
    }

    public static int getTotalIncome() {
        return totalIncome;
    }

    public static int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }

    public static int getCurrentIncome() {
        return currentIncome;
    }
}
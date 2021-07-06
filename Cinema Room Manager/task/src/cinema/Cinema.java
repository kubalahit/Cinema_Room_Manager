package cinema;

import java.util.Scanner;

public class Cinema {

    int rows;
    int seatsInRow;
    int seats;
    int totalIncome;
    int currentIncome;
    int purchasedTickets;
    double purchasedTicketsPercentage;
    boolean[][] seatsReservation;
    boolean isRunning;

    public Cinema(int rows, int seatsInRow){
        this.isRunning = true;
        this.rows = rows;
        this.seatsInRow = seatsInRow;
        this.seats = this.seatsInRow * this.rows;
        this.seatsReservation = new boolean[rows][seatsInRow];
        this.purchasedTickets = 0;
        this.purchasedTicketsPercentage = 0;
        this.currentIncome = 0;
        if (this.seats <= 60) this.totalIncome = this.seats * 10;
        else if (this.rows % 2 == 0) this.totalIncome = this.seats/2 * (10 + 8);
        else this.totalIncome = (Math.floorDiv(this.rows, 2) + 1) * this.seatsInRow * 8
                    + Math.floorDiv(this.rows, 2) * this.seatsInRow * 10;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        var rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        var seatsInRow = scanner.nextInt();
        Cinema cinema = new Cinema(rows, seatsInRow);
        while(cinema.isRunning){
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            switch (scanner.nextInt()){
                case 1:
                    cinema.printCinemaTable();
                    break;
                case 2:
                    cinema.reservationManagement();
                    break;
                case 3:
                    cinema.showStatistics();
                    break;
                case 0:
                    cinema.isRunning = false;
                    break;
            }
        }
    }

    public int checkTicketPrice(int row){
        if (this.seats <= 60) return 10;
        else if (this.rows % 2 == 0 && (this.rows / 2) >= row) return 10;
        else if (this.rows % 2 == 0 && (this.rows / 2) < row) return 8;
        else if (this.rows % 2 == 1 && (this.rows / 2) >= row) return 10;
        else return 8;
    }

    public void printCinemaTable(){
        System.out.println("Cinema: \n");
        for(int i = 0; i <= this.rows; i++){
            if (i == 0) {
                for (int j = 0; j <= this.seatsInRow; j++)
                    if (j == 0) System.out.print(" ");
                    else System.out.print(" " + j);
            }
            else {
                for (int j = 0; j <= this.seatsInRow; j++){
                    if (j == 0) System.out.print(i);
                    else if (!this.seatsReservation[i - 1][j - 1]) System.out.print(" S");
                    else System.out.print(" B");
                }
            }
            System.out.println();
        }
    }

    public void reservationManagement(){
        Scanner scanner = new Scanner(System.in);
        int row;
        int seat;
        while(true) {
            System.out.println("Enter a row number:");
            row = scanner.nextInt();
            if (row > this.rows || row < 1){
                System.out.println("Wrong input!");
                continue;
            }
            System.out.println("Enter a seat number in that row:");
            seat = scanner.nextInt();
            if (seat > this.seatsInRow || seat < 1){
                System.out.println("Wrong input!");
                continue;
            }
            if (this.seatsReservation[row - 1][seat - 1]){
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            break;
        }
        this.seatsReservation[row - 1][seat - 1] = true;
        var ticketPrice = this.checkTicketPrice(row);
        System.out.println("Ticket price: $" + ticketPrice);
        this.purchasedTickets ++;
        this.purchasedTicketsPercentage = (double)this.purchasedTickets / (double)this.seats * 100;
        this.currentIncome += ticketPrice;

    }

    public void showStatistics(){
        System.out.println("Number of purchased tickets: " + this.purchasedTickets);
        System.out.print("Percentage: ");
        System.out.printf("%.2f", this.purchasedTicketsPercentage);
        System.out.println("%");
        System.out.println("Current income: $" + this.currentIncome);
        System.out.println("Total income: $" + totalIncome);

    }
}
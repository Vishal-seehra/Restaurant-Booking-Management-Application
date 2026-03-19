import services.*;

public class Main {
    
    public static void main(String[] args) throws Exception {

        TableManager tableManager = new TableManager();
        ReservationManager reservationManager = new ReservationManager(tableManager);

        ReservationServer.startServer(reservationManager);
    } 
}
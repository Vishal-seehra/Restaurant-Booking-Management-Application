package models;

public class Reservation {
    private String firstName;
    private String lastName;
    private String time;
    private int numberofguests;
    private int tableId;

    public Reservation(String firstName, String lastName, int numberofguests, String time, int tableId) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.numberofguests = numberofguests;
        this.time = time;
        this.tableId = tableId;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getNumberofguests() { return numberofguests; }
    public String getTime() { return time; }
    public int getTableId() { return tableId; }

    @Override
    public String toString() {
        return "Reservation [Name=" + firstName + ", Guests=" + numberofguests + 
               ", Time=" + time + ", TableId=" + tableId + "]";
    }
}
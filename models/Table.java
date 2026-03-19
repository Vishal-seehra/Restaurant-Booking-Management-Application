package models;
import java.util.*;

public class Table{
    private int tableId;
    private int size;
    private boolean isReserved;

    public Table(int tableId,int size){
        this.tableId=tableId;
        this.size=size;
        this.isReserved=false;
    }

    public int getTableId(){
        return tableId;
    }
    public int getSize(){
        return size;
    }
    public boolean isReserved(){
        return isReserved;
    }

    public void setReserved(boolean Reserved){
        isReserved = Reserved;
    }
}
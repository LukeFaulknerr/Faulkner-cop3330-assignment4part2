package ucf.assignments;

import java.util.ArrayList;

public class TDList {
    private ArrayList<TDItem> TDItems;
    private String title;

    public ArrayList<TDItem> getTDItems(){
        return TDItems;
    }

    public String getTitle(){
        return title;
    }

    public void setTDItems(ArrayList<TDItem> newList){
        TDItems = newList;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }
}

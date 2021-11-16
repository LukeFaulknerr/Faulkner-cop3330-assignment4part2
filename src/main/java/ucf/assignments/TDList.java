/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Luke Faulkner
 */

package ucf.assignments;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class TDList implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<TDItem> TDItems;
    private final String title;

    public TDList (ArrayList<TDItem> a, String t) {
        TDItems = a;
        title = t;
    }

    public ArrayList<TDItem> getTDItems(){
        return TDItems;
    }

    public String getTitle(){
        return title;
    }

}

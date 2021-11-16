/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Luke Faulkner
 */

package ucf.assignments;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TDListEdit {
    public static void addTDList(ObservableList<TDList> TDLists, String name){
        TDLists.addAll(new TDList(new ArrayList<>(), name));
    }

    public static void clearTDList(TDList ToDoList){
        ToDoList.getTDItems().clear();
    }

    public static Boolean getCompleteAsBoolean(String s){
        return s.equals("true");
    }
}

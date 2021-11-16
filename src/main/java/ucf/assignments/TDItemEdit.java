/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Luke Faulkner
 */

package ucf.assignments;


public class TDItemEdit {
    public static void addTDItem(TDItem ToDoItem, TDList ToDoList){
        ToDoList.getTDItems().add(ToDoItem);
    }

    public static void removeTDItem(TDList ToDoList, TDItem removed){
        ToDoList.getTDItems().remove(removed);
    }

    public static void editDescription(TDItem Item, String newDesc){
        Item.setDesc(newDesc);
    }

}

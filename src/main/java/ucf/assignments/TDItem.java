/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Luke Faulkner
 */

package ucf.assignments;

import java.io.Serial;
import java.io.Serializable;

public class TDItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String desc;
    private String date;
    private Boolean complete;

    public TDItem(String de, String da, Boolean b){
        desc = de;
        setDate(da.substring(0, 4), da.substring(5,7), da.substring(8));
        complete = b;
    }
    public String getDesc(){
        return desc;
    }

    public String getDate(){
        return date;
    }

    public Boolean getComplete(){
        return complete;
    }

    public String getCompleteAsString(){
        if (complete) {
            return ("Complete");
        }
        else
            return ("Incomplete");
    }

    public void setDesc(String newDesc){
        newDesc = newDesc.substring(0, Math.min(256, newDesc.length()));    // Sets the description length to be between 1 and 256
        desc = newDesc;
    }

    public void setDate(String year, String month, String day){
        int m = Integer.parseInt(month);
        System.out.println(month);
        int d = Integer.parseInt(day);
        String updateM1 = Integer.toString(m + 1);
        String updateM2 = "0" + (m + 1);

        // Checking if the date is valid, if not, set to the next available date
        // February has a special case due to its day amount
        if (m == 2 && d > 28) {
            month = updateM2;
            day = "01";
        }
        // Case for months with 30 days
        else if ((m == 4 || m == 6 || m == 9 || m == 11) && d > 30){
            if (Integer.parseInt(month) < 10){
                month = updateM2;
            }
            else {
                month = updateM1;
            }
            day = "01";
        }
        // Case for months with 31 days
        else if (((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && d > 31) || m > 12){
            // Special case for december as the year must go up one
            if (Integer.parseInt(month) >= 12) {
                year = Integer.toString(Integer.parseInt(year) + 1);
                month = "01";
            }
            // Case for months that are not december
            else {
                if (m < 10) month = "0" + (Integer.parseInt(month) + 1);
                else {
                    month = Integer.toString(Integer.parseInt(month) + 1);
                }
            }
            day= "01";
        }

        // Set day
        date = year +"-" + month + "-" + day;
    }

    public void setComplete(Boolean newComplete){
        complete = newComplete;
    }

}

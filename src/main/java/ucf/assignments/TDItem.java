package ucf.assignments;

public class TDItem {
    private String desc;
    private String date;
    private Boolean complete;

    public String getDesc(){
        return desc;
    }

    public String getDate(){
        return date;
    }

    public Boolean getComplete(){
        return complete;
    }

    public void setDesc(String newDesc){
        desc = newDesc;
    }

    public void setDate(String newDate){
        date = newDate;
    }

    public void setComplete(Boolean newComplete){
        complete = newComplete;
    }
}

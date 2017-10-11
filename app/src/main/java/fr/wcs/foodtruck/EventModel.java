package fr.wcs.foodtruck;

public class EventModel {

    //Attributes

    private String eid,name,details;
    private String date;

    //Constructors

    public EventModel (){

    }

    public EventModel(String eid, String name, String details, String date) {
        this.eid = eid;  //Primary Key and key
        this.name = name;
        this.details = details;
        this.date = date;
    }

    //Getters

    public String getEid() {
        return eid;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getDate() { return date; }

    //Setters

    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDate(String date) { this.date = date; }


}

package fr.wcs.foodtruck.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class EventModel implements Parcelable {

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

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    protected EventModel(Parcel in) {
        eid = in.readString();
        name = in.readString();
        details = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(eid);
        parcel.writeString(name);
        parcel.writeString(details);
        parcel.writeString(date);
    }
}

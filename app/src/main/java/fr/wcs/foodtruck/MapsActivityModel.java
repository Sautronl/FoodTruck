package fr.wcs.foodtruck;

/**
 * Created by apprenti on 20/10/17.
 */

public class MapsActivityModel {
    public Double lat;
    public Double lon;


    public MapsActivityModel(String key, String s){}


    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public MapsActivityModel(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;

    }
}


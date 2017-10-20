package fr.wcs.foodtruck;

/**
 * Created by apprenti on 18/10/17.
 */

public class GeocoderModel {
    public Double lat;
    public Double lon;
    public String adrs;

    public GeocoderModel(){}


    public GeocoderModel(Double lat, Double lon, String adrs) {
        this.lat = lat;
        this.lon = lon;
        this.adrs = adrs;
    }

    public String getAdrs() {
        return adrs;
    }
}



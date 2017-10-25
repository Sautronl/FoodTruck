package fr.wcs.foodtruck;

/**
 * Created by sam on 19/10/17.
 */

public class ReservationModels {
    private String nomReserv;
    private String numTelReserv;

    public ReservationModels() {

    }

    public ReservationModels(String nomReserv, String numTelReserv) {
        this.nomReserv = nomReserv;
        this.numTelReserv = numTelReserv;
    }

    public String getNomReserv() {
        return nomReserv;
    }

    public void setNomReserv(String nomReserv) {
        this.nomReserv = nomReserv;
    }

    public String getNumTelReserv() {
        return numTelReserv;
    }

    public void setNumTelReserv(String numTelReserv) {
        this.numTelReserv = numTelReserv;
    }

}

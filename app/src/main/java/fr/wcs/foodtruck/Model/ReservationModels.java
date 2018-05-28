package fr.wcs.foodtruck.Model;

import android.widget.Spinner;

/**
 * Created by sam on 19/10/17.
 */

public class ReservationModels {
    private String id;
    private String nomReserv;
    private String numTelReserv;
    private String horaire;
    private String nomBurger;
    private String prixBurger;

    public ReservationModels() {

    }

    public ReservationModels(String id, String nomReserv, String numTelReserv, String horaire) {
        this.id = id;
        this.nomReserv = nomReserv;
        this.numTelReserv = numTelReserv;
        this.horaire = horaire;
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

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomBurger() {
        return nomBurger;
    }

    public void setNomBurger(String nomBurger) {
        this.nomBurger = nomBurger;
    }

    public String getPrixBurger() {
        return prixBurger;
    }

    public void setPrixBurger(String prixBurger) {
        this.prixBurger = prixBurger;
    }

}

package fr.wcs.foodtruck.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Spinner;

/**
 * Created by sam on 19/10/17.
 */

public class ReservationModels implements Parcelable{
    private String id;
    private String nomReserv;
    private String numTelReserv;
    private String horaire;
    private String nomBurger;
    private String prixBurger;
    private String choixBoisson;
    private String choixDessert;

    public ReservationModels() {

    }

    public ReservationModels(String id, String nomReserv, String numTelReserv, String horaire,String nomBurger,String prixBurger, String choixBoisson, String choixDessert) {
        this.id = id;
        this.nomReserv = nomReserv;
        this.numTelReserv = numTelReserv;
        this.horaire = horaire;
        this.nomBurger = nomBurger;
        this.prixBurger = prixBurger;
        this.choixBoisson = choixBoisson;
        this.choixDessert = choixDessert;
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

    public String getChoixBoisson() {
        return choixBoisson;
    }

    public void setChoixBoisson(String choixBoisson) {
        this.choixBoisson = choixBoisson;
    }

    public String getChoixDessert() {
        return choixDessert;
    }

    public void setChoixDessert(String choixDessert) {
        this.choixDessert = choixDessert;
    }

    public static final Creator<ReservationModels> CREATOR = new Creator<ReservationModels>() {
        @Override
        public ReservationModels createFromParcel(Parcel in) {
            return new ReservationModels(in);
        }

        @Override
        public ReservationModels[] newArray(int size) {
            return new ReservationModels[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nomReserv);
        dest.writeString(numTelReserv);
        dest.writeString(horaire);
        dest.writeString(nomBurger);
        dest.writeString(prixBurger);
        dest.writeString(choixBoisson);
        dest.writeString(choixDessert);
    }

    protected ReservationModels(Parcel in) {
        id = in.readString();
        nomReserv = in.readString();
        numTelReserv = in.readString();
        horaire = in.readString();
        nomBurger = in.readString();
        prixBurger = in.readString();
        choixBoisson = in.readString();
        choixDessert = in.readString();
    }
}

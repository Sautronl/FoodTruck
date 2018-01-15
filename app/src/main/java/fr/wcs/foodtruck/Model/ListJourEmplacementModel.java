package fr.wcs.foodtruck.Model;

/**
 * Created by apprenti on 19/10/17.
 */


public class ListJourEmplacementModel {
    private String jour;
    private String adresse;

    public ListJourEmplacementModel(){}

    public ListJourEmplacementModel(String jour, String adresse) {
        this.jour = jour;
        this.adresse = adresse;
    }
    public String getJour() {
        return jour;
    }
    public String getAdresse() {
        return adresse;
    }
}
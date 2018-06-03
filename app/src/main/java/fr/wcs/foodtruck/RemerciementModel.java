package fr.wcs.foodtruck;


import android.content.ClipData;
import android.icu.text.SimpleDateFormat;

import java.util.Date;

/**
 * Created by apprenti on 02/10/17.
 */

public class RemerciementModel {
    private String nom;
    private String heure;

    public RemerciementModel(String nom, String heure) {
        this.nom = nom;
        this.heure = heure;
    }

    public String getNom() {
        return nom;
    }


    public String getHeure() {
        return heure;
    }
}



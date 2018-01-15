package fr.wcs.foodtruck.UI.Activity.User;

/**
 * Created by sam on 12/10/17.
 */

public class MajPlatDuJour {

    private String nomPlat;
    private String descPlat;
    private String urlImg;

    public MajPlatDuJour(){

    }

    public MajPlatDuJour(String nomPlat, String descPlat, String urlImg) {
        this.nomPlat = nomPlat;
        this.descPlat = descPlat;
        this.urlImg = urlImg;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public String getDescPlat() {
        return descPlat;
    }

    public void setDescPlat(String descPlat) {
        this.descPlat = descPlat;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}

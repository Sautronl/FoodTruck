package fr.wcs.foodtruck.Model;

/**
 * Created by sam on 12/10/17.
 */

public class MajPlatDuJour {

    private String nomPlat;
    private String descPlat;
    private String urlImg;
    private String prix;

    public MajPlatDuJour(){

    }

    public MajPlatDuJour(String nomPlat, String descPlat, String urlImg,String prix) {
        this.nomPlat = nomPlat;
        this.descPlat = descPlat;
        this.urlImg = urlImg;
        this.prix = prix;
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

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}

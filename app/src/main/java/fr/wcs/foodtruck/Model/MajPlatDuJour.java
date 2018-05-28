package fr.wcs.foodtruck.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 12/10/17.
 */

public class MajPlatDuJour implements Parcelable{

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


    public static final Creator<MajPlatDuJour> CREATOR = new Creator<MajPlatDuJour>() {
        @Override
        public MajPlatDuJour createFromParcel(Parcel in) {
            return new MajPlatDuJour(in);
        }

        @Override
        public MajPlatDuJour[] newArray(int size) {
            return new MajPlatDuJour[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    protected MajPlatDuJour(Parcel in) {
        nomPlat = in.readString();
        descPlat = in.readString();
        urlImg = in.readString();
        prix = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomPlat);
        dest.writeString(descPlat);
        dest.writeString(urlImg);
        dest.writeString(prix);
    }
}

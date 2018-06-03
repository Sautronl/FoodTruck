package fr.wcs.foodtruck;



public class ContactAdminModel {
    private String id;
    private String nom;
    private String tel;
    private String sujet;
    private String message;

    public ContactAdminModel(){

    }

    public ContactAdminModel(String id,String nom, String tel,String sujet,String message){
        this.id = id;
        this.nom = nom;
        this.tel = tel;
        this.sujet = sujet;
        this.message = message;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

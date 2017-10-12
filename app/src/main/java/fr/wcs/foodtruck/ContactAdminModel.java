package fr.wcs.foodtruck;



public class ContactAdminModel {
    private String nom;
    private String objet;


    public ContactAdminModel(String nom, String objet){
        this.nom = nom;
        this.objet = objet;

    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getObjet() {
        return this.objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

}

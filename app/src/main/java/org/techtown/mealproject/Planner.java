package org.techtown.mealproject;

public class Planner {
    int _id;
    String categorie;
    String bre_menu;
    String lun_menu;
    String din_menu;

    public Planner(int _id, String categorie, String bre_menu, String lun_menu, String din_menu)
    {
        this._id = _id;
        this.categorie = categorie;
        this.bre_menu = bre_menu;
        this.lun_menu = lun_menu;
        this.din_menu = din_menu;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getBre_menu() {
        return bre_menu;
    }

    public void setBre_menu(String bre_menu) {
        this.bre_menu = bre_menu;
    }

    public String getLun_menu() {
        return lun_menu;
    }

    public void setLun_menu(String lun_menu) {
        this.lun_menu = lun_menu;
    }

    public String getDin_menu() {
        return din_menu;
    }

    public void setDin_menu(String din_menu) {
        this.din_menu = din_menu;
    }
}

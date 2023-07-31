package org.techtown.mealproject;

public class Planner {
    int _id;
    String week;
    String categorie;
    String time;
    String menu;
    String mainsub;

    public Planner(String week, String time, String mainsub, String categorie, String menu)
    {
        //this._id = _id; 위에 int _id 추가할것
        this.week = week;
        this.categorie = categorie;
        this.mainsub = mainsub;
        this.time = time;
        this.menu = menu;
    }
    public Planner()
    {
        this("", "", "", "", "");
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMainsub() {
        return mainsub;
    }

    public void setMainsub(String mainsub) {
        this.mainsub = mainsub;
    }
}

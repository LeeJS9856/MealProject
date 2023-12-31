package org.techtown.mealproject;

public class Planner {
    int _id;
    String week;
    String categorie;
    String time;
    String menu;
    String mainsub;

    public Planner(int _id, String week, String time, String mainsub, String categorie, String menu)
    {
        this._id = _id;
        this.week = week;
        this.categorie = categorie;
        this.mainsub = mainsub;
        this.time = time;
        this.menu = menu;
    }

    public Planner(String mainsub, String categorie, String menu) {
        this._id = 0;
        this.week = "";
        this.time = "";
        this.mainsub = mainsub;
        this.categorie = categorie;
        this.menu = menu;
    }
    public Planner()
    {
        this(0,"일요일", "조식", "메인메뉴", "카테고리", "메뉴");
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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

package org.techtown.mealproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager {
    SQLiteDatabase database;

    public void println(String data) {
        Log.d("myTagr", data);
    }

    public void createDatabase(String name, Context context) {
        println("createDatabase 호출됨");

        database = context.openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        println("데이터베이스 생성함: " + name);
    }

    public void createTable(String name) {
        println("createTable 호출됨. ");

        if(database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        database.execSQL("create table if not exists " + name + "("
                +"_id integer PRIMARY KEY autoincrement, "
                +"week text, "
                +"time text, "
                +"mainSub text, "
                +"categorie text, "
                +"menu text)");

        println("테이블 생성함: "+ name);
    }

    public void insetRecord(SQLiteDatabase database, String tableName) {
        println("insertRecord 호출됨.");
        if(database == null) {
            println("데이터베이스를 먼저 생성항세요.");
            return;
        }

        if(tableName == null) {
            println("테이블을 먼저 생성하세요.");
            return;
        }

        database.execSQL("insert into "+ tableName
                +"(week, time, mainsub, categorie, menu) "
                +" values "
                +"( '일요일' , '조식 ' , '메인메뉴' , '카테고리' , '메뉴' )");

        println("레코드 추가함");
    }

    public void editRecord(SQLiteDatabase database, String tableName) {
        println("deleteRecord 호출됨");
        if(database == null) {
            println("데이터베이스가 없습니다.");
            return;
        }
        if(tableName == null) {
            println("테이블이 없습니다.");
            return;
        }




    }

    public void executeQuery(SQLiteDatabase database) {
        println("executeQuery 호출됨.");

        Cursor cursor = database.rawQuery("select _id, week, time, mainSub, categorie, menu from planner", null);
        int recordCount = cursor.getCount();
        println("레코드 개수: "+recordCount);

        for (int i=0;i<recordCount;i++) {
            cursor.moveToNext();
            int _id = cursor.getInt(0);
            String week = cursor.getString(1);
            String time = cursor.getString(2);
            String mainSub = cursor.getString(3);
            String categorie = cursor.getString(4);
            String menu = cursor.getString(5);

            println("레코드#"+i+" : "+ _id + ", "+week+", "+time+", "+mainSub+", "+categorie+", "+menu);
        }
        cursor.close();
    }
}

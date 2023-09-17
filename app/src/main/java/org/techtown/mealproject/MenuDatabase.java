package org.techtown.mealproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MenuDatabase {
    private static final String TAG = "MenuDatabase";
    private static MenuDatabase database;
    public static int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private final Context context;

    private MenuDatabase(Context context) {
        this.context = context;
        open();
    }

    public static MenuDatabase getInstance(Context context) {
        if(database == null) {
            database = new MenuDatabase(context);
        }

        return database;
    }

    public boolean open() {
        println("opening database [" + DatabaseName.MENU_DATABASE + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void close() {
        println("closing database [" + DatabaseName.MENU_DATABASE + "].");
        db.close();

        database = null;
    }

    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor cursor = null;
        try{
            if(db != null) {
                cursor = db.rawQuery(SQL, null);
                println("cursor count : " + cursor.getCount());
            }
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return cursor;
    }

    public boolean exeSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        }catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DatabaseName.MENU_DATABASE, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            println("creating database [" + DatabaseName.MENU_DATABASE + "].");

            for (String table : DatabaseName.MENU_TABLE) {
                println("creating table [" + table + "].");

                String DROP_SQL = "drop table if exists " + table;
                try {
                    db.execSQL(DROP_SQL);
                } catch (Exception ex) {
                    Log.e(TAG, "Exception in DROP_SQL", ex);
                }

                String CREATE_SQL = "create table " + table + "("
                        + " _id INTEGER, "
                        + "week text, "
                        + "time text, "
                        + "mainSub text, "
                        + "categorie text, "
                        + "menu text)";

                try {
                    db.execSQL(CREATE_SQL);
                } catch (Exception ex) {
                    Log.e(TAG, "Exception in CREATE_SQL", ex);
                }
            }


        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DatabaseName.MENU_DATABASE + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");
        }
    }

    private void println(String msg) {
            Log.d(TAG, msg);
        }
}

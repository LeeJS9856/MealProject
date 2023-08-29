package org.techtown.mealproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlannerDatabase {
    private static final String TAG = "PlannerDatabase";
    private static PlannerDatabase database;
    public static String DATABASE_NAME = "planner.db";
    public static String TABLE_PLANNER = "planner";
    public static int DATABASE_VERSION = 1;

    private DatabaseHelperSample dbHelper;
    private SQLiteDatabase db;
    private final Context context;

    private PlannerDatabase(Context context) {
        this.context = context;
    }

    public static PlannerDatabase getInstance(Context context) {
        if(database == null) {
            database = new PlannerDatabase(context);
        }

        return database;
    }

    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelperSample(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
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

    private class DatabaseHelperSample extends SQLiteOpenHelper {
        public DatabaseHelperSample(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            println("creating database [" + DATABASE_NAME + "].");

            println("creating table [" + TABLE_PLANNER + "].");

            String DROP_SQL = "drop table if exists " + TABLE_PLANNER;
            try{
                db.execSQL(DROP_SQL);
            }catch (Exception ex) {
                Log.d(TAG, "Exception in DROP_SQL", ex);
            }

            String CREATE_SQL = "create table " + TABLE_PLANNER + "("
                + " _id INTEGER, "
                    +"week text, "
                    +"time text, "
                    +"mainSub text, "
                    +"categorie text, "
                    +"menu text)";

            try {
                db.execSQL(CREATE_SQL);
            }catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }
        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");
        }
    }


    private void println(String msg) {
        Log.d(TAG, msg);
    }
}

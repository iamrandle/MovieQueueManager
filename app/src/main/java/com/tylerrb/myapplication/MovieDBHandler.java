package com.tylerrb.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by TylerLap on 12-Jan-17.
 */

public class MovieDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";
    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "_title";
    public static final String COLUMN_YEAR = "_year";
    public static final String COLUMN_RATED = "_rated";
    public static final String COLUMN_RUNTIME = "_runtime";
    public static final String COLUMN_DIRECTOR = "_director";
    public static final String COLUMN_WRITER = "_writer";
    public static final String COLUMN_ACTORS = "_actors";
    public static final String COLUMN_POSTER = "_poster";
    public static final String COLUMN_PLOT = "_plot";
    public static final String COLUMN_TYPE = "_type";
    public static final String COLUMN_METASCORE = "_metascore";

    public MovieDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + this.TABLE_MOVIES + "(" +
                this.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                this.COLUMN_TITLE + " TEXT," +
                this.COLUMN_YEAR + " TEXT," +
                this.COLUMN_RATED + " TEXT," +
                this.COLUMN_RUNTIME + " TEXT," +
                this.COLUMN_DIRECTOR + " TEXT," +
                this.COLUMN_WRITER + " TEXT," +
                this.COLUMN_ACTORS + " TEXT," +
                this.COLUMN_POSTER + " TEXT," +
                this.COLUMN_PLOT + " TEXT," +
                this.COLUMN_TYPE + " TEXT," +
                this.COLUMN_METASCORE + " TEXT" +
                ");";
        db.execSQL(query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + this.TABLE_MOVIES);
        this.onCreate(db);

    }

    public void addMovie(JSONObject movie){
        ContentValues values = new ContentValues();
        try {
            values.put(this.COLUMN_TITLE, movie.getString("Title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db  = getWritableDatabase();
        db.insert(this.TABLE_MOVIES, null, values);
        db.close();
    }
    public void deleteMovie(String title)
    {
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("DELETE FROM " + this.TABLE_MOVIES + " WHERE " + this.COLUMN_TITLE + "=\"" + title + "\";");

    }
    public String databaseToString ()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + this.TABLE_MOVIES + " WHERE 1 ";

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_TITLE)) != null)
            {
                dbString+= c.getString(c.getColumnIndex(COLUMN_TITLE)) + "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;

    }
}

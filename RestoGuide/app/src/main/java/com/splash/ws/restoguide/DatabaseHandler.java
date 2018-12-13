package com.splash.ws.restoguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "restaurant_db";
    private static final String TABLE_NAME = "restaurants";

    // Table column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_RATE = "rate";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database created..");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+ KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," +
                KEY_ADDRESS + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_DESCRIPTION +
                " TEXT, " + KEY_TAGS + " TEXT, " + KEY_RATE + " TEXT)";

        db.execSQL(CREATE_TABLE);
        Log.d("Database Operations", "Table created....");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // deleting the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // creates table again
        onCreate(db);
    }

    public boolean addData(String name, String address, String phone, String desc, String tag, String rate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_ADDRESS,address);
        values.put(KEY_PHONE,phone);
        values.put(KEY_DESCRIPTION,desc);
        values.put(KEY_TAGS,tag);
        values.put(KEY_RATE,rate);

        // input data to database
        Log.d("Database Operations", "Table created....");
        long result = db.insert(TABLE_NAME, null, values);
        //db.close();

        if(result == -1){return false;}
        else{return true;}
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor query = db.rawQuery("SELECT * FROM " +TABLE_NAME,null);
        return  query;
    }

    public Cursor getItemAll(int position){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ID, NAME, ADDRESS, PHONE, DESCRIPTION, TAGS, RATE FROM " + TABLE_NAME +
                " WHERE " + KEY_ID + " = '" + position + "'";
        Cursor all = db.rawQuery(query, null);
        return all;
    }

    public boolean deleteItem(String name){
        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + KEY_ID + " = '" + position + "'";
//        Log.d("deleting", "deleted");
//        db.execSQL(query);
        String where = KEY_NAME + "=" + name;
        return db.delete(TABLE_NAME, where,null) !=0;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            int result = db.delete(TABLE_NAME,KEY_ID+" =?",new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    // end file
}

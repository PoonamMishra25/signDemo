package com.example.poonamtiwarigreenflag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;


public class SqlDataBaseHelper extends SQLiteOpenHelper {


    static String name = "database";
    static int version = 1;
    public static String GREEN_FLAG_PROJECT_TABLE = "GREEN_FLAG_PROJECT";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    private final Context context;


    public SqlDataBaseHelper(Context context) {
        super(context, name, null, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE if not exists GREEN_FLAG_PROJECT ('id' INTEGER PRIMARY KEY AUTOINCREMENT ," +
                EMAIL + " TEXT , " +
                PASSWORD + " TEXT )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GREEN_FLAG_PROJECT_TABLE);
        onCreate(db);
    }


    public void insertUserDetail(ContentValues contentValues) {

        try (Cursor cursor = getReadableDatabase().
                rawQuery("SELECT * FROM GREEN_FLAG_PROJECT WHERE email = ?", new String[]{contentValues.get("email").toString()})) {
            if (cursor.moveToFirst()) {

                return;
            }
        }

        getWritableDatabase().insert(GREEN_FLAG_PROJECT_TABLE, "", contentValues);
        Toast.makeText(context, "Welcome! Registered successfully", Toast.LENGTH_SHORT).show();

    }




    public ArrayList<String> checkIfRecordExist(String email) {
        ArrayList<String> emails=new ArrayList<>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = " select * from " + GREEN_FLAG_PROJECT_TABLE + " where " + EMAIL + "=?";
            String[] select = new String[]{email};
            Cursor cursor = db.rawQuery(query, select);


            if (cursor.moveToFirst()) {
                do{
                emails.add( cursor.getString(1));

            }while (cursor.moveToNext());
            cursor.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }


}
